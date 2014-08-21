package com.framework_technology.esper.epl;

/**
 * API - 5.16. Declaring an Event Type: Create Schema
 *
 * @author wei.Li by 14-8-21.
 */
public class EPL_9_Schema {

    /**
     * 声明一个提供事件类型的名称和数据类型
     * <p>
     * 语法：
     * <p>
     * create [map | objectarray] schema schema_name [as]
     * (property_name property_type [,property_name property_type [,...])
     * [inherits inherited_event_type[, inherited_event_type] [,...]]
     * [starttimestamp timestamp_property_name]
     * [endtimestamp timestamp_property_name]
     * [copyfrom copy_type_name [, copy_type_name] [,...]]
     * <p>
     * 1.任何Java类名，完全合格或引入简单的类名配置。
     * 2.添加左右方括号[]到任何类型来表示一个数组类型的事件属性。
     * 3.使用事件类型名称作为属性类型。
     *
     * @return epl[]
     */
    protected static String declareSchema() {
        String epl1 = "";

        return epl1;
    }

}
