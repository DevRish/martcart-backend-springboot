package com.devrish.martcart.dto.requests.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetProductsParams {

    private String id;
    private String name;
    private Double minPrice;
    private Double maxPrice;
    private Double minRating;
    private Double maxRating;
    private String categoryId;
    private Long page;
    private Long limit;

    @Override
    public String toString() {
        return "GetProductsParams{" +
                "id='" + id + '\'' +
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
