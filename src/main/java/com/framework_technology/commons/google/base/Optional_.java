package com.framework_technology.commons.google.base;

import com.google.common.base.Optional;
import org.slf4j.LoggerFactory;

/**
 * @author wei.Li by 14-8-26.
 */
public class Optional_ {

    private static final org.slf4j.Logger LOGGER
            = LoggerFactory.getLogger(Optional_.class);

    /**
     *
     */
    private static void createObject() {
        //创建一个空对象
        Optional<String> absent = Optional.absent();
        LOGGER.info("Optional.absent()              is <{}>", absent.isPresent());

        //创建一个非空对象
        Optional<String> stringOptional = Optional.of("aa");
        LOGGER.info("Optional.of(\"aa\")            is <{}>", stringOptional.get());

        //创建一个不确定是否非空的对象
        Optional<String> fromNullable = Optional.fromNullable("bb");
        LOGGER.info("Optional.fromNullable(\"bb\")  is <{}>", fromNullable.get());



    }


}
