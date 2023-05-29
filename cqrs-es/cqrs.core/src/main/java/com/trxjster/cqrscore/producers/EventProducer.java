package com.trxjster.cqrscore.producers;

import com.trxjster.cqrscore.events.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
