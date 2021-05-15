package edu.learn.launcher;

import edu.learn.entity.User;
import edu.learn.repository.UserRespository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
public class Startup {
    @Inject
    UserRespository userRespository;

    @Transactional
    public void loadUsers(@Observes StartupEvent startupEvent) {
        this.userRespository.deleteAll();
        this.userRespository.persist(new User("nabbasi", BcryptUtil.bcryptHash("x"), "admin"));
        this.userRespository.persist(new User("fabbasi", BcryptUtil.bcryptHash("x"), "user"));
    }
}
