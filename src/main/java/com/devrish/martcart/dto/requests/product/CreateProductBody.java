package com.devrish.martcart.dto.requests.product;

import com.devrish.martcart.model.ChoiceItem;
import com.devrish.martcart.model.SpecificationItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductBody {
    private String name;
    private Double currentPrice;

    private Double originalPrice;

    private String categoryId;

    private List<ChoiceItem> choices;

    private List<SpecificationItem> specifications;
}
