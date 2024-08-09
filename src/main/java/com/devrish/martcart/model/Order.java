package com.devrish.martcart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;

@Document(collection = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    private ObjectId _id;

    @Field(targetType = FieldType.OBJECT_ID)
    @DocumentReference
    private Product productId; // Reference to Product

    @Field(targetType = FieldType.OBJECT_ID)
    @DocumentReference
    private User userId; // Reference to User

    private Long quantity;

    private String address;

    private Double totalPrice;

    @Builder.Default
    private Date orderedAt = new Date();

}
