package com.java.plug.syslog4j;

import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.impl.message.structured.StructuredSyslogMessage;

/**
 * Created by lw on 14-7-14.
 *
 */
public class Syslog4j_Client {

    public static void main(String[] args) {
        writeLog();
    }
    private static void writeLog() {
        SyslogIF syslog = Syslog.getInstance("udp");
        syslog.getConfig().setHost("192.168.1.58");
        syslog.getConfig().setPort(1514);
        syslog.error(" --------Log Message --------");
        syslog.critical("-------Log critical ---------");
    }
}
