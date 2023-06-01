package com.cal.eventscalendar.event;

import com.cal.eventscalendar.exceptions.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService service;

    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody CreateEventDTO data){
        Event createdEvent = this.service.create(data);
        return new ResponseEntity<Event>(createdEvent, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Event>> findAll(){
        return new ResponseEntity<List<Event>>(this.service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> findById(@PathVariable Long id){
        if(this.service.findById(id).isEmpty()){
            throw new NotFoundException("Event could not be found with id: " + id);
        }
        return new ResponseEntity<Event>(this.service.findById(id).get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Event> deleteById(@PathVariable Long id){
        boolean deleted = this.service.deleteById(id);
        if(deleted){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Event> updateById(@PathVariable Long id, @Valid @RequestBody UpdateEventDTO data){
        Optional<Event> updated = this.service.updateById(id, data);
        if(updated.isPresent()){
            return new ResponseEntity<Event>(updated.get(), HttpStatus.OK);
        }
        throw new NotFoundException("Could not find post with id: " + id);
    }
}
