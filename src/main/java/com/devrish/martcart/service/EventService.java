package com.devrish.martcart.service;

import com.devrish.martcart.dto.responses.EventResponse;
import com.devrish.martcart.exception.event.EventNotFoundException;
import com.devrish.martcart.model.Event;
import com.devrish.martcart.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public EventResponse getAll() throws Exception {
        List<Event> events = eventRepository.findAll();
        return new EventResponse(
                true,
                "Events fetched successfully",
                null,
                events
        );
    }

    public EventResponse getById(String id) throws Exception {
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) throw new EventNotFoundException();
        return new EventResponse(
                true,
                "Event fetched successfully",
                event,
                null
        );
    }

}
