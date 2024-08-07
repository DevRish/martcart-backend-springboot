package com.devrish.martcart.dto.responses;

import com.devrish.martcart.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse extends GenericResponse {
    private User user;

    public UserResponse(Boolean status, String message, User user) {
        super(status, message);
        this.user = user;
    }
}
