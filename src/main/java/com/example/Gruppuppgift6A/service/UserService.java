package com.example.Gruppuppgift6A.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.Gruppuppgift6A.controller.UserController;
import com.example.Gruppuppgift6A.entity.User;
import com.example.Gruppuppgift6A.exceptions.CreateUserException;
import com.example.Gruppuppgift6A.exceptions.InvalidPasswordException;
import com.example.Gruppuppgift6A.exceptions.NoSuchUsernameException;
import com.example.Gruppuppgift6A.exceptions.UserAlreadyExistsException;
import com.example.Gruppuppgift6A.repo.IUserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final IUserRepo userRepo;
    private final JwtService jwtService;

    public UserResponse register (UserController.UserCredentials userCredentials) {

        if (userRepo.existsByUsername(userCredentials.username())) {
            throw new UserAlreadyExistsException("User with username " + userCredentials.username() + " already exists");
        }
        if (userCredentials.username().length() < 5) {
            throw new CreateUserException("Username too short");
        }
        if (userCredentials.password().length() < 8) throw new InvalidPasswordException("Password too short");
        if (!userCredentials.password().matches(".*[^a-zA-Z0-9].*")) throw new
                InvalidPasswordException("Password requires at least one symbol");
        if (!userCredentials.password().matches(".*[A-Z].*")) throw new
                InvalidPasswordException("Password requires at least one uppercase letter");
        if (!userCredentials.password().matches(".*[0-9].*")) throw new
                InvalidPasswordException("Password requires at least one number");

        String hashedPassword = BCrypt.withDefaults().hashToString(12, userCredentials.password().toCharArray());
        var newUser = new User(userCredentials.username(), hashedPassword);

        log.info("User {}: Created successfully", userCredentials.username());
        userRepo.save(newUser);

        return new UserResponse(newUser.getUsername(), "");
    }

    public UserResponse login(UserController.UserCredentials userCredentials) {

        User user = userRepo.findByUsername(userCredentials.username()).orElseThrow(() ->
                new UsernameNotFoundException("Invalid username or password"));

        BCrypt.Result result = BCrypt.verifyer().verify(
                userCredentials.password().toCharArray(),
                user.getPassword()
        );

        if (!result.verified) {
            throw new InvalidPasswordException("Invalid username or password");
        }

        return new UserResponse(user.getUsername(), jwtService.generateToken(user.getId()));
    }

    public record UserResponse(String username, String token) { }

}
