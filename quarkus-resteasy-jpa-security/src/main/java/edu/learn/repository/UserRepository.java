package edu.learn.repository;

import edu.learn.entity.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

@Transactional
@RequestScoped
public class UserRepository implements PanacheRepository<User> {
    public User saveOrUpdate(User user) {
        User getUser = null;

        if(user.getUserId() != null) {
            getUser = this.findById(user.getUserId());
        } else {
            getUser = new User();
        }

        getUser.setUsername(user.getUsername());
        getUser.setPassword(BcryptUtil.bcryptHash(user.getPassword()));
        getUser.setRole(user.getRole());
        persist(getUser);

        this.isPersistent(getUser);

        return getUser;
    }

    public void deleteUser(Long userId) {
        User getUser = this.findById(userId);
        this.delete(getUser);
    }
}
