package edu.learn.setup;

import edu.learn.entity.User;
import edu.learn.repository.UserRepository;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Optional;

@Singleton
public class SetupApplication {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    UserRepository userRepository;

    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        String databaseKind = ConfigProvider.getConfig().getValue("quarkus.datasource.db-kind", String.class);
        Optional<String> maybeDatabaseKind = ConfigProvider.getConfig().getOptionalValue("quarkus.datasource.db-kind", String.class);
        log.info("Using Database: {}", databaseKind);
        log.info("Using  Database(Opt): {}", maybeDatabaseKind.get());

        // reset and load all test users
        User.deleteAll();
        this.userRepository.persist(new User("admin", "admin", "admin"));
        this.userRepository.persist(new User("user", "user", "user"));
    }
}
