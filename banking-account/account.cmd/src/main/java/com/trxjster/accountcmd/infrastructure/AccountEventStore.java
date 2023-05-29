package com.trxjster.accountcmd.infrastructure;

import com.trxjster.accountcmd.domain.AccountAggregate;
import com.trxjster.accountcmd.domain.EventStoreRepository;
import com.trxjster.cqrscore.events.BaseEvent;
import com.trxjster.cqrscore.events.EventModel;
import com.trxjster.cqrscore.exceptions.AggregateNotFoundException;
import com.trxjster.cqrscore.exceptions.ConcurrencyException;
import com.trxjster.cqrscore.infrastructure.EventStore;
import com.trxjster.cqrscore.producers.EventProducer;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountEventStore implements EventStore {
    private final EventStoreRepository eventStoreRepository;
    private final EventProducer eventProducer;

    public AccountEventStore(EventStoreRepository eventStoreRepository, EventProducer eventProducer) {
        this.eventStoreRepository = eventStoreRepository;
        this.eventProducer = eventProducer;
    }

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if(expectedVersion != -1 && eventStream.get(eventStream.size() -1).getVersion() != expectedVersion){
            throw new ConcurrencyException();
        }

        var version = expectedVersion;
        for(var event: events){
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            if(!persistedEvent.getId().isEmpty()){
                eventProducer.produce(event.getClass().getSimpleName(),event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvent(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if(eventStream == null || eventStream.isEmpty()){
            throw new AggregateNotFoundException("The bank account is incorrect");
        }
        return eventStream.stream().map(x -> x.getEventData()).collect(Collectors.toList());
    }
}
