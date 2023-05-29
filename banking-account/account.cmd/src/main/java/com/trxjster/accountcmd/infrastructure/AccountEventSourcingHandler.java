package com.trxjster.accountcmd.infrastructure;

import com.trxjster.accountcmd.domain.AccountAggregate;
import com.trxjster.cqrscore.domain.AggregateRoot;
import com.trxjster.cqrscore.handlers.EventSourcingHandler;
import com.trxjster.cqrscore.infrastructure.EventStore;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {
    private final EventStore eventStore;

    public AccountEventSourcingHandler(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvent(id);
        if(events != null && !events.isEmpty()){
            aggregate.replayEvents(events);
            var latestVersion = events.stream().map(x -> x.getVersion()).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }

        return aggregate;
    }
}
