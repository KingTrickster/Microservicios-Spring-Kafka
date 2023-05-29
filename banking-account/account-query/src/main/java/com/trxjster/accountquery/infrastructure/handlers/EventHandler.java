package com.trxjster.accountquery.infrastructure.handlers;

import com.trxjster.accountcommon.events.AccountClosedEvent;
import com.trxjster.accountcommon.events.AccountOpenedEvent;
import com.trxjster.accountcommon.events.FundsDepositedEvent;
import com.trxjster.accountcommon.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
