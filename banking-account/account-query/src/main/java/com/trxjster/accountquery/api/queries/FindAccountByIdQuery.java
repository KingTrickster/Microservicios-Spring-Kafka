package com.trxjster.accountquery.api.queries;

import com.trxjster.cqrscore.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountByIdQuery extends BaseQuery {
    private String id;
}
