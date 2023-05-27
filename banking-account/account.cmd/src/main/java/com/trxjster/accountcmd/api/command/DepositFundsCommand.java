package com.trxjster.accountcmd.api.command;

import com.trxjster.cqrscore.commands.BaseCommand;
import lombok.Data;

@Data
public class DepositFundsCommand extends BaseCommand {
    private double amount;
}
