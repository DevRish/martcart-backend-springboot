package com.devrish.martcart.util.JWT;

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
