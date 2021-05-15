package edu.learn.setup;

import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.microprofile.jwt.Claims;

import io.smallrye.jwt.build.Jwt;

public class GenerateToken {
    /**
     * Generate JWT token
     * -Dsmallrye.jwt.sign.key-location=server.key
     */
    public static void main(String[] args) {
        String token =
           Jwt.issuer("http://nabbasi/issuer")
             .upn("jdoe@quarkus.io")
             .groups(new HashSet<>(Arrays.asList("user", "admin")))
             .claim(Claims.birthdate.name(), "2001-07-13")
           .sign();
        System.out.println(token);
    }
}
