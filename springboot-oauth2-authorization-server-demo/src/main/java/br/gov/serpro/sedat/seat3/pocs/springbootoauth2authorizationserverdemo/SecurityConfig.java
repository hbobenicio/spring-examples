package br.gov.serpro.sedat.seat3.pocs.springbootoauth2authorizationserverdemo;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * Spring Authorization Server configuration:
 * https://docs.spring.io/spring-authorization-server/docs/current/reference/html/getting-started.html
 */
@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    @Order(0)
    public SecurityFilterChain initialSetupSecurityFilterChain(HttpSecurity http) throws Exception {
        http.cors().disable();

        // NOTE(security): always enable CSRF for endpoints used by browsers
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    /**
     * 1) Spring Security filter chain for the Protocol Endpoints
     */
    @Bean
    @Order(1)
//    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain oauth2AuthorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {

        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);


        // Enable OpenID Connect 1.0
//        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());

        http.exceptionHandling()
                .authenticationEntryPoint((request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                );

//        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
//        http.apply(authorizationServerConfigurer);
//
//        authorizationServerConfigurer.clientAuthentication(clientAuthentication ->
//                        clientAuthentication
//                                .authenticationConverter(authenticationConverter)
//                                .authenticationConverters(authenticationConvertersConsumer)
//                                .authenticationProvider(authenticationProvider)
//                                .authenticationProviders(authenticationProvidersConsumer)
//                                .authenticationSuccessHandler(authenticationSuccessHandler)
//                                .errorResponseHandler(errorResponseHandler)
//                );

        // Accept access tokens for User Info and/or Client Registration
//        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

//        http.authorizeHttpRequests()
//                .requestMatchers("/api/**")
//                .authenticated();

//        http.authorizeHttpRequests()
//                .requestMatchers("/oauth2/authorize**")
//                .permitAll();

        return http.build();
    }

    /**
     * 2) Spring Security filter chain for authentication
     */
//    @Bean
////    @Order(2)
//    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
//        // Form login handles the redirect to the login page from the
//        // authorization server filter chain
////                .formLogin(Customizer.withDefaults())
//        ;
//
//        return http.build();
//    }

    /**
     * 3) Spring Security configuration for retrieving users to authenticate
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("Sistema X do INSS")
                .password("123456")
                .roles("INSS-SYSTEM")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

    /**
     * 4) Configuration for managing clients
     *
     * A RegisteredClient is a representation of a client that is registered with the authorization server.
     * A client must be registered with the authorization server before it can initiate an authorization grant flow,
     * such as authorization_code or client_credentials.
     *
     * During client registration, the client is assigned a unique client identifier,
     * (optionally) a client secret (depending on client type), and metadata associated with its unique client identifier.
     * The client’s metadata can range from human-facing display strings (such as client name) to items specific to a
     * protocol flow (such as the list of valid redirect URIs).
     *
     * @see https://docs.spring.io/spring-authorization-server/docs/current/reference/html/core-model-components.html#registered-client
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("inss-system-x")
                .clientSecret("{noop}123456")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
//                .redirectUri("http://localhost:8080/authorized")
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
                .scope("divida.read")
                .scope("divida.write")
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * 8) Spring Authorization Server Configuration.
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer("http://my.app.dns.com")

//        The default is /oauth2/authorize
//                .authorizationEndpoint("/api/oauth2/authorize")

//                The default is /oauth2/token
//                .tokenEndpoint("/api/oauth2/token")
                .build();
    }
}
