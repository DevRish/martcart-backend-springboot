package com.devrish.martcart.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    private ObjectId _id;

    @NotNull(message = "name is required")
    @NotBlank(message = "name cannot be blank")
    private String name;

    @Builder.Default
    private Long itemCount = 0L;

}
