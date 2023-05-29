package com.trxjster.accountquery.api.dto;

import com.trxjster.accountcommon.dto.BaseResponse;
import com.trxjster.accountquery.domain.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AccountLookupResponse extends BaseResponse {
    private List<BankAccount> bankAccounts;

    public AccountLookupResponse(String message){
        super(message);
    }
}
