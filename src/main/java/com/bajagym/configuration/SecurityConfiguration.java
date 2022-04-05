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
        http.authorizeRequests().antMatchers("/usuarios/desconectar").permitAll();


        //Paginas privadas

        http.authorizeRequests().anyRequest().authenticated();

        //Formulario login
        http.formLogin().loginPage("/usuarios/login")
                .usernameParameter("userName")
                .passwordParameter("contrasenia")
                .defaultSuccessUrl("/usuarios/loging")
                .failureUrl("/usuarios/fallo");

        //Desconectar
        http.logout().logoutUrl("/usuarios/desconectar");
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
