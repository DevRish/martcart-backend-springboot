package com.devrish.martcart.dto.requests.auth;

import com.devrish.martcart.model.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern.Flag;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupBody {

    @NotNull(message = "firstname is required")
    @Size(min = 2, max = 100, message = "firstname must be between 2 to 100 characters long")
    private String firstname;

    @NotNull(message = "lastname is required")
    @Size(min = 2, max = 100, message = "lastname must be between 2 to 100 characters long")
    private String lastname;

    @NotNull(message = "username is required")
    @NotBlank(message = "username cannot be blank")
    private String username;

    @NotNull(message = "phone number is required")
    @Size(min = 10, max = 10, message = "phone number is invalid (must have 10 digits)")
    private String phone;

    @NotNull(message = "email is required")
    @Email(message = "The email address is invalid.", flags = { Flag.CASE_INSENSITIVE })
    private String email;

    @NotNull(message = "password is required")
    @Size(min = 6, message = "password must be at least 6 characters long")
    private String password;

    @NotNull(message = "userType is required")
    private UserType userType;
}
