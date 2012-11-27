package com.app.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.app.client.model.CallbackResult;
import com.app.client.model.Coordinates;

import java.util.LinkedList;
import java.util.List;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class TicTacToeApp implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        TicTacToeWidget widget = new TicTacToeWidget();
        RootPanel.get().add(widget);
    }
}