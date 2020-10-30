package com.example.demo.controllers;

import com.example.demo.config.ItemNotFoundExcption;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;


    @PostMapping("/submit/{username}")
    public ResponseEntity<UserOrder> submit(@PathVariable String username, Principal principal) {
        if (username.equals(principal.getName())) {
            User user = userRepository.findByUsername(username);
            UserOrder order = UserOrder.createFromCart(user.getCart());
            orderRepository.save(order);
            logger.info("items submitted for: {}.", user.getUsername());
            return ResponseEntity.ok(order);
        } else
            throw new ItemNotFoundExcption("No such user!");
    }

        @GetMapping("/history/{username}")
        public ResponseEntity<List<UserOrder>> getOrdersForUser (@PathVariable String username, Principal principal) {
            if (username.equals(principal.getName())) {
                User user = userRepository.findByUsername(username);
                return ResponseEntity.ok(orderRepository.findByUser(user));
            }else
                throw new ItemNotFoundExcption("No such user!");
        }
    }
