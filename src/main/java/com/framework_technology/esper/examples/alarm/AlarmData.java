package com.framework_technology.esper.examples.alarm;

import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;

/**
 * @author wei.Li by 14-8-14.
 */
public class AlarmData {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AlarmData.class);

    private String id; //编号
    private String keyunique;//流+类型+开始时间  创建唯一标识
    private int stream_id;//所属流
    private long start_time;//开始时间
    private long end_time;//结束时间
    private int type;//告警类型 0条件告警 1基线告警
    private double num;//实际数值
    private double Offset;//偏移基线值
    private int duration;//持续时间
    private boolean merged = false;//是否已告警合并
    private Integer merged_at = null;//告警合并后的最后创建时间
    private int merged_count = 0;//告警合并数量


    //获取随机的数据对象
    protected static AlarmData getRandom() {
        return new AlarmData();
    }

    public AlarmData() {
        Random random = new Random();
        this.id = UUID.randomUUID().toString();
        this.stream_id = random.nextInt(3);
        this.start_time = System.currentTimeMillis();
        this.type = random.nextInt(2);
        this.num = random.nextInt(10000);
        this.keyunique = String.format("%s#%s#%s", stream_id, type, start_time);
        //LOGGER.debug("Random AlarmData`s type : <{}> , num : <{}>", this.type, this.num);
    }

    @Override
    public String toString() {
        return "AlarmData{" +
                "id='" + id + '\'' +
                ", keyunique='" + keyunique + '\'' +
                ", stream_id=" + stream_id +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", type=" + type +
                ", num=" + num +
                ", Offset=" + Offset +
                ", duration=" + duration +
                ", merged=" + merged +
                ", merged_at=" + merged_at +
                ", merged_count=" + merged_count +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyunique() {
        return keyunique;
    }

    public void setKeyunique(String keyunique) {
        this.keyunique = keyunique;
    }

    public int getStream_id() {
        return stream_id;
    }

    public void setStream_id(int stream_id) {
        this.stream_id = stream_id;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public double getOffset() {
        return Offset;
    }

    public void setOffset(double offset) {
        Offset = offset;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isMerged() {
        return merged;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    public Integer getMerged_at() {
        return merged_at;
    }

    public void setMerged_at(Integer merged_at) {
        this.merged_at = merged_at;
    }

    public int getMerged_count() {
        return merged_count;
    }

    public void setMerged_count(int merged_count) {
        this.merged_count = merged_count;
    }
}

/**
 * 基线值-动态修改
 */
class Baseline implements Runnable {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Baseline.class);

    public static double num;//当前基线值

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            num = random.nextInt(1000);
            //LOGGER.debug("Baseline update num is : <{}>", num);
            try {
                Thread.sleep(random.nextInt(20000));
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}

/**
 * 条件值动态修改
 */
class Condition implements Runnable {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Condition.class);

    public static int num;//当前条件设置值

    @Override
    public void run() {
        while (true) {
            Random random = new Random();
            num = random.nextInt(1000);
            //LOGGER.debug("Condition update num is : <{}>", num);
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}
