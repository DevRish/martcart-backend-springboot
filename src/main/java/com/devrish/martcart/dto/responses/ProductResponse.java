package com.devrish.martcart.dto.responses;

import com.devrish.martcart.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductResponse extends GenericResponse {
    private Product product;
    private List<Product> products;

    private Long total;

    public ProductResponse(Boolean status, String message, Product product, List<Product> products, Long total) {
        super(status, message);
        this.product = product;
        this.products = products;
        this.total = total;
    }
}
