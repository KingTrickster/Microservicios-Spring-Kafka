package com.trxjster.accountquery.api.queries;

import com.trxjster.accountquery.api.dto.EqualityType;
import com.trxjster.accountquery.domain.AccountRepository;
import com.trxjster.accountquery.domain.BankAccount;
import com.trxjster.cqrscore.domain.BaseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountQueryHandler implements QueryHandler{
    private final AccountRepository accountRepository;

    public AccountQueryHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        Iterable<BankAccount> bankAccounts = accountRepository.findAll();
        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccounts.forEach(bankAccountList::add);

        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        var bankAccount = accountRepository.findById(query.getId());
        if(bankAccount.isEmpty())
            return null;

        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccountList.add(bankAccount.get());
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        var bankAccount = accountRepository.findByAccountHolder(query.getAccountHolder());
        if(bankAccount.isEmpty())
            return null;

        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccountList.add(bankAccount.get());
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {
        List<BaseEntity> bankAccountList = query.getEqualityType() == EqualityType.GREATER_THAN
                ? accountRepository.findByBalanceGreaterThan(query.getBalance())
                : accountRepository.findByBalanceLessThan(query.getBalance());
        return bankAccountList;
    }
}
