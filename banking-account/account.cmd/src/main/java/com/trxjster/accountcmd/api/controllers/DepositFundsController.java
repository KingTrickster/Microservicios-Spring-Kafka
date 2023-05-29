package com.trxjster.accountcmd.api.controllers;

import com.trxjster.accountcmd.api.command.DepositFundsCommand;
import com.trxjster.accountcommon.dto.BaseResponse;
import com.trxjster.cqrscore.exceptions.AggregateNotFoundException;
import com.trxjster.cqrscore.infrastructure.CommandDispatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/depositFunds")
public class DepositFundsController {
    private static final Logger logger = Logger.getLogger(DepositFundsController.class.getName());

    private final CommandDispatcher commandDispatcher;

    public DepositFundsController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value = "id") String id, @RequestBody DepositFundsCommand command){
        try{
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Funds deposit was successful."), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e){
            logger.log(Level.WARNING, MessageFormat.format("Errors in request from client {0}", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
             var safeErrorMessage = MessageFormat.format("Errors in request from client {0}", e.toString());
             return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
