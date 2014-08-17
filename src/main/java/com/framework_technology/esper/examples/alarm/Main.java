package com.framework_technology.esper.examples.alarm;


import java.util.Random;

/**
 * @author wei.Li by 14-8-14.
 */
public class Main {

    public static final String ALARMNAME = Alarm.class.getName();
    public static final String STREAM = Stream.class.getName();

    public static final String ALARMCATEGORY_CONTEXT =
            "create context alarmcategory group by num > " + Baseline.num + " as baselinealarm,group by num > " + Condition.num + " as conditionalarm from " + STREAM;
    public static final String ALARMCATEGORY_EPL =
            "context alarmcategory  select  context.label , stream_id , num from " + STREAM;//context.id,context.name,context.label,

    public static void main(String[] args) {

        //启动线程动态更新基线与条件的设定值
        new Thread(new Baseline()).start();
        new Thread(new Condition()).start();

        String epl_MergeBaseline = "select * from " + ALARMNAME;
        EsperService.EsperAlarmProvider mergeCepProvider = EsperService
                .getInstance("mergeCepProvider", new MergeListener());
        mergeCepProvider.registerEPL2Listener(epl_MergeBaseline);


        EsperService.EsperAlarmProvider preliminaryCepProvider = EsperService
                .getInstance("preliminaryCepProvider", new PreliminaryListener(mergeCepProvider));
        preliminaryCepProvider.registerEPL(ALARMCATEGORY_CONTEXT);
        preliminaryCepProvider.registerEPL2Listener(ALARMCATEGORY_EPL);
        sendEventByEPRuntime(preliminaryCepProvider);
    }

    /**
     * 发送事件
     *
     * @param preliminaryCepProvider EPRuntime引擎接收
     */
    private static void sendEventByEPRuntime(EsperService.EsperAlarmProvider preliminaryCepProvider) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    preliminaryCepProvider.sendEvent(Stream.getRandom());
                    try {
                        Thread.sleep(new Random().nextInt(3000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        });

        thread.start();
    }
}
