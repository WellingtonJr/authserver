package com.wellington.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wellington.authserver.core.io.Base64ProtocolResolver;

@SpringBootApplication
public class AuthserverApplication {

	public static void main(String[] args) {
		var app = new SpringApplication(AuthserverApplication.class);
		app.addListeners(new Base64ProtocolResolver());
		app.run(args);
		// SpringApplication.run(AuthserverApplication.class, args);
	}

}
