package com.trxjster.accountquery.api.queries;

import com.trxjster.accountquery.api.dto.EqualityType;
import com.trxjster.cqrscore.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {
    private double balance;
    private EqualityType equalityType;
}
