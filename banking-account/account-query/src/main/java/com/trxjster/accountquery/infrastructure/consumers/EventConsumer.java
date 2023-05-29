package com.trxjster.accountquery.infrastructure.consumers;

import com.trxjster.accountcommon.events.AccountClosedEvent;
import com.trxjster.accountcommon.events.AccountOpenedEvent;
import com.trxjster.accountcommon.events.FundsDepositedEvent;
import com.trxjster.accountcommon.events.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload AccountOpenedEvent event, Acknowledgment ack);
    void consume(@Payload FundsDepositedEvent event, Acknowledgment ack);
    void consume(@Payload FundsWithdrawnEvent event, Acknowledgment ack);
    void consume(@Payload AccountClosedEvent event, Acknowledgment ack);
}
