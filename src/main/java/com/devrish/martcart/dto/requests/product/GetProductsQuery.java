package com.devrish.martcart.dto.requests.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetProductsQuery {

    private String name;
    private Double minPrice;
    private Double maxPrice;
    private Double minRating;
    private Double maxRating;
    private ObjectId categoryId;
    private Integer page;
    private Integer limit;

    @Override
    public String toString() {
        return "GetProductsQuery{" +
                ", name='" + name + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", minRating=" + minRating +
                ", maxRating=" + maxRating +
                ", categoryId='" + categoryId + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }
}
