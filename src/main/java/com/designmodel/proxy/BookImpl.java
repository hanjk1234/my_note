package com.designmodel.proxy;

/**
 * @author lw by 14-5-1.
 */
public class BookImpl implements BookFacade {
    @Override
    public void seeBook() {
        System.out.println("see book ing ...");
    }
}
