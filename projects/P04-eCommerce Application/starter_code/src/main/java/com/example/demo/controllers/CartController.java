package com.example.demo.controllers;

import java.security.Principal;
import java.util.Optional;

import java.util.stream.IntStream;

import com.example.demo.config.ItemNotFoundExcption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping("/addToCart")
    public ResponseEntity<Cart> addTocart(@RequestBody ModifyCartRequest request, Principal principal) {
        LOGGER.info("AddToCart request, user={}", request.getUsername());
        if (request.getUsername().equals(principal.getName())) {
            User user = userRepository.findByUsername(request.getUsername());
            if (user == null) {
                LOGGER.error("Exception:user not found to user={}", request.getUsername());
                throw new ItemNotFoundExcption("No such user!");
            }
            Optional<Item> item = itemRepository.findById(request.getItemId());
            if (!item.isPresent()) {
                LOGGER.error("Exception:item not found ,user={}", request.getUsername());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Cart cart = user.getCart();
//		for(int i=0;i<request.getQuantity();i++){
//			cart.addItem(item.get());
//		}
            IntStream.range(0, request.getQuantity())
                    .forEach(i -> cart.addItem(item.get()));
            cartRepository.save(cart);
            LOGGER.info("item={} added to user={} ", itemRepository.findById(request.getItemId()).get().getName(), request.getUsername());
            return ResponseEntity.ok(cart);
        } else
            LOGGER.error("Exception:forbidden access to user={}", request.getUsername());
        throw new ItemNotFoundExcption("No such user!");
    }

    @PostMapping("/removeFromCart")
    public ResponseEntity<Cart> removeFromcart(@RequestBody ModifyCartRequest request, Principal principal) {
        LOGGER.info("RemoveFromCart request user={}", request.getUsername());
        if (request.getUsername().equals(principal.getName())) {
            User user = userRepository.findByUsername(request.getUsername());
            if (user == null) {
                LOGGER.error("Exception:user not found, user={}", request.getUsername());
                throw new ItemNotFoundExcption("No such user!");
            }
            Optional<Item> item = itemRepository.findById(request.getItemId());
            if (!item.isPresent()) {
                LOGGER.error("Exception:item not found, user={}", user.getUsername());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Cart cart = user.getCart();
            IntStream.range(0, request.getQuantity())
                    .forEach(i -> cart.removeItem(item.get()));
            cartRepository.save(cart);
            LOGGER.info("Item={} removed, user={}",itemRepository.findById(request.getItemId()).get().getName(), request.getUsername());
            return ResponseEntity.ok(cart);
        } else
            LOGGER.error("Exception:forbidden access to user={}", request.getUsername());
        throw new ItemNotFoundExcption();

    }

}
