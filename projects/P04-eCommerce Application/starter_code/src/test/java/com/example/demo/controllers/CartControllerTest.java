package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.config.ItemNotFoundExcption;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {


    private  Cart cart;
    CartController cartController;
    private UserRepository userRepo=mock(UserRepository.class);
    private CartRepository cartRepo=mock(CartRepository.class);
    private ItemRepository itemRepo=mock(ItemRepository.class);
    private  User user;
    private  Item item;
    private ModifyCartRequest request;
    private static UsernamePasswordAuthenticationToken authenticationToken;


    public void createItem(){
        item=new Item();
        item.setName("lollipop");
        item.setId(1L);
        item.setPrice(BigDecimal.valueOf(1.00));
    }
    public void createCart(){
        cart=new Cart();
        cart.setUser(user);
    }
    public void createUser(){
        user=new User();
        user.setUsername("Moustafa");
        user.setCart(cart);
    }
    @BeforeClass
    public static void setup(){
        authenticationToken =new UsernamePasswordAuthenticationToken("Moustafa",null,null);
    }
    @Before
    public void initial(){
        cartController=new CartController();
        TestUtils.injectObject(cartController,"userRepository",userRepo);
        TestUtils.injectObject(cartController,"cartRepository",cartRepo);
        TestUtils.injectObject(cartController,"itemRepository",itemRepo);
        createItem();
        createCart();
        createUser();

        when(userRepo.findByUsername("Moustafa")).thenReturn(user);
        when(itemRepo.findById(1L)).thenReturn(Optional.of(item));
        when(cartRepo.save(cart)).thenReturn(cart);
        request=new ModifyCartRequest();
        request.setItemId(1L);
        request.setQuantity(3);
        request.setUsername("Moustafa");
    }

    @Test
    public void verify_addToCart_happy_path(){

        ResponseEntity<Cart> response = cartController.addTocart(request, authenticationToken);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(cart,response.getBody());
        assertEquals(BigDecimal.valueOf(3.0),response.getBody().getTotal());


    }

    @Test(expected = ItemNotFoundExcption.class)
    public void verify_addToCart_sad_path(){
        request.setItemId(2L);

        ResponseEntity<Cart> response = cartController.addTocart(request, authenticationToken);
        assertEquals(404,response.getStatusCodeValue());
        assertNull(response.getBody());

        request.setItemId(1L);
        request.setUsername("Nour");
        assertThrows(ItemNotFoundExcption.class, (ThrowingRunnable) cartController.addTocart(request, authenticationToken));
        assertEquals(404,response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void verify_removeFromcart_happy_path(){
       request.setQuantity(1);
        ResponseEntity<Cart> response = cartController.removeFromcart(request, authenticationToken);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(BigDecimal.valueOf(-1.0),response.getBody().getTotal());
    }

    @Test(expected = ItemNotFoundExcption.class)
    public void verify_removeFromcart_sad_path(){
        request.setItemId(2L);
        request.setQuantity(1);
        ResponseEntity<Cart> response = cartController.removeFromcart(request, authenticationToken);
        assertEquals(404,response.getStatusCodeValue());
        assertNull(response.getBody());

        request.setItemId(1L);
        request.setUsername("Nour");
        ResponseEntity<Cart> response2 = cartController.removeFromcart(request, authenticationToken);
        assertEquals(404,response2.getStatusCodeValue());
        assertNull(response2.getBody());
    }

}
