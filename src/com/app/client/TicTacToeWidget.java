package com.app.client;

import com.app.client.model.CallbackResult;
import com.app.client.model.Coordinates;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.LinkedList;
import java.util.List;

public class TicTacToeWidget extends Composite {

    public static final String EMPTY_CHARACTER = "Click here";
    public static final String CROSS_CHARACTER = "X";
    public static final String ZERO_CHARACTER = "O";

    private static TicTacToeWidgetUiBinder uiBinder = GWT.create(TicTacToeWidgetUiBinder.class);

    private final TicTacToeMessages messages = GWT.create(TicTacToeMessages.class);

    interface TicTacToeWidgetUiBinder extends UiBinder<Widget, TicTacToeWidget> { }

    @UiField
    Label label;

    @UiField (provided=true)
    FlexTable grid;

    @UiField
    Button button;

    public TicTacToeWidget() {
        TicTacToeResources.INSTANCE.css().ensureInjected();
        grid = new FlexTable();
        populateGrid(grid);
        grid.insertRow(3);
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

        initWidget(uiBinder.createAndBindUi(this));
        button.setText(messages.buttonText());
        button.addStyleName(TicTacToeResources.INSTANCE.css().button());

        label.setText(messages.labelText());
        label.addStyleName(TicTacToeResources.INSTANCE.css().label());
    }

    @UiHandler("button")
    void handleClick(ClickEvent e) {
        populateGrid(grid);
        label.setText("Click on the cell!");
    }

    private List<Coordinates> getAllCoordinates(FlexTable grid, String value) {
        List<Coordinates> combination = new LinkedList<Coordinates>();
        for (int row = 0; row < grid.getRowCount(); row++) {
            for (int col = 0; col < grid.getCellCount(row); col++) {
                if (grid.getText(row, col).equals(value)) {
                    combination.add(new Coordinates(row, col));
                }
            }
        }
        return combination;
    }

    private void populateGrid(FlexTable grid) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                grid.getCellFormatter().setStyleName(row, col, TicTacToeResources.INSTANCE.css().cell());
                grid.setText(row, col, EMPTY_CHARACTER);
            }
        }
    }

    private static class ClickAsyncCallback implements AsyncCallback<CallbackResult> {
        private FlexTable grid;
        private Label label;
        private String value;

        public ClickAsyncCallback(FlexTable grid, Label label, String value) {
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
                    for (int col = 0; col < grid.getCellCount(row); col++) {
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