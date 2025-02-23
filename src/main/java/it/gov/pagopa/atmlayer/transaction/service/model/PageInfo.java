package it.gov.pagopa.atmlayer.transaction.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Data
@AllArgsConstructor
public class PageInfo<T> {
    @Schema(minimum = "0", maximum = "10000")
    private Integer page;
    @Schema(minimum = "0", maximum = "100")
    private Integer limit;
    @Schema(minimum = "0", maximum = "1000000")
    private Integer itemsFound;
    @Schema(minimum = "0", maximum = "10000")
    private Integer totalPages;
    @Schema(type = SchemaType.ARRAY, maxItems = 10000)
    private List<T> results;
}
