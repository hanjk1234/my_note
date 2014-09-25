package com.designmodel.proxy;


/**
 * @author lw by 14-5-1.
 */
public class Test_JDK {

    public static void main(String[] args) {
        BookFacadeProxy_JDK proxy_jdk = new BookFacadeProxy_JDK();
        BookFacade_JDK bookFacade
                = (BookFacade_JDK) proxy_jdk.bind(new BookFacadeJDKImpl_JDK());
        bookFacade.seeBook();
    }

}
