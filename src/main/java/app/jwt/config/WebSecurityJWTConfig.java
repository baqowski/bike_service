package app.jwt.config;

import app.jwt.filter.JWTAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author Karol Bąk
 */


@Configuration
@Order(2)
public class WebSecurityJWTConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and().csrf().disable()
                .antMatcher("/authorization/**")
                .authorizeRequests()
                .antMatchers("/authorization/login", "/authorization/register").permitAll()
              /*  .antMatchers("/ext/**").permitAll()*/
                .and()
                /*.antMatcher("/ext/**")
                .authorizeRequests()
                .antMatchers("**").permitAll()
                .and()*/
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}
