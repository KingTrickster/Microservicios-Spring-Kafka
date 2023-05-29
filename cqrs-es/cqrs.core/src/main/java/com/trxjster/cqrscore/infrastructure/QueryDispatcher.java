package com.trxjster.cqrscore.infrastructure;

import com.trxjster.cqrscore.domain.BaseEntity;
import com.trxjster.cqrscore.queries.BaseQuery;
import com.trxjster.cqrscore.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> List<U> send(BaseQuery query);
}
