package com.example.demo.controllers;

import com.example.demo.config.ItemNotFoundExcption;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import net.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    public static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/id/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id, Principal principal) {
        LOGGER.info("FindUserById request, Id={}", id);
        if (userRepository.findById(id).orElseThrow(() -> new ItemNotFoundExcption("No such user!"))
                .getUsername().equals(principal.getName())) {
            LOGGER.info("found user={}", userRepository.findById(id).get().getUsername());
            return ResponseEntity.of(userRepository.findById(id));
        } else {
            LOGGER.error("Exception:Forbidden access to user={}", userRepository.findById(id).get().getUsername());
            throw new ItemNotFoundExcption("No such user!");
        }
//        return new ResponseEntity(userRepository.findById(id), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUserName(@PathVariable String username, Principal principal) {
        LOGGER.info("FindByUserName request, name={}", username);
        if (principal.getName().equals(username)) {
            LOGGER.info("found user={}", username);
            return ResponseEntity.ok(userRepository.findByUsername(username));
        } else {
            LOGGER.error("Exception:forbidden access to user={}", username);
            throw new ItemNotFoundExcption("No such user!");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        LOGGER.info("CreateUser request ", createUserRequest.getUsername());
        User user = new User();
        if (userRepository.findByUsername(createUserRequest.getUsername()) != null) {
            LOGGER.error("CreateUser request failures, Username validation failed");
            return ResponseEntity.badRequest().build();
        }
        user.setUsername(createUserRequest.getUsername());
        Cart cart = new Cart();
        cartRepository.save(cart);
        user.setCart(cart);
        if (createUserRequest.getPassword().length() < 7 || !createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
            LOGGER.error("CreateUser request failures, Password validation failed");
            return ResponseEntity.badRequest().build();
        }
//		user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
        String salt = BCrypt.gensalt();
        user.setPassword(BCrypt.hashpw(createUserRequest.getPassword(), salt));
        user.setSalt(salt);
        userRepository.save(user);
        LOGGER.info("CreateUser request successes, user={}", user.getUsername());
        return ResponseEntity.ok(user);
    }

}
