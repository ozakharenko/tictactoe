package com.app.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

import com.app.client.model.CallbackResult;
import com.app.client.model.Coordinates;

import java.util.LinkedList;
import java.util.List;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class TicTacToeApp implements EntryPoint {

    public static final String EMPTY_CHARACTER = "Click here";
    public static final String CROSS_CHARACTER = "X";
    public static final String ZERO_CHARACTER = "O";

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Button button = new Button("Start new game");
        final Label label = new Label();
        final Grid grid = new Grid(3, 3);

        grid.setBorderWidth(1);
        grid.setCellSpacing(10);

        grid.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                HTMLTable.Cell cell = grid.getCellForEvent(event);

                if (grid.getText(cell.getRowIndex(), cell.getCellIndex()).equals(EMPTY_CHARACTER)) {
                    grid.setText(cell.getRowIndex(), cell.getCellIndex(), CROSS_CHARACTER);
                    List<Coordinates> crosses = getAllCoordinates(grid, CROSS_CHARACTER);
                    List<Coordinates> zeros = getAllCoordinates(grid, ZERO_CHARACTER);
                    List<Coordinates> spaces = getAllCoordinates(grid, EMPTY_CHARACTER);

                    TicTacToeAppService.App.getInstance().process(crosses, zeros, spaces, new ClickAsyncCallback(grid, label, ZERO_CHARACTER));
                }
            }
        });

        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                for (int row = 0; row < grid.getRowCount(); row++) {
                    for (int col = 0; col < grid.getColumnCount(); col++) {
                        grid.getCellFormatter().setWidth(row, col, "100px");
                        grid.getCellFormatter().setHeight(row, col, "100px");
                        grid.getCellFormatter().setHorizontalAlignment(row, col, HasHorizontalAlignment.ALIGN_CENTER);
                        grid.getCellFormatter().setVerticalAlignment(row, col, HasVerticalAlignment.ALIGN_MIDDLE);
                        grid.setText(row, col, EMPTY_CHARACTER);
                    }
                }
                label.setText("Click on the cell!");
            }
        });

        RootPanel.get("button").add(button);
        RootPanel.get("grid").add(grid);
        RootPanel.get("label").add(label);
        button.click();
    }

    private List<Coordinates> getAllCoordinates(Grid grid, String value) {
        List<Coordinates> combination = new LinkedList<Coordinates>();
        for (int row = 0; row < grid.getRowCount(); row++) {
            for (int col = 0; col < grid.getColumnCount(); col++) {
                if (grid.getText(row, col).equals(value)) {
                    combination.add(new Coordinates(row, col));
                }
            }
        }
        return combination;
    }

    private static class ClickAsyncCallback implements AsyncCallback<CallbackResult> {
        private Grid grid;
        private Label label;
        private String value;

        public ClickAsyncCallback(Grid grid, Label label, String value) {
            this.grid = grid;
            this.label = label;
            this.value = value;
        }

        public void onSuccess(CallbackResult result) {
            if (result.getCoordinates() != null) {
                grid.setText(result.getCoordinates().getRow(), result.getCoordinates().getColumn(), value);
            }
            if (result.getMessage() != null) {
                label.setText(result.getMessage());

                //remove label "click here" on cells
                for (int row = 0; row < grid.getRowCount(); row++) {
                    for (int col = 0; col < grid.getColumnCount(); col++) {
                        if (grid.getText(row, col).equals(EMPTY_CHARACTER)) {
                            grid.setText(row, col, " ");
                        }
                    }
                }
            }
        }

        public void onFailure(Throwable throwable) {
            label.setText("Internal server error");
        }
    }
}