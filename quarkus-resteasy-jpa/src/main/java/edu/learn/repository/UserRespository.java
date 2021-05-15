package edu.learn.repository;

import edu.learn.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRespository implements PanacheRepository<User> {
}
