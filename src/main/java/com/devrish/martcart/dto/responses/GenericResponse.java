package com.devrish.martcart.dto.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericResponse {
    private Boolean status;
    private String message;
}
