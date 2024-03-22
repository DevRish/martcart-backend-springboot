package com.devrish.martcart.dto.requests.auth;

import com.devrish.martcart.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupBody {
    private String firstname;
    private String lastname;
    private String username;
    private String phone;
    private String email;
    private String password;
    private UserType userType;
}
