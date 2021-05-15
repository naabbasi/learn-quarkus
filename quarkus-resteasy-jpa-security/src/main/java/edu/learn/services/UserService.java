package edu.learn.services;

import javax.enterprise.context.RequestScoped;

import edu.learn.entity.User;

@RequestScoped
public class UserService {
    public User getUserInfo() {
        return new User("nabbasi", "x");
    }
}