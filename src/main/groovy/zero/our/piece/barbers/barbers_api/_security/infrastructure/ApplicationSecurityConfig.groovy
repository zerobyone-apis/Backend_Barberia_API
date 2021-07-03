package zero.our.piece.barbers.barbers_api._security.infrastructure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager


import static ApplicationUserRole.*

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    /*
        TODO:
            Basic Authentication funciona.
            falta guardar los usuarios, y pasarnos a FORM AUTHENTICATION con JWT.

            -> https://youtu.be/her_7pa0vrg?t=8362 crack.

     */

    @Autowired
    private PasswordEncoder passwordEncoder

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
    }


    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("zeroDev")
                .password(passwordEncoder.encode("password"))
                //.roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build()

        UserDetails supervisor = User.builder()
                .username("TeamSupervisor")
                .password(passwordEncoder.encode("password"))
                //.roles(SUPERVISOR.name())
                .authorities(SUPERVISOR.getGrantedAuthorities())
                .build()

        UserDetails barber = User.builder()
                .username("TeamBarber")
                .password(passwordEncoder.encode("password"))
                //.roles(BARBER.name())
                .authorities(BARBER.getGrantedAuthorities())
                .build()

        UserDetails client = User.builder()
                .username("TeamClient")
                .password(passwordEncoder.encode("password"))
                //.roles(CLIENT.name())
                .authorities(CLIENT.getGrantedAuthorities())
                .build()

        new InMemoryUserDetailsManager(
                admin, supervisor, barber, client
        )
    }
}
