package edu.learn.setup;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

public class GenerateToken {
    /**
     * Generate JWT token
     * -Dsmallrye.jwt.sign.key-location=server.key
     */
    public static void main(String[] args) {
        String token =
           Jwt.issuer("https://nabbasi:8443/issuer")
             .upn("jdoe@quarkus.io")
             .groups(new HashSet<>(Arrays.asList("user", "admin")))
             .claim(Claims.email.name(), "nabbasi@softpak.com")
             .claim(Claims.birthdate.name(), LocalDateTime.now())
           .sign();
        System.out.println(token);
    }
}
