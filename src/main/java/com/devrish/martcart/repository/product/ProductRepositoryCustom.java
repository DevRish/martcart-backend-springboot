package com.devrish.martcart.repository.product;

import com.devrish.martcart.dto.requests.product.GetProductsQuery;
import com.devrish.martcart.model.Category;
import com.devrish.martcart.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<Product> findAllDynamicQuery(GetProductsQuery reqQuery);
}
