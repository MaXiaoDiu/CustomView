package com.cyy.mxdcalendar.Entity;

/**
 * Created by Cyy513 on 2018/4/19.
 */

public class DateModel {

    public int getRow() {
        return Row;
    }

    public void setRow(int row) {
        Row = row;
    }

    public int getCol() {
        return Col;
    }

    public void setCol(int col) {
        Col = col;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    private int Row;//行
    private int Col;//列
    private int Count;//数字


}
