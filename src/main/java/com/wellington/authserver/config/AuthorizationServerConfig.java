package com.wellington.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // PASSWORD GRANT TYPE
                .withClient("courses-web")
                .secret(passwordEncoder.encode("web123"))
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("write", "read")
                .accessTokenValiditySeconds(6 * 60 * 60) // 6 HORAS
                .refreshTokenValiditySeconds(60 * 24 * 60 * 60) // 60 DIAS
                // CLIENT CREDENTIALS GRANT TYPE
                .and()
                .withClient("outra-api-backend")
                .secret(passwordEncoder.encode("outra123"))
                .authorizedGrantTypes("client_credentials")
                .scopes("write", "read")
                // AUTHORIZATION CODE GRANT TYPE
                // http://localhost:8080/oauth/authorize?response_type=code&client_id=outra-api-backend2&state=stateTest&redirect_uri=http://aplicacao-cliente
                .and()
                .withClient("outra-api-backend2")
                .secret(passwordEncoder.encode("outra2123"))
                .authorizedGrantTypes("authorization_code")
                .scopes("write", "read")
                .redirectUris("http://127.0.0.1:5500")
                // IMPLICT GRANT TYPE
                // http://localhost:8080/oauth/authorize?response_type=token&client_id=webadmin&state=stateTest&redirect_uri=http://aplicacao-cliente
                .and()
                .withClient("webadmin")
                .authorizedGrantTypes("implicit")
                .scopes("write", "read")
                .redirectUris("http://aplicacao-cliente")
                // CLIENT DO RESOURCE SERVER
                .and()
                .withClient("checkToken")
                .secret(passwordEncoder.encode("check123"));
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()");
        // security.checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .reuseRefreshTokens(false);
    }

}
