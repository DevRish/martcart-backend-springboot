package com.devrish.martcart.dto.jwt;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JWTHeader {
    private String alg;
    private String typ;

}
