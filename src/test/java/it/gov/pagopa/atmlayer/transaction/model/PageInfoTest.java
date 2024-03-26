package it.gov.pagopa.atmlayer.transaction.model;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.transaction.service.model.PageInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@QuarkusTest
class PageInfoTest {

    @Test
    void testCanEqual() {
        PageInfo<Object> pageInfo = new PageInfo<>(1, 1, 42, 1, new ArrayList<>());
        assertEquals(pageInfo, new PageInfo<>(1, 1, 42, 1, new ArrayList<>()));
    }

    @Test
    void testConstructor() {
        ArrayList<Object> results = new ArrayList<>();
        PageInfo<Object> actualPageInfo = new PageInfo<>(1, 1, 42, 1, results);
        actualPageInfo.setItemsFound(42);
        actualPageInfo.setLimit(1);
        actualPageInfo.setPage(1);
        ArrayList<Object> results2 = new ArrayList<>();
        actualPageInfo.setResults(results2);
        actualPageInfo.setTotalPages(1);
        String actualToStringResult = actualPageInfo.toString();
        Integer actualItemsFound = actualPageInfo.getItemsFound();
        Integer actualLimit = actualPageInfo.getLimit();
        Integer actualPage = actualPageInfo.getPage();
        List<Object> actualResults = actualPageInfo.getResults();
        Integer actualTotalPages = actualPageInfo.getTotalPages();
        assertEquals("PageInfo(page=1, limit=1, itemsFound=42, totalPages=1, results=[])", actualToStringResult);
        assertEquals(1, actualLimit.intValue());
        assertEquals(1, actualPage.intValue());
        assertEquals(1, actualTotalPages.intValue());
        assertEquals(42, actualItemsFound.intValue());
        assertEquals(results, actualResults);
        assertSame(results2, actualResults);
    }

    @Test
    void testEquals() {
        PageInfo<Object> pageInfo = new PageInfo<>(1, 1, 42, 1, new ArrayList<>());
        PageInfo<Object> pageInfo2 = new PageInfo<>(1, 1, 42, 1, new ArrayList<>());

        assertEquals(pageInfo, pageInfo2);
        int expectedHashCodeResult = pageInfo.hashCode();
        assertEquals(expectedHashCodeResult, pageInfo2.hashCode());
    }

}
