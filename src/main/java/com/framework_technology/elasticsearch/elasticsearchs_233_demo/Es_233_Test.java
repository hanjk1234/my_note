package com.framework_technology.elasticsearch.elasticsearchs_233_demo;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * Created by lw on 14-7-15.
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 官方JAVA-API
 * http://www.elasticsearch.org/guide/en/elasticsearch/client/java-api/current/index.html
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class Es_233_Test {

    private static final String[] INDEXS = {"recent_ezsonar_2014-07-25_15-12"};

    public static void main(String[] dfd) {
        Es_233_Utils.startupClient();
        try {
            //Es_233_Utils.getAllIndices();
            QueryBuilder queryBuilder = Es_233_QueryBuilders_DSL.matchAllQuery();
            //SearchResponse searchResponse = Es_233_Search.builderSearchResponse(Es_233_Search.builderSearchRequestBuilder("message", queryBuilder, "recent_ezsonar_2014-07-23_16-52"));
            // Es_233_Utils.writeSearchResponse(searchResponse);
            Es_233_Search es_233_search = new Es_233_Search(Es_233_Utils.client);
            SearchRequestBuilder builder = es_233_search.builderSearchRequestBuilderByIndex("message", INDEXS)
                    .setQuery(queryBuilder);
            Es_233_Utils.writeSearchResponseToMap(es_233_search.builderSearchResponse(builder), "_start_at");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Es_233_Utils.shutDownClient();
        }
    }
}
