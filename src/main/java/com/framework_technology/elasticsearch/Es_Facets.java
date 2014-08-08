package com.framework_technology.elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.Facets;
import org.elasticsearch.search.facet.filter.FilterFacet;
import org.elasticsearch.search.facet.filter.FilterFacetBuilder;
import org.elasticsearch.search.facet.geodistance.GeoDistanceFacet;
import org.elasticsearch.search.facet.geodistance.GeoDistanceFacetBuilder;
import org.elasticsearch.search.facet.histogram.HistogramFacet;
import org.elasticsearch.search.facet.histogram.HistogramFacetBuilder;
import org.elasticsearch.search.facet.query.QueryFacet;
import org.elasticsearch.search.facet.query.QueryFacetBuilder;
import org.elasticsearch.search.facet.range.RangeFacet;
import org.elasticsearch.search.facet.range.RangeFacetBuilder;
import org.elasticsearch.search.facet.statistical.StatisticalFacet;
import org.elasticsearch.search.facet.statistical.StatisticalFacetBuilder;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.elasticsearch.search.facet.terms.TermsFacetBuilder;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by lw on 14-7-15.
 * <p>
 * 搜索 Facets分组统计
 * <p>
 * <a>http://www.elasticsearch.org/guide/en/elasticsearch/client/java-api/current/java-facets.html</a>
 */
public class Es_Facets {

    /**
     * 搜索，Query搜索API
     * Facets 查询-对搜索结果进行计算处理API
     */
    protected static void searchByQuery_Facets() {


        TermsFacetBuilder termsFacetBuilder = FacetBuilders.termsFacet("TermsFacetBuilder")
                .field("name")
                .size(Integer.MAX_VALUE);//取得 name分组后COUNT值，显示size值


        RangeFacetBuilder rangeFacetBuilder = FacetBuilders.rangeFacet("RangeFacetBuilder")
                .field("age")
                .addRange(20, 30);//取得age-》20到30值的结果


        HistogramFacetBuilder histogramFacetBuilder = FacetBuilders.histogramFacet("HistogramFacetBuilder")
                .field("birthday") //生日分组统计
                .interval(1, TimeUnit.MINUTES); //按分钟数分组


        FilterFacetBuilder filterFacetBuilder = FacetBuilders.filterFacet("FilterFacetBuilder",
                FilterBuilders.termFilter("name", "葫芦747娃"));    // Your Filter here


        QueryFacetBuilder queryFacetBuilder = FacetBuilders.queryFacet("QueryFacetBuilder",
                QueryBuilders.matchQuery("age", 29));


        StatisticalFacetBuilder statisticalFacetBuilder = FacetBuilders.statisticalFacet("StatisticalFacetBuilder")
                .field("height");


        GeoDistanceFacetBuilder geoDistanceFacetBuilder = FacetBuilders.geoDistanceFacet("GeoDistanceFacetBuilder")
                .field("location")                   // Field containing coordinates we want to compare with
                .point(40, -70)                     // Point from where we start (0)
                .addUnboundedFrom(10)               // 0 to 10 km (excluded)
                .addRange(10, 20)                   // 10 to 20 km (excluded)
                .addRange(20, 100)                  // 20 to 100 km (excluded)
                .addUnboundedTo(100)                // from 100 km to infinity (and beyond ;-) )
                .unit(DistanceUnit.DEFAULT);     // All distances are in kilometers. Can be MILES


        /**
         * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
         * prepareSearch 执行
         * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
         */
        SearchResponse response = Es_Utils.client.prepareSearch(Es_Utils.INDEX_DEMO_01)
                .setTypes(Es_Utils.INDEX_DEMO_01_MAPPING)
                .setQuery(QueryBuilders.termQuery("age", 29))
                        //.setPostFilter(FilterBuilders.rangeFilter("age").gt(98))
                .addFacet(termsFacetBuilder)
                .addFacet(rangeFacetBuilder)
                .addFacet(histogramFacetBuilder)
                .addFacet(filterFacetBuilder)
                .addFacet(queryFacetBuilder)
                .addFacet(statisticalFacetBuilder)
                .addFacet(geoDistanceFacetBuilder)
                .setSize(1000)
                .execute()
                .actionGet();

        /**
         * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
         * prepareSearch 结果集
         * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
         */
        Es_Utils.writeSearchResponse(response);


        Facets facets = response.getFacets();
        Map facets_map = facets.getFacets();

        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("TermsFacet-API");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        TermsFacet termsFacet = (TermsFacet) facets_map.get("TermsFacetBuilder");
        System.out.println("termsFacet.getTotalCount():" + termsFacet.getTotalCount());// Total terms doc count
        System.out.println("termsFacet.getOtherCount():" + termsFacet.getOtherCount());// Not shown terms doc count
        System.out.println("termsFacet.getMissingCount():" + termsFacet.getMissingCount());// Without term doc count
        for (TermsFacet.Entry entry : termsFacet) {
            // Term -》Doc count
            System.out.println("key :" + entry.getTerm() + "\t value:" + entry.getCount());
        }

        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("RangeFacet-API");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        RangeFacet rangeFacet = (RangeFacet) facets_map.get("RangeFacetBuilder");
        for (RangeFacet.Entry entry : rangeFacet) {
            // sr is here your SearchResponse object
            entry.getFrom();    // Range from requested
            entry.getTo();      // Range to requested
            entry.getCount();   // Doc count
            entry.getMin();     // Min value
            entry.getMax();     // Max value
            entry.getMean();    // Mean
            entry.getTotal();   // Sum of values
        }

        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("HistogramFacet-API");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        HistogramFacet histogramFacet = (HistogramFacet) facets_map.get("HistogramFacetBuilder");
        // For each entry -Key (X-Axis) -Doc count (Y-Axis)
        for (HistogramFacet.Entry entry : histogramFacet) {
            System.out.println("entry.getKey()->" + entry.getKey() + "\t entry.getCount()->" + entry.getCount());
        }

        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("FilterFacet-API");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        FilterFacet filterFacet = (FilterFacet) facets_map.get("FilterFacetBuilder");
        System.out.println("filterFacet.getCount()->" + filterFacet.getCount());// Number of docs that matched


        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("QueryFacet-API");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        QueryFacet queryFacet = (QueryFacet) facets_map.get("QueryFacetBuilder");
        System.out.println("queryFacet.getCount()->" + queryFacet.getCount());// Number of docs that matched


        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("StatisticalFacet-API");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        StatisticalFacet statisticalFacet = (StatisticalFacet) facets_map.get("StatisticalFacetBuilder");
        statisticalFacet.getCount();           // Doc count
        statisticalFacet.getMin();             // Min value
        statisticalFacet.getMax();             // Max value
        statisticalFacet.getMean();            // Mean
        statisticalFacet.getTotal();           // Sum of values
        statisticalFacet.getStdDeviation();    // Standard Deviation
        statisticalFacet.getSumOfSquares();    // Sum of Squares
        statisticalFacet.getVariance();        // Variance


        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~@Notsolved~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("GeoDistanceFacet-API -[40, -70]地理距离");
        System.out.println("~~~~~~~~~~~~~~~~~~~@Notsolved~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        GeoDistanceFacet geoDistanceFacet = (GeoDistanceFacet) facets_map.get("GeoDistanceFacetBuilder");
        // For each entry
        for (GeoDistanceFacet.Entry entry : geoDistanceFacet) {
            entry.getFrom();            // Distance from requested
            entry.getTo();              // Distance to requested
            entry.getCount();           // Doc count
            entry.getMin();             // Min value
            entry.getMax();             // Max value
            entry.getTotal();           // Sum of values
            entry.getMean();            // Mean
        }

    }
}
