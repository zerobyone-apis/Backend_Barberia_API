package zero.our.piece.barbers.barbers_api._security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import static zero.our.piece.barbers.barbers_api._security.ApplicationUserRole.*

@Configuration
@EnableWebSecurity
class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "index", "/js/*").permitAll()
                .antMatchers("/enterprise/**", "/email/**", "/metrics").hasRole(ADMIN.name()) //todo: we can select which route can access the rol.
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
    }

    /*  todo: ROUTE BASE AUTHENTICATION.
            Falta terminar de crear la seguridad, por ahoran tenemos autenticacion de rutas por medio de bcrypt y spring.
            - https://www.youtube.com/watch?v=her_7pa0vrg&t=1948s min: 01:15
    */


    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("zeroDev")
                .password(passwordEncoder.encode("password"))
                .roles(ADMIN.name())
                .build()

        UserDetails supervisor = User.builder()
                .username("TeamSupervisor")
                .password(passwordEncoder.encode("password"))
                .roles(SUPERVISOR.name())
                .build()

        UserDetails barber = User.builder()
                .username("TeamBarber")
                .password(passwordEncoder.encode("password"))
                .roles(BARBER.name())
                .build()

        UserDetails client = User.builder()
                .username("TeamClient")
                .password(passwordEncoder.encode("password"))
                .roles(CLIENT.name())
                .build()

        new InMemoryUserDetailsManager(
                admin, supervisor, barber, client
        )
    }

    @Bean
    static CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration()
        configuration.setAllowedOrigins(Arrays.asList("*"))
        configuration.setAllowedMethods(Arrays.asList("*"))
        configuration.setAllowedHeaders(Arrays.asList("*"))
        configuration.setAllowCredentials(true)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
