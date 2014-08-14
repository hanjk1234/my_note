package com.framework_technology.esper.examples.alarm;


import java.util.Random;

/**
 * @author wei.Li by 14-8-14.
 */
public class Main {

    public static final String ALARMDATANAME = AlarmData.class.getName();

    public static void main(String[] args) {

        new Thread(new Baseline()).start();
        new Thread(new Condition()).start();

        EsperService.EsperAlarmProvider mergeCepProvider = EsperService.getInstance("mergeCepProvider", new MergeListener());
        String epl_MergeBaseline = "select * from " + ALARMDATANAME;
        mergeCepProvider.registerEPL(epl_MergeBaseline, "epl_MergeBaseline");

        EsperService.EsperAlarmProvider preliminaryCepProvider = EsperService.getInstance("preliminaryCepProvider", new PreliminaryListener(mergeCepProvider));
        String epl_Baseline = "select a.type,a.num from " + ALARMDATANAME + " a where a.type = 0 and a.num >" + Baseline.num;
        String epl_Condition = "select a.type,a.num from " + ALARMDATANAME + " a where a.type = 1 and a.num >" + Condition.num;

        preliminaryCepProvider.registerEPL(epl_Baseline, "epl_Baseline");
        preliminaryCepProvider.registerEPL(epl_Condition, "epl_Condition");
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
                    preliminaryCepProvider.sendEvent(AlarmData.getRandom());
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
