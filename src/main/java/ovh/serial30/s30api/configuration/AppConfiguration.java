package ovh.serial30.s30api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ovh.serial30.s30api.repositories.UsersRepository;

@Configuration
public class AppConfiguration {
    @Autowired
    private UsersRepository usersRepository;

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    /**
     * Sets the new strategy to perform the authentication
     * @return AuthenticationProvider
     */
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Retrieves the user information using the UserRepository that is injected
     * @return UserDetailsService bean
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usersRepository.findByUsername(username).orElseThrow();
    }

    /**
     * Creates an instance of the BCryptPasswordEncoder class used to encode the plain user password
     * @return PasswordEncoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
