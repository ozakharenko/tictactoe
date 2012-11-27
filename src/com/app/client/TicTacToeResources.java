package com.app.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface TicTacToeResources extends ClientBundle {

    interface TicTacToeCss extends CssResource {
        String button();
        String cell();
        String label();
    }

    public static final TicTacToeResources INSTANCE = GWT.create(TicTacToeResources.class);

    @Source("resources/resources.css")
    TicTacToeCss css();
}
