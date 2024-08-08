package com.devrish.martcart.dto.responses;

import com.devrish.martcart.model.CartItem;
import com.devrish.martcart.model.Product;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
public class CartResponse extends GenericResponse {

    private List<CartItem> cart;
    private Product productId;

    public CartResponse(Boolean status, String message, List<CartItem> cart, Product productId) {
        super(status, message);
        this.cart = cart;
        this.productId = productId;
    }
}
