package com.thread.jdk_thread.example;

import java.util.UUID;

/**
 * 对象
 *
 * @author wei.Li by 14-8-21.
 */
public class Obj {
    private String objId;

    public Obj() {
        this.objId = UUID.randomUUID().toString();
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    @Override
    public String toString() {
        return "Obj{" +
                "objId='" + objId + '\'' +
                '}';
    }
}
