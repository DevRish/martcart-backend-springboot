package com.devrish.martcart.dto.requests.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginBody {

    @NotNull(message = "username is required")
    private String username;

    @NotNull(message = "password is required")
    @Size(min = 6, message = "password must be at least 6 characters long")
    private String password;

}
