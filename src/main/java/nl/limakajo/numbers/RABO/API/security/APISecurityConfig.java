package nl.limakajo.numbers.RABO.API.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class APISecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("{bcrypt}$2a$10$k4/7UdadGtTAw.JGUiJ4E.N.pIXGJiHOIy33gwT5WQDWJDiPIvjTG").roles("USER", "ADMIN")
                .and()
                .withUser("user").password("{bcrypt}$2a$10$m/01n1RdxOWO9Q0rYG1sHOtE7.4.sRAnLztYV4HUwIzNOabZqF3cK").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/api/levels/*").hasRole("USER")
                    .antMatchers(HttpMethod.GET, "/api/levels/closest/*").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/api/levels").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/api/levels").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/api/levels/*").hasRole("ADMIN")
                .and()
                .csrf()
                    .disable()
                .formLogin()
                    .disable();
    }

}
