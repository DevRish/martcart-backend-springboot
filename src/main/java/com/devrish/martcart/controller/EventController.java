package com.devrish.martcart.controller;

import com.devrish.martcart.dto.responses.CategoryResponse;
import com.devrish.martcart.dto.responses.EventResponse;
import com.devrish.martcart.dto.responses.GenericResponse;
import com.devrish.martcart.exception.category.CategoryNotFoundException;
import com.devrish.martcart.service.EventService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/event")
@Slf4j
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/")
    public ResponseEntity<GenericResponse> getEvents() {
        try {
            EventResponse res = eventService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    GenericResponse.builder().status(false).message("Server Error").build()
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getEventById(
            @Valid @PathVariable @NotNull(message = "id is required") String id
    ) {
        try {
            EventResponse res = eventService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch(CategoryNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    GenericResponse.builder().status(false).message(e.getMessage()).build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    GenericResponse.builder().status(false).message("Server Error").build()
            );
        }
    }

}
