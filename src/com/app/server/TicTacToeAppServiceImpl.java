package com.app.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.app.client.TicTacToeAppService;
import com.app.client.model.CallbackResult;
import com.app.client.model.Coordinates;

import java.util.LinkedList;
import java.util.List;

public class TicTacToeAppServiceImpl extends RemoteServiceServlet implements TicTacToeAppService {

    public static final List<List<Coordinates>> WIN_RESULTS;

    static {
        WIN_RESULTS = new LinkedList<List<Coordinates>>();
        List<Coordinates> combination = new LinkedList<Coordinates>();
        combination.add(new Coordinates(0 ,0));
        combination.add(new Coordinates(0 ,1));
        combination.add(new Coordinates(0 ,2));
        WIN_RESULTS.add(combination);
        combination = new LinkedList<Coordinates>();
        combination.add(new Coordinates(1 ,0));
        combination.add(new Coordinates(1 ,1));
        combination.add(new Coordinates(1 ,2));
        WIN_RESULTS.add(combination);
        combination = new LinkedList<Coordinates>();
        combination.add(new Coordinates(2 ,0));
        combination.add(new Coordinates(2, 1));
        combination.add(new Coordinates(2, 2));
        WIN_RESULTS.add(combination);
        combination = new LinkedList<Coordinates>();
        combination.add(new Coordinates(0, 0));
        combination.add(new Coordinates(1, 0));
        combination.add(new Coordinates(2, 0));
        WIN_RESULTS.add(combination);
        combination = new LinkedList<Coordinates>();
        combination.add(new Coordinates(0, 1));
        combination.add(new Coordinates(1, 1));
        combination.add(new Coordinates(2, 1));
        WIN_RESULTS.add(combination);
        combination = new LinkedList<Coordinates>();
        combination.add(new Coordinates(0, 2));
        combination.add(new Coordinates(1, 2));
        combination.add(new Coordinates(2, 2));
        WIN_RESULTS.add(combination);
        combination = new LinkedList<Coordinates>();
        combination.add(new Coordinates(0, 0));
        combination.add(new Coordinates(1, 1));
        combination.add(new Coordinates(2, 2));
        WIN_RESULTS.add(combination);
        combination = new LinkedList<Coordinates>();
        combination.add(new Coordinates(0, 2));
        combination.add(new Coordinates(1, 1));
        combination.add(new Coordinates(2, 0));
        WIN_RESULTS.add(combination);
    }

    public CallbackResult process(List<Coordinates> crosses, List<Coordinates> zeros, List<Coordinates> spaces) {

        //check win cross
        for (List<Coordinates> list : WIN_RESULTS) {
            int count = 0;
            for (Coordinates coordinates : list) {
                if (crosses.contains(coordinates)) {
                    count++;
                }
            }
            if (count == 3) {
                return new CallbackResult("You are win!", null);
            }
        }

        //return draw
        if (spaces.size() == 0) {
            return new CallbackResult("Draw!", null);
        }

        //go and win
        for (List<Coordinates> list : WIN_RESULTS) {
            int count = 0;
            Coordinates emptyCoordinates = null;
            for (Coordinates coordinates : list) {
                if (zeros.contains(coordinates)) {
                    count++;
                } else {
                    emptyCoordinates = coordinates;
                }
            }
            if (count == 2 && spaces.contains(emptyCoordinates)) {
                return new CallbackResult("You are failed!", emptyCoordinates);
            }
        }

        //go and protect win cross
        for (List<Coordinates> list : WIN_RESULTS) {
            int count = 0;
            Coordinates emptyCoordinates = null;
            for (Coordinates coordinates : list) {
                if (crosses.contains(coordinates)) {
                    count++;
                } else {
                    emptyCoordinates = coordinates;
                }
            }
            if (count == 2 && spaces.contains(emptyCoordinates)) {
                return new CallbackResult(null, emptyCoordinates);
            }
        }

        //go and set 2 element from win combination
        for (List<Coordinates> list : WIN_RESULTS) {
            for (Coordinates zero : zeros) {
                if (list.contains(zero)) {
                    int index = list.indexOf(zero);
                    int count = 0;
                    Coordinates zeroNew = null;

                    for (Coordinates coordinates : list) {
                        if(spaces.contains(coordinates)) {
                            count++;
                            zeroNew = coordinates;
                        }
                    }
                    if (count == 2) {
                        return new CallbackResult(null, zeroNew);
                    }
                }
            }
        }

        //set last
        if (spaces.size() == 1) {
            return new CallbackResult("Draw", spaces.get(0));
        }

        //set random value
        return new CallbackResult(null, spaces.get((int)(Math.random()*spaces.size() - 1)));
    }
}