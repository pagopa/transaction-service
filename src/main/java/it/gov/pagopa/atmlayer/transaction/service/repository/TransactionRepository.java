package it.gov.pagopa.atmlayer.transaction.service.repository;

import io.netty.handler.codec.http.multipart.AbstractDiskHttpData;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.transaction.service.entity.TransactionEntity;
import it.gov.pagopa.atmlayer.transaction.service.model.PageInfo;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class TransactionRepository implements PanacheRepositoryBase<TransactionEntity, String> {

    public Uni<PageInfo<TransactionEntity>> findByFilters(Map<String, Object> params, int pageIndex, int pageSize) {

        String queryFilters = params.keySet().stream()
                .map(key -> "startTime".equals(key) ? "t.createdAt >= :" + key : ("endTime".equals(key) ? "t.createdAt <= :" + key : "t." + key + " = :" + key))
                .collect(Collectors.joining(" and "));
//          "t.branchId = :branchId and ... and t.createdAt >= :startTime and t.createdAt <= :endTime"

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

    public Uni<List<TransactionEntity>> findOlderThanThreeMonths() {
        LocalDateTime threeMonthsAgoDateTime = LocalDateTime.now().minusMonths(3);
        Timestamp threeMonthsAgoTimestamp = Timestamp.valueOf(threeMonthsAgoDateTime);
        PanacheQuery<TransactionEntity> result = find("select t from TransactionEntity t where t.createdAt < ?1", threeMonthsAgoTimestamp);
        return result.list().onItem().transform(ArrayList::new);
    }

}
