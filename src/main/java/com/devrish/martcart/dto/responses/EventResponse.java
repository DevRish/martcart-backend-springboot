package com.devrish.martcart.dto.responses;

import com.devrish.martcart.model.Event;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EventResponse extends GenericResponse {

    private Event event;
    private List<Event> events;

    public EventResponse(Boolean status, String message, Event event, List<Event> events) {
        super(status, message);
        this.event = event;
        this.events = events;
    }
}
