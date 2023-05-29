package com.trxjster.cqrscore.handlers;

import com.trxjster.cqrscore.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregate);
    T getById(String id);
}
