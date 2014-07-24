package com.designmodel.proxy;


/**
 * Created by lw on 14-5-1.
 */
public class Test_JDK {

    public static void main(String[] args) {
        BookFacadeProxy_JDK proxy_jdk = new BookFacadeProxy_JDK();
        BookFacade bookFacade = (BookFacade) proxy_jdk.bind(new BookImpl());
        bookFacade.seeBook();
    }

}
