package com.devrish.martcart.service;

import com.devrish.martcart.dto.requests.cart.AddOrRemoveItemBody;
import com.devrish.martcart.dto.responses.CartResponse;
import com.devrish.martcart.exception.cart.ProductNotFoundException;
import com.devrish.martcart.model.CartItem;
import com.devrish.martcart.model.Product;
import com.devrish.martcart.model.User;
import com.devrish.martcart.repository.product.ProductRepository;
import com.devrish.martcart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Component
public class CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public CartResponse getCart(User currentUser) throws Exception {
        List<CartItem> cart = currentUser.getCart();
        // hide user details of soldBy
        cart.forEach(cartItem -> {
            cartItem.getProductId().setSoldBy(
                    User.builder()
                            .firstname(cartItem.getProductId().getSoldBy().getFirstname())
                            .lastname(cartItem.getProductId().getSoldBy().getLastname())
                            .build()
            );
        });
        return new CartResponse(
                true,
                "Successfully fetched cart",
                currentUser.getCart(),
                null
        );
    }

    public CartResponse addItemToCart(AddOrRemoveItemBody body, User currentUser) throws Exception {
        Product product = productRepository.findById(body.getProductId().toString()).orElse(null);
        if (product == null) throw new ProductNotFoundException();
        List<CartItem> cart = currentUser.getCart();
        AtomicBoolean exists = new AtomicBoolean(false);
        cart.forEach(cartItem -> {
            if (body.getProductId().equals(cartItem.getProductId().get_id().toString())) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                exists.set(true);
            }
        });
        if (!exists.get()) {
            cart.add(CartItem.builder().productId(product).quantity(1L).build());
        }
        currentUser.setCart(cart);
        userRepository.save(currentUser);
        product.setSoldBy(
                User.builder()
                        .firstname(product.getSoldBy().getFirstname())
                        .lastname(product.getSoldBy().getLastname())
                        .build()
        );
        return new CartResponse(
                true,
                "Item " + product.get_id() + " added to cart",
                null,
                product
        );
    }

    public CartResponse removeItemFromCart(AddOrRemoveItemBody body, User currentUser) throws Exception {
        Product product = productRepository.findById(body.getProductId().toString()).orElse(null);
        if (product == null) throw new ProductNotFoundException();
        List<CartItem> cart = currentUser.getCart();
        cart.forEach(cartItem -> {
            if (body.getProductId().equals(cartItem.getProductId().get_id().toString())) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
            }
        });
        cart = cart.stream().filter(cartItem -> cartItem.getQuantity() > 0).collect(Collectors.toList());
        currentUser.setCart(cart);
        userRepository.save(currentUser);
        product.setSoldBy(
                User.builder()
                        .firstname(product.getSoldBy().getFirstname())
                        .lastname(product.getSoldBy().getLastname())
                        .build()
        );
        return new CartResponse(
                true,
                "Item " + product.get_id() + " removed from cart",
                null,
                product
        );
    }

    public CartResponse emptyCart(User currentUser) throws Exception {
        currentUser.setCart(new ArrayList<>());
        userRepository.save(currentUser);
        return new CartResponse(
                true,
                "Cart emptied!",
                new ArrayList<>(),
                null
        );
    }

}
