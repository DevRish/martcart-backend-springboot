package com.devrish.martcart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "users")
public class User {
    @Id
    private String _id;
    private String firstname;
    private String lastname;
    private String username;
    @Field(targetType = FieldType.STRING)
    private UserType userType;
    private String phone;
    private String email;
    private String password;
    @Builder.Default
    private Date joinDate = new Date(); // default value => current date
    private List<CartItem> cart;
}
