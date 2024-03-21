package com.devrish.martcart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {

    @Id
    private ObjectId _id;

    private String name;

    private String imagePath;

    private List<String> tagLines;

    private String ctaLink; // redirection link on call-to-action button

    // design related

    private String colorLight;

    private String colorDark;

    private String colorCTA;

}
