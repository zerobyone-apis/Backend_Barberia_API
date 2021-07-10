package zero.our.piece.barbers.barbers_api._security.infrastructure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import zero.our.piece.barbers.barbers_api._security.infrastructure.jwt.JwtUsernameAndPasswordAuthFilter
import zero.our.piece.barbers.barbers_api._security.service.UserSecurityService

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity (prePostEnabled = true)
class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    /*
        TODO:
            Basic Authentication funciona.
            falta guardar los usuarios, y pasarnos a FORM AUTHENTICATION con JWT.

            -> https://youtu.be/her_7pa0vrg?t=8362 crack.

     */

    @Autowired
    private PasswordEncoder passwordEncoder

    @Autowired
    private UserSecurityService userSecurityService

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthFilter(authenticationManager: authenticationManager()))
                .authorizeRequests()
                .antMatchers("/user/v1/login").permitAll()
                .anyRequest()
                .authenticated()
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider())
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        new DaoAuthenticationProvider(
                passwordEncoder: passwordEncoder,
                userDetailsService: userSecurityService,
        )
    }


}
