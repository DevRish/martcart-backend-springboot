package com.devrish.martcart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "products")
public class Product {

    @Id
    private ObjectId _id;

    // Searchable Data

    private String name;

    private Double currentPrice;

    @Builder.Default
    private Double rating = -1.0;

    @Field(targetType = FieldType.OBJECT_ID)
    @DBRef
    private Category category; // Reference to Category

    @Field(targetType = FieldType.OBJECT_ID)
    @DBRef
    private User soldBy; // Reference to User

    // Non-Searchable Data

    private Double originalPrice;

    private String imagePath;

    private List<ChoiceItem> choices;

    private List<SpecificationItem> specifications;

}
