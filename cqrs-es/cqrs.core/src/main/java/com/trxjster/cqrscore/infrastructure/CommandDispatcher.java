package com.trxjster.cqrscore.infrastructure;

import com.trxjster.cqrscore.commands.BaseCommand;
import com.trxjster.cqrscore.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}
