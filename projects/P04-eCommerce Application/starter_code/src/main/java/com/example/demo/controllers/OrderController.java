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
    public static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;


    @PostMapping("/submit/{username}")
    public ResponseEntity<UserOrder> submit(@PathVariable String username, Principal principal) {
        LOGGER.info("Order submit request, user={}",username);
        if (username.equals(principal.getName())) {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                LOGGER.error("Exception:user not found , user={}",username);
                throw new ItemNotFoundExcption("No such user!");
            }
            UserOrder order = UserOrder.createFromCart(user.getCart());
            orderRepository.save(order);
            LOGGER.info("order requests successes, user={}", user.getUsername());
            return ResponseEntity.ok(order);
        } else
            LOGGER.error("order requests failures, Exceptiom:forbidden access to user={}",username);
            throw new ItemNotFoundExcption("No such user!");
    }

        @GetMapping("/history/{username}")
        public ResponseEntity<List<UserOrder>> getOrdersForUser (@PathVariable String username, Principal principal) {
            if (username.equals(principal.getName())) {
                User user = userRepository.findByUsername(username);
                return ResponseEntity.ok(orderRepository.findByUser(user));
            }else
                LOGGER.error("Exception:forbidden access to user={}",username);
                throw new ItemNotFoundExcption("No such user!");
        }
    }
