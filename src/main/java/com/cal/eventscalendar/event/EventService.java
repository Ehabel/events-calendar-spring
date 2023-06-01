package com.cal.eventscalendar.event;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Event create(CreateEventDTO data){
        Event newEvent = new Event(data.eventName, data.startDate, data.endDate, data.location, data.label);
        return this.eventRepository.save(newEvent);
    }

    public List<Event> findAll(){
        return this.eventRepository.findAll();
    }

    public Optional<Event> findById(Long id) {
        return this.eventRepository.findById(id);
    }

    public boolean deleteById(Long id){
        Optional<Event> maybePost = this.findById(id);
        if(maybePost.isPresent()){
            this.eventRepository.delete(maybePost.get());
            return true;
        }
        return false;
    }

    public Optional<Event> updateById(Long id, UpdateEventDTO data){
        Optional<Event> maybeEvent = this.findById(id);
        if(maybeEvent.isPresent()){
            Event existingEvent = maybeEvent.get();
            modelMapper.map(data, existingEvent);
            return Optional.of(this.eventRepository.save(existingEvent));
        }
        return maybeEvent;
    }
}
