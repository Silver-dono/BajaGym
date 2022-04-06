package com.bajagym.configuration;

import com.bajagym.security.UserRepositoryAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public UserRepositoryAuthenticationProvider userRepositoryAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //Paginas publicas
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/usuarios/login").permitAll();
        http.authorizeRequests().antMatchers("/usuarios/registro").permitAll();
        http.authorizeRequests().antMatchers("/usuarios/registered").permitAll();
        http.authorizeRequests().antMatchers("/rutinas/ejemplos").permitAll();
        http.authorizeRequests().antMatchers("/ClasesColectivas/").permitAll();
        http.authorizeRequests().antMatchers("/usuarios/fallo").permitAll();
        http.authorizeRequests().antMatchers("/logout").permitAll();


        //Paginas privadas


        http.authorizeRequests().antMatchers("/usuarios/crearRutina/newRutina").hasAnyRole("ENTRENADOR");
        http.authorizeRequests().antMatchers("/usuarios/crearClaseColectiva/newClaseColectiva").hasAnyRole("ENTRENADOR");
        http.authorizeRequests().antMatchers("/usuarios/ClasesColectivas/{idClase}/delete/ ").hasAnyRole("ENTRENADOR");
        http.authorizeRequests().antMatchers("/usuarios/rutinas/{name}/deleteRutina").hasAnyRole("ENTRENADOR");
        http.authorizeRequests().antMatchers("/usuarios/cambiarRutina/{name}").hasAnyRole("ENTRENADOR");
        http.authorizeRequests().antMatchers("/usuarios/cambiarRutina/cambiadoRutina/{name}").hasAnyRole("ENTRENADOR");
        http.authorizeRequests().antMatchers("/usuarios/crearRutina/{name}").hasAnyRole("ENTRENADOR");
        http.authorizeRequests().antMatchers("/usuarios/crearClaseColectiva/{name}").hasAnyRole("ENTRENADOR");
        http.authorizeRequests().antMatchers("/ClasesColectivas/{name}").hasAnyRole("USUARIO");
        http.authorizeRequests().antMatchers("/rutinas/{name}").hasAnyRole("USUARIO");


        //Formulario login
        http.formLogin().loginPage("/usuarios/login")
                .usernameParameter("userName")
                .passwordParameter("contrasenia")
                .defaultSuccessUrl("/usuarios/loging")
                .failureUrl("/usuarios/fallo");

        //Desconectar
        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/");

        //Deshabilitar CSRF
        //http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Database authentication provider
        auth.authenticationProvider(userRepositoryAuthenticationProvider);
    }

}
