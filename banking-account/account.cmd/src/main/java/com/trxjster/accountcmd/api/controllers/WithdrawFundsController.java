package com.trxjster.accountcmd.api.controllers;

import com.trxjster.accountcmd.api.command.WithdrawFundsCommand;
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
@RequestMapping(path = "/api/v1/withdrawFunds")
public class WithdrawFundsController {
    private static final Logger logger = Logger.getLogger(WithdrawFundsController.class.getName());

    private final CommandDispatcher commandDispatcher;

    public WithdrawFundsController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable(value = "id") String id, @RequestBody WithdrawFundsCommand command){
        try{
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Funds withdraw was successful."), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e){
            logger.log(Level.WARNING, MessageFormat.format("Errors in request from client {0}", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            var safeErrorMessage = MessageFormat.format("Errors in request from client {0}", e.toString());
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
