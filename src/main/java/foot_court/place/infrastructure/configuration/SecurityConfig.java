package foot_court.place.infrastructure.configuration;

import foot_court.place.infrastructure.security.CustomAccessDeniedHandler;
import foot_court.place.infrastructure.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static foot_court.place.domain.utils.PlaceUtils.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/restaurants/create-restaurant").hasRole(ROLE_ADMINISTRATOR)
                        .requestMatchers("/plates/create-plate").hasRole(ROLE_OWNER)
                        .requestMatchers("/plates/update-plate").hasRole(ROLE_OWNER)
                        .requestMatchers("/plates/change-availability/**").hasRole(ROLE_OWNER)
                        .requestMatchers("/restaurants/get-restaurants").permitAll()
                        .requestMatchers("/restaurants/get-menu").permitAll()
                        .requestMatchers("/orders/create-order").hasRole(ROLE_CUSTOMER)
                        .requestMatchers("/orders/view-orders").hasRole(ROLE_EMPLOYEE)
                        .requestMatchers("/orders/assign-order").hasRole(ROLE_EMPLOYEE)
                        .requestMatchers("/orders/order-ready").hasRole(ROLE_EMPLOYEE)
                        .requestMatchers("/restaurants/enter-employee").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}