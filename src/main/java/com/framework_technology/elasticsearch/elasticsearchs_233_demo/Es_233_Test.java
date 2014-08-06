package com.framework_technology.elasticsearch.elasticsearchs_233_demo;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.Facets;
import org.elasticsearch.search.facet.terms.TermsFacet;

import java.util.*;

/**
 * Created by lw on 14-7-15.
 * <p>
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 官方JAVA-API
 * http://www.elasticsearch.org/guide/en/elasticsearch/client/java-api/current/index.html
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class Es_233_Test {

    private static final String[] INDEXS = {"recent_ezsonar_2014-08-06_13-24"};

    public static void main(String[] dfd) {
        Es_233_Utils.startupClient();
        try {
            //Es_233_Utils.getAllIndices();
            QueryBuilder queryBuilder = Es_233_QueryBuilders_DSL.matchAllQuery();
            //SearchResponse searchResponse = Es_233_Search.builderSearchResponse(Es_233_Search.builderSearchRequestBuilder("message", queryBuilder, "recent_ezsonar_2014-07-23_16-52"));
            // Es_233_Utils.writeSearchResponse(searchResponse);
            Es_233_Search es_233_search = new Es_233_Search(Es_233_Utils.client);
            SearchRequestBuilder builder = es_233_search.builderSearchRequestBuilderByIndex("message", INDEXS)
                    .setQuery(queryBuilder)
                    .addFacet(FacetBuilders.termsFacet("TermsFacetBuilder")
                            .field("_start_at")
                            .size(Integer.MAX_VALUE));

            //Es_233_Utils.writeSearchResponseToMap(es_233_search.builderSearchResponse(builder), "_start_at");

            Facets facets = es_233_search.builderSearchResponse(builder).getFacets();
            Map facets_map = facets.getFacets();

            System.out.println();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("TermsFacet-API");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            TermsFacet termsFacet = (TermsFacet) facets_map.get("TermsFacetBuilder");
            Set<String> stringSet = new TreeSet<>();

            for (TermsFacet.Entry entry : termsFacet) {

                String s = Es_233_Utils.DATETIME_FORMATTER.format(new Date(Long.parseLong(entry.getTerm().toString()) * 1000L));
                stringSet.add(s);
                // Term -》Doc count
                //System.out.println("key :" + s + "\t value:" + entry.getCount());
            }
            String[] strings = stringSet.toArray(new String[0]);
            System.out.println("当天索引时间最小值：" + strings[0]);
            System.out.println("当天索引时间最大值：" + strings[strings.length - 1]);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Es_233_Utils.shutDownClient();
        }
    }
}
