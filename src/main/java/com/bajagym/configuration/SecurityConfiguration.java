package com.bajagym.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public UserRepositoryAuthenticationProvider authenticationProvider;

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


        //Paginas privadas
        http.authorizeRequests().anyRequest().authenticated();

        http.authorizeRequests().antMatchers("usuarios/crearRutina/newRutina").hasAnyRole("ENTRENADOR");
        http.authorizeRequests().antMatchers("usuarios/crearClaseColectiva/newClaseColectiva").hasAnyRole("ENTRENADOR");
        http.authorizeRequests().antMatchers("usuarios/cambiarRutina/{name}").hasAnyRole("ENTRENADOR");

        //Formulario login
        http.formLogin().loginPage("/usuarios/login");
        http.formLogin().usernameParameter("userName");
        http.formLogin().passwordParameter("contrasenia");
        http.formLogin().defaultSuccessUrl("/usuarios/loging");
        http.formLogin().failureUrl("/usuarios/fallo");


        //Desconectar
        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/");
        //Deshabilitar CSRF
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Database authentication provider
        auth.authenticationProvider(authenticationProvider);
    }

}
