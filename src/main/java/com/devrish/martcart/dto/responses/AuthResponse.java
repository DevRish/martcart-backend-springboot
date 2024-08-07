package com.devrish.martcart.dto.responses;

import com.devrish.martcart.model.CartItem;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class AuthResponse extends GenericResponse {
    private String token;
    private AuthUser user;

    public AuthResponse(Boolean status, String message, String token, AuthUser user) {
        super(status, message);
        this.token = token;
        this.user = user;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthUser {
        private String firstname;
        private String lastname;
        private List<CartItem> cart;
    }
}
