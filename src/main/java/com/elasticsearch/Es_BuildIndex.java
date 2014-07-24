package com.elasticsearch;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by lw on 14-7-7.
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * mapping创建
 * 添加记录到es
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class Es_BuildIndex {


    /**
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 索引的mapping
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 预定义一个索引的mapping,使用mapping的好处是可以个性的设置某个字段等的属性
     * Es_Setting.INDEX_DEMO_01类似于数据库
     * mapping 类似于预设某个表的字段类型
     * <p/>
     * Mapping,就是对索引库中索引的字段名及其数据类型进行定义，类似于关系数据库中表建立时要定义字段名及其数据类型那样，
     * 不过es的 mapping比数据库灵活很多，它可以动态添加字段。
     * 一般不需要要指定mapping都可以，因为es会自动根据数据格式定义它的类型，
     * 如果你需要对某 些字段添加特殊属性（如：定义使用其它分词器、是否分词、是否存储等），就必须手动添加mapping。
     * 有两种添加mapping的方法，一种是定义在配 置文件中，一种是运行时手动提交mapping，两种选一种就行了。
     *
     * @throws Exception
     */
    protected static void buildIndexMapping() throws Exception {
        //在本例中主要得注意,ttl及timestamp如何用java ,这些字段的具体含义,请去到es官网查看
        CreateIndexRequestBuilder cib = Es_Utils.client.admin().indices().prepareCreate(Es_Utils.INDEX_DEMO_01);
        XContentBuilder mapping = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("we3r")//
                .startObject("_ttl")//有了这个设置,就等于在这个给索引的记录增加了失效时间,
                        //ttl的使用地方如在分布式下,web系统用户登录状态的维护.
                .field("enabled", true)//默认的false的
                .field("default", "5m")//默认的失效时间,d/h/m/s 即天/小时/分钟/秒
                .field("store", "yes")
                .field("index", "not_analyzed")
                .endObject()
                .startObject("_timestamp")//这个字段为时间戳字段.即你添加一条索引记录后,自动给该记录增加个时间字段(记录的创建时间),搜索中可以直接搜索该字段.
                .field("enabled", true)
                .field("store", "no")
                .field("index", "not_analyzed")
                .endObject()
                        //properties下定义的name等等就是属于我们需要的自定义字段了,相当于数据库中的表字段 ,此处相当于创建数据库表
                .startObject("properties")
                .startObject("name").field("type", "string").field("store", "yes").endObject()
                .startObject("home").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("now_home").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("height").field("type", "double").endObject()
                .startObject("age").field("type", "integer").endObject()
                .startObject("birthday").field("type", "date").field("format", "YYYY-MM-dd").endObject()
                .startObject("isRealMen").field("type", "boolean").endObject()
                .startObject("location").field("lat", "double").field("lon", "double").endObject()
                .endObject()
                .endObject()
                .endObject();
        cib.addMapping(Es_Utils.INDEX_DEMO_01_MAPPING, mapping);
        cib.execute().actionGet();
    }


    /**
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 添加记录到es
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 增加索引记录
     *
     * @param user 添加的记录
     * @throws Exception
     */
    protected static void buildIndex(User user) throws Exception {
        // productindex_liwei为上个方法中定义的索引,prindextype为类型.jk8231为id,以此可以代替memchche来进行数据的缓存
        IndexResponse response = Es_Utils.client.prepareIndex(Es_Utils.INDEX_DEMO_01, Es_Utils.INDEX_DEMO_01_MAPPING)
                .setSource(
                        User.getXContentBuilder(user)
                )
                .setTTL(8000)//这样就等于单独设定了该条记录的失效时间,单位是毫秒,必须在mapping中打开_ttl的设置开关
                .execute()
                .actionGet();
    }

    /**
     * 批量添加记录到索引
     *
     * @param userList 批量添加数据
     * @throws java.io.IOException
     */
    protected static void buildBulkIndex(List<User> userList) throws IOException {
        BulkRequestBuilder bulkRequest = Es_Utils.client.prepareBulk();
        // either use Es_Setting.client#prepare, or use Requests# to directly build index/delete requests

        for (User user : userList) {
            //通过add批量添加
            bulkRequest.add(Es_Utils.client.prepareIndex(Es_Utils.INDEX_DEMO_01, Es_Utils.INDEX_DEMO_01_MAPPING)
                            .setSource(
                                    User.getXContentBuilder(user)
                            )
            );
        }

        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        //如果失败
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
            System.out.println("buildFailureMessage:" + bulkResponse.buildFailureMessage());
        }
    }


}
