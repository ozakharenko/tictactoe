package com.app.client.model;

import java.io.Serializable;

public class Coordinates implements Serializable {

    private int row;
    private int column;

    public Coordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Coordinates() {
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Coordinates)) {
            return false;
        }
        if (((Coordinates)object).getColumn() == this.column && ((Coordinates)object).getRow() == this.row) {
            return true;
        }
        return false;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
