package com.example.Gruppuppgift6A.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.Gruppuppgift6A.entity.User;
import com.example.Gruppuppgift6A.exceptions.CreateUserException;
import com.example.Gruppuppgift6A.exceptions.InvalidPasswordException;
import com.example.Gruppuppgift6A.exceptions.NoSuchUsernameException;
import com.example.Gruppuppgift6A.exceptions.UserAlreadyExistsException;
import com.example.Gruppuppgift6A.repo.IUserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final IUserRepo userRepo;
    private final JwtService jwtService;

    public User createUser(User user) throws CreateUserException {

        if (userRepo.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("User with username " + user.getUsername() + " already exists");
        }
        if (user.getUsername().length() < 5) {
            throw new CreateUserException("Username too short");
        }
        if (user.getPassword().length() < 8) {throw new InvalidPasswordException("Password too short");}
        if (!user.getPassword().matches(".*[^a-zA-Z0-9].*")) throw new InvalidPasswordException("Password requires at least one symbol");
        if (!user.getPassword().matches(".*[A-Z].*")) throw new InvalidPasswordException("Password requires at least one uppercase letter");
        if (!user.getPassword().matches(".*[0-9].*")) throw new InvalidPasswordException("Password requires at least one number");

        String hashedPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        var newUser = new User(user.getUsername(), hashedPassword);

        log.info("User {}: Created successfully", user.getUsername());
        return userRepo.save(newUser);
    }

    public String login(String username, String password) {

        if (!userRepo.existsByUsername(username)) {
            throw new NoSuchUsernameException("Invalid username or password");
        }

        var user = userRepo.findByUsername(username);
        BCrypt.Result result = BCrypt.verifyer().verify(
                password.toCharArray(),
                user.getPassword()
        );

        if (!result.verified) {
            throw new InvalidPasswordException("Invalid username or password");
        }

        return jwtService.generateToken(user.getId());
    }

}
