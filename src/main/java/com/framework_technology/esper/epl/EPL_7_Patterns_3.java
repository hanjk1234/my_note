package com.framework_technology.esper.epl;

import com.framework_technology.esper.javabean.Apple;
import com.framework_technology.esper.javabean.Banana;

/**
 * @author wei.Li by 14-8-18.
 */
public class EPL_7_Patterns_3 {

    /**
     * And
     *
     * @return epl
     */
    protected static String every() {
        String epl1 = "select a.*  from pattern[every a=" + Apple.CLASSNAME + " -> b=" + Banana.CLASSNAME + "]";
        return epl1;
    }
}
