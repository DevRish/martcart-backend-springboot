package com.devrish.martcart.util.JWT;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JWTPayload {
    private String id;
}
