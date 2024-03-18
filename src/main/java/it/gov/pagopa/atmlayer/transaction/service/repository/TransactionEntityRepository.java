package it.gov.pagopa.atmlayer.transaction.service.repository;

import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.transaction.service.entity.TransactionEntity;
import it.gov.pagopa.atmlayer.transaction.service.model.PageInfo;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class TransactionEntityRepository implements PanacheRepositoryBase<TransactionEntity, String> {

    public Uni<PageInfo<TransactionEntity>> findByFilters(Map<String, Object> params, int pageIndex, int pageSize) {
        String queryFilters = params.keySet().stream().map(key -> ("t." + key + " = :" + key)
        ).collect(Collectors.joining(" and "));
        PanacheQuery<TransactionEntity> queryResult = find(("select t from TransactionEntity t").concat(queryFilters.isBlank() ? "" : " where " + queryFilters).concat(" order by t.lastUpdatedAt DESC"), params).page(Page.of(pageIndex, pageSize));
        return queryResult.count()
                .onItem().transformToUni(count -> {
                    int totalCount = count.intValue();
                    int totalPages = (int) Math.ceil((double) totalCount / pageSize);
                    return queryResult.list()
                            .onItem()
                            .transform(list -> new PageInfo<TransactionEntity>(pageIndex, pageSize, totalCount, totalPages, list));
                });
    }

}
