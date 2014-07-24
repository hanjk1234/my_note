package com.designmodel.proxy;

/**
 * Created by lw on 14-5-1.
 */
public class BookImpl implements BookFacade {
    @Override
    public void seeBook() {
        System.out.println("see book ing ...");
    }
}
