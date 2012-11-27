package com.app.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.app.client.model.CallbackResult;
import com.app.client.model.Coordinates;

import java.util.List;

@RemoteServiceRelativePath("TicTacToeAppService")
public interface TicTacToeAppService extends RemoteService {
    // Sample interface method of remote interface
    CallbackResult process(List<Coordinates> crosses, List<Coordinates> zeros, List<Coordinates> spaces);

    public static class App {
        private static TicTacToeAppServiceAsync ourInstance = GWT.create(TicTacToeAppService.class);

        public static synchronized TicTacToeAppServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
