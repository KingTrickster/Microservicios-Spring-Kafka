package com.trxjster.accountquery.api.controllers;

import com.trxjster.accountquery.api.dto.AccountLookupResponse;
import com.trxjster.accountquery.api.dto.EqualityType;
import com.trxjster.accountquery.api.queries.FindAccountByHolderQuery;
import com.trxjster.accountquery.api.queries.FindAccountByIdQuery;
import com.trxjster.accountquery.api.queries.FindAccountWithBalanceQuery;
import com.trxjster.accountquery.api.queries.FindAllAccountsQuery;
import com.trxjster.accountquery.domain.BankAccount;
import com.trxjster.cqrscore.infrastructure.QueryDispatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "api/v1/bankAccountLookup")
public class AccountLookupController {
    private static final Logger logger = Logger.getLogger(AccountLookupController.class.getName());

    private final QueryDispatcher queryDispatcher;

    public AccountLookupController(QueryDispatcher queryDispatcher) {
        this.queryDispatcher = queryDispatcher;
    }

    @GetMapping(path = "/")
    public ResponseEntity<AccountLookupResponse> getAllAccounts(){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountsQuery());
            if(accounts == null || accounts.size() == 0){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            var response = AccountLookupResponse.builder()
                    .bankAccounts(accounts)
                    .message(MessageFormat.format("Consult made successfully", null))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            var safeErrorMessage = "Errors while executing consult";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byId/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable(value = "id") String id){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
            if(accounts == null || accounts.size() == 0){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            var response = AccountLookupResponse.builder()
                    .bankAccounts(accounts)
                    .message(MessageFormat.format("Consult made successfully", null))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            var safeErrorMessage = "Errors while executing consult";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byHolder/{accountHolder}")
    public ResponseEntity<AccountLookupResponse> getAccountByAccountHolder(@PathVariable(value = "accountHolder") String accountHolder){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));
            if(accounts == null || accounts.size() == 0){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            var response = AccountLookupResponse.builder()
                    .bankAccounts(accounts)
                    .message(MessageFormat.format("Consult made successfully", null))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            var safeErrorMessage = "Errors while executing consult";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/withBalance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookupResponse> getAccountWithBalance(@PathVariable(value = "equalityType") EqualityType equalityType,
                                                                       @PathVariable(value = "balance") double balance){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountWithBalanceQuery(balance, equalityType));
            if(accounts == null || accounts.size() == 0){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            var response = AccountLookupResponse.builder()
                    .bankAccounts(accounts)
                    .message(MessageFormat.format("Consult made successfully", null))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            var safeErrorMessage = "Errors while executing consult";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
