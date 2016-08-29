package cvut.fit.util;

import cvut.fit.domain.entity.User;
import cvut.fit.domain.entity.UserRole;
import cvut.fit.domain.entity.enums.Role;
import cvut.fit.domain.repository.UserRepository;
import cvut.fit.domain.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by Jakub Tuček on 29.8.2016.
 */
@Component
public class TestDataInit implements CommandLineRunner {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public TestDataInit(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User u1 = new User("user", encoder.encode("password"), true);
        u1 = userRepository.save(u1);
        UserRole role1 = new UserRole("ADMIN", u1);
        userRoleRepository.save(role1);
    }
}
