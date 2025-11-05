package br.com.fiap.QMove_MVC.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            
            .authorizeHttpRequests(auth -> auth
                // Libera acesso às páginas públicas
                .requestMatchers("/", "/home", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                
                // Libera acesso às páginas de erro
                .requestMatchers("/error/**").permitAll()

                // Somente ADMIN pode acessar funcionários
                .requestMatchers("/funcionarios/**").hasRole("ADMIN")

                // ADMIN e USER podem acessar setores e motos
                .requestMatchers("/setores/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/motos/**").hasAnyRole("ADMIN", "USER")

                // Qualquer outra rota precisa de login
                .anyRequest().authenticated()
            )
            
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            
            .exceptionHandling(exception -> exception
                .accessDeniedHandler(customAccessDeniedHandler)
            );

        return http.build();
    }
}
