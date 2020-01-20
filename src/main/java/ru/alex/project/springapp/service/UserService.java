package ru.alex.project.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.alex.project.springapp.entities.Role;
import ru.alex.project.springapp.entities.User;
import ru.alex.project.springapp.repositories.UserRepo;

import java.util.*;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found!");
        return user;
    }

    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null)
            return false;

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        sendMessage(user);

        return true;
    }

    private void sendMessage(User user) {
        if (! user.getEmail().isEmpty()) {
            String message = format(
                    "Hello, %s! \n" +
                            "Welcome to Lexa's. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode());

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null)
            return false;

        user.setActivationCode(null);

        userRepo.save(user);

        return true;
    }

    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(toSet());

        user.getRoles().clear();

        form.keySet().stream()
                .filter(key -> roles.contains(key))
                .forEach(key -> user.getRoles().add(Role.valueOf(key)));

        userRepo.save(user);
    }

    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);

            if(!StringUtils.isEmpty(email))
                user.setActivationCode(UUID.randomUUID().toString());

            if (!StringUtils.isEmpty(password))
                user.setPassword(password);

            userRepo.save(user);

            if (isEmailChanged)
                sendMessage(user);
        }
    }
}