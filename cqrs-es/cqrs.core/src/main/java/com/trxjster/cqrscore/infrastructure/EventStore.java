package com.trxjster.cqrscore.infrastructure;

import com.trxjster.cqrscore.events.BaseEvent;

import java.util.List;

public interface EventStore {
    void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion);
    List<BaseEvent> getEvent(String aggregateId);
}
