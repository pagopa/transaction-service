package it.gov.pagopa.atmlayer.transaction.service.repository;

import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.transaction.service.entity.TransactionEntity;
import it.gov.pagopa.atmlayer.transaction.service.model.PageInfo;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class TransactionRepository implements PanacheRepositoryBase<TransactionEntity, String> {

    @ConfigProperty(name = "transaction.oldTransactions.monthsToSubtract")
    Integer timeToSubtract;

    public Uni<List<TransactionEntity>> findOldTransactions() {
        LocalDateTime dateTime = LocalDateTime.now().minusMonths(timeToSubtract);
        Timestamp timestamp = Timestamp.valueOf(dateTime);
        PanacheQuery<TransactionEntity> result = find("select t from TransactionEntity t where t.createdAt < ?1", timestamp);
        return result.list().onItem().transform(ArrayList::new);
    }

    public Uni<PageInfo<TransactionEntity>> findByFilters(Map<String, Object> params, int pageIndex, int pageSize) {

        String queryFilters = params.entrySet().stream()
                .map(entry -> {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if ("startTime".equals(key)) {
                        return "t.createdAt >= :" + key;
                    } else if ("endTime".equals(key)) {
                        return "t.createdAt <= :" + key;
                    } else if (value instanceof String) {
                        return "LOWER(t." + key + ") LIKE LOWER(CONCAT('%', :" + key + ", '%'))";
                    } else {
                        return "t." + key + " = :" + key;
                    }
                })
                .collect(Collectors.joining(" and "));

        PanacheQuery<TransactionEntity> queryResult = find(("select t from TransactionEntity t")
                .concat(queryFilters.isBlank() ? "" : " where " + queryFilters)
                .concat(" order by t.lastUpdatedAt DESC"), params)
                .page(Page.of(pageIndex, pageSize));

        return queryResult.count()
                .onItem().transformToUni(count -> {
                    int totalCount = count.intValue();
                    int totalPages = (int) Math.ceil((double) totalCount / pageSize);
                    return queryResult.list()
                            .onItem()
                            .transform(list -> new PageInfo<>(pageIndex, pageSize, totalCount, totalPages, list));
                });
    }

}
