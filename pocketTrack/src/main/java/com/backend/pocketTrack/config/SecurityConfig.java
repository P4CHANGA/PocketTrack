package com.backend.pocketTrack.config;

import com.backend.pocketTrack.config.security.JwtAccessDenied;
import com.backend.pocketTrack.config.security.JwtAuthenticationFilter;
import com.backend.pocketTrack.config.security.JwtEntryPoint;
import com.backend.pocketTrack.enums.RoleType;
import com.backend.pocketTrack.service.Usuario.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final IUsuarioService userService;
    //private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtEntryPoint unauthorizedHandler;
    private final JwtAccessDenied accessDeniedHandler;

    @Bean
    UserDetailsService userDetailsService() {
        return username -> userService.loadUserByUsername(username);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    //DAO y SetUserDatailsService estan deprecados

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS","PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler)
                        .accessDeniedHandler(accessDeniedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/pocket/**").hasAnyRole(RoleType.ADMIN.name())
                                .requestMatchers(HttpMethod.GET, "/api/pocket/**").hasAnyRole(RoleType.USER.name())
                                .requestMatchers(HttpMethod.GET,  "/api/pocket/obtenerGastos/{idCuenta}").hasAnyRole(RoleType.ADMIN.name())
                                .requestMatchers(HttpMethod.GET,"/api/pocket/obtenerIdUsuarioPorUsername/").hasAnyRole(RoleType.USER.name())
                                .requestMatchers(HttpMethod.GET,"/api/pocket/obtenerIdUsuarioPorUsername/").hasAnyRole(RoleType.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/api/pocket/crearCuenta/**").hasAnyRole(RoleType.ADMIN.name())
                                .requestMatchers(HttpMethod.POST,"/api/pocket/crearGasto/**").hasAnyRole(RoleType.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/api/pocket/**").hasAnyRole(RoleType.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/api/pocket/eliminarGasto/**").hasAnyRole(RoleType.ADMIN.name())
                                .requestMatchers(HttpMethod.PATCH, "/api/pocket/cantidadCuenta/**").hasAnyRole(RoleType.ADMIN.name())
                                //.requestMatchers(HttpMethod.PATCH, "/api/**").hasAnyRole(RoleType.ADMIN.name())
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                //.requestMatchers("/websocket/**").permitAll()
                                .anyRequest().denyAll()
                )
                .userDetailsService(userDetailsService());

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
