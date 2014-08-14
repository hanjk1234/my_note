package com.framework_technology.esper.examples.alarm;

import com.espertech.esper.client.*;
import org.slf4j.LoggerFactory;


/**
 * @author wei.Li by 14-8-14.
 */
public class EsperService {

    public static interface ICEPProvider {

        public void registerEPL(String epl, String eplID);

        public void sendEvent(Object theEvent);
    }

    public static EsperAlarmProvider getInstance(String providerURI, UpdateListener updateListener) {
        return new EsperAlarmProvider(providerURI, updateListener);
    }

    public static class EsperAlarmProvider implements ICEPProvider {

        private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(EsperAlarmProvider.class);

        private EPAdministrator epAdministrator;

        private EPRuntime epRuntime;

        private UpdateListener updateListener;


        public EsperAlarmProvider(String providerURI, UpdateListener updateListener) {
            EPServiceProvider epService = EPServiceProviderManager.getProvider(providerURI);
            this.epAdministrator = epService.getEPAdministrator();
            this.epRuntime = epService.getEPRuntime();
            LOGGER.debug("EsperAlarmProvider  run ... epAdministrator is <{}> , epRuntime is <{}>", epAdministrator, epRuntime);
            this.updateListener = updateListener;
        }


        public void registerEPL(String epl, String eplID) {
            EPStatement stmt = this.epAdministrator.createEPL(epl, eplID);
            stmt.addListener(this.updateListener);
        }

        public void sendEvent(Object theEvent) {
            this.epRuntime.sendEvent(theEvent);
        }
    }
}
