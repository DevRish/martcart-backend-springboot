package com.devrish.martcart.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
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

    @NotNull(message = "name is required")
    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotNull(message = "currentPrice is required")
    private Double currentPrice;

    @Builder.Default
    private Double rating = -1.0;

    @NotNull(message = "category (id) is required")
    @DocumentReference
    private Category category; // Reference to Category

    @DocumentReference
    private User soldBy; // Reference to User

    // Non-Searchable Data

    @NotNull(message = "originalPrice is required")
    private Double originalPrice;

    @Builder.Default
    private String imagePath = "";

    @Builder.Default
    private List<ChoiceItem> choices = new ArrayList<>();

    @Builder.Default
    private List<SpecificationItem> specifications = new ArrayList<>();

}
