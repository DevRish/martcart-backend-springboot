package com.devrish.martcart.repository.product;

import com.devrish.martcart.dto.requests.product.GetProductsQuery;
import com.devrish.martcart.model.Product;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Page<Product> findAllDynamicQuery(GetProductsQuery reqQuery) {
        // extract request query fields
        String name = reqQuery.getName();
        Double minPrice = reqQuery.getMinPrice();
        Double maxPrice = reqQuery.getMaxPrice();
        Double minRating = reqQuery.getMinRating();
        Double maxRating = reqQuery.getMaxRating();
        ObjectId categoryId = reqQuery.getCategoryId();
        Integer page = reqQuery.getPage();
        Integer limit = reqQuery.getLimit();
        if (page == null) page = Integer.valueOf(1);
        if (limit == null) limit = Integer.valueOf(5);

        // Dynamic Query generation with criteria, and querying database through MongoTemplate
        Query dbQuery = new Query();

        if (name != null && !name.isEmpty()) {
            dbQuery.addCriteria(Criteria.where("name").regex(name, "i"));
        }
        if (minPrice != null && maxPrice != null) {
            dbQuery.addCriteria(Criteria.where("currentPrice").gte(minPrice).lte(maxPrice));
        }
        if (minRating != null && maxRating != null) {
            dbQuery.addCriteria(Criteria.where("rating").gte(minRating).lte(maxRating));
        }
        if (categoryId != null && categoryId != null) {
            dbQuery.addCriteria(Criteria.where("category").is(categoryId));
        }

        long total = mongoTemplate.count(dbQuery, Product.class);

        // Pagination
        Pageable pageable = PageRequest.of(page - 1, limit);
        dbQuery.with(pageable);

        List<Product> products = mongoTemplate.find(dbQuery, Product.class);

        return new PageImpl<>(products, pageable, total);
    }
}
