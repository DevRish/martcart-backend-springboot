package com.devrish.martcart.service;

import com.devrish.martcart.dto.requests.product.GetProductsQuery;
import com.devrish.martcart.dto.responses.ProductResponse;
import com.devrish.martcart.exception.cart.ProductNotFoundException;
import com.devrish.martcart.model.Product;
import com.devrish.martcart.model.User;
import com.devrish.martcart.repository.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StorageService storageService;

    public ProductResponse getById(String id) throws Exception {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) throw new ProductNotFoundException();
        return new ProductResponse(
                true,
                "Product fetched successfully",
                product,
                null,
                1L
        );
    }

    public ProductResponse getAll(GetProductsQuery reqQuery) throws Exception {
        Page<Product> page = productRepository.findAllDynamicQuery(reqQuery);
        return new ProductResponse(
                true,
                "Products fetched successfully",
                null,
                page.getContent(),
                page.getTotalElements()
        );
    }

    public ProductResponse createProduct(Product product, MultipartFile image) throws Exception {
        product = productRepository.save(product);
        final String filename = product.get_id().toString() + "." + StringUtils.getFilenameExtension(image.getOriginalFilename());
        final String filepath = "images/" + filename;
        log.info("Filepath: {}", filepath);
        storageService.uploadFile(filepath, image);
        product.setImagePath("/" + filepath);
        product = productRepository.save(product);
        product.setSoldBy(
                User.builder()
                        .firstname(product.getSoldBy().getFirstname())
                        .lastname(product.getSoldBy().getLastname())
                        .build()
        );
        return new ProductResponse(
                true,
                "Product created successfully",
                product,
                null,
                1L
        );
    }

    public Resource getImageById(String id) throws Exception {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) throw new ProductNotFoundException();
        String key = product.getImagePath().substring(1);
        return storageService.getFile(key);
    }

}
