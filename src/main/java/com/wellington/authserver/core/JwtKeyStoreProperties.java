package com.wellington.authserver.core;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
@Validated
@Component
@ConfigurationProperties("courseapi.jwt.keystore")
public class JwtKeyStoreProperties {

    // @NotBlank
    // private String path;

    @NotNull
    private Resource jksLocation;

    @NotBlank
    private String password;

    @NotBlank
    private String keypairAlias;

    // public String getPassword() {
    // return password;
    // }

    // public void setPassword(String password) {
    // this.password = password;
    // }

    // public String getKeypairAlias() {
    // return keypairAlias;
    // }

    // public void setKeypairAlias(String keypairAlias) {
    // this.keypairAlias = keypairAlias;
    // }

    // public String getPath() {
    // return path;
    // }

    // public void setPath(String path) {
    // this.path = path;
    // }

}
