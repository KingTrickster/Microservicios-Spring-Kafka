package com.trxjster.accountcmd.api.command;

import com.trxjster.cqrscore.commands.BaseCommand;
import lombok.Data;

@Data
public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand(String id){
        super(id);
    }
}
