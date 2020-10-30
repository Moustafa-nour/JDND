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
    Logger logger = LoggerFactory.getLogger(CartController.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/id/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id, Principal principal) {
        if(userRepository.findById(id).orElseThrow(() -> new ItemNotFoundExcption("No such user!"))
                .getUsername().equals(principal.getName()))
             return ResponseEntity.of(userRepository.findById(id));
        else
            throw new ItemNotFoundExcption("No such user!");
//        return new ResponseEntity(userRepository.findById(id), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUserName(@PathVariable String username, Principal principal) {
        if(principal.getName().equals(username)) {
            return ResponseEntity.ok(userRepository.findByUsername(username));
        }else
            throw new ItemNotFoundExcption("No such user!");
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        Cart cart = new Cart();
        cartRepository.save(cart);
        user.setCart(cart);
        if (createUserRequest.getPassword().length() < 7 || !createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
            return ResponseEntity.badRequest().build();
        }
//		user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
        String salt = BCrypt.gensalt();
        user.setPassword(BCrypt.hashpw(createUserRequest.getPassword(), salt));
        user.setSalt(salt);
        userRepository.save(user);
        logger.info("new user created with name: {}.", user.getUsername());
        return ResponseEntity.ok(user);
    }

}
