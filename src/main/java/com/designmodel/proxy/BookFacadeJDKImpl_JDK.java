package com.designmodel.proxy;

/**
 * @author lw by 14-5-1.
 */
public class BookFacadeJDKImpl_JDK implements BookFacade_JDK {
    @Override
    public void seeBook() {
        System.out.println("see book ing ...");
    }
}
