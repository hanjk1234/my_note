package com.designmodel.proxy;


/**
 * @author lw by 14-5-1.
 */
public class Test_Cglib {

    public static void main(String[] args) {
        BookFacadeProxy_Cglib proxy_cglib = new BookFacadeProxy_Cglib();
        BookImpl bookImpl = (BookImpl) proxy_cglib.getInstance(new BookImpl());
        bookImpl.seeBook();

    }
}
