package com.app.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.app.client.model.CallbackResult;
import com.app.client.model.Coordinates;

import java.util.List;

public interface TicTacToeAppServiceAsync {
    void process(List<Coordinates> crosses, List<Coordinates> zeros, List<Coordinates> spaces, AsyncCallback<CallbackResult> async);
}
