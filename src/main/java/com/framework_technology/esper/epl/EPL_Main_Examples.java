package com.framework_technology.esper.epl;


import com.espertech.esper.client.*;

/**
 * main 函数测试
 *
 * @author wei.Li
 *         事件监听处理
 * @see com.framework_technology.esper.epl.AppleListener#update(com.espertech.esper.client.EventBean[], com.espertech.esper.client.EventBean[])
 */
public class EPL_Main_Examples implements Runnable {

    //线程执行时间间隔-ms
    private static final int EXECUTE_INTERVAL_MILLISECOND = 1000;
    //执行次数
    private static final int EXECUTE_NUM = 10;

    protected static final EPServiceProvider defaultProvider = EPServiceProviderManager.getDefaultProvider();
    protected static final EPAdministrator epAdministrator = defaultProvider.getEPAdministrator();
    protected static final EPRuntime epRuntime = defaultProvider.getEPRuntime();


    public static void main(String[] args) throws InterruptedException {

        //获取 epl
        String epl = EPL_1.groupBy();

        EPStatement epStatement = epAdministrator.createEPL(epl);
        //注册监听
        epStatement.addListener(new AppleListener());

        new EPL_Main_Examples().run();

    }

    /**e
     * use Thread add event
     */
    @Override
    public void run() {

        int temp = 1;
        while (temp <= EXECUTE_NUM) {
            temp++;
            epRuntime.sendEvent(Apple.getRandomApple());
            try {
                Thread.sleep(EXECUTE_INTERVAL_MILLISECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }
}




