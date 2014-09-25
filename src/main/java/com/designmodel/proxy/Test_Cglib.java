package com.designmodel.proxy;


/**
 * @author lw by 14-5-1.
 */
public class Test_Cglib {

    public static void main(String[] args) {
        BookFacadeProxy_Cglib proxy_cglib = new BookFacadeProxy_Cglib();
        BookFacadeJDKImpl_JDK bookImpl
                = (BookFacadeJDKImpl_JDK) proxy_cglib.getInstance(new BookFacadeJDKImpl_JDK());
        bookImpl.seeBook();
    }
}
