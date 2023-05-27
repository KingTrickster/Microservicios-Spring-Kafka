package com.trxjster.accountcmd.api.command;

import com.trxjster.accountcommon.dto.AccountType;
import com.trxjster.cqrscore.commands.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
