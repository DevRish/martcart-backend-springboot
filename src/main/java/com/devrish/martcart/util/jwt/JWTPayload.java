package com.devrish.martcart.util.jwt;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JWTPayload {
    private String id;
}
