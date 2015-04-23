/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shipandmonster;

/**
 *
 * @author Dara
 */
public class Position {
    protected double latitude;
    protected double longitude;
    protected int column;
    protected int row;
    public int x;//pixel location
    public int y;
    
    public Position(){
        
    }
    
    public Position(int col, int row)
    {
        this.column = col;
        this.row = row;
        x = MenuLibrary.ICON_SIZE * col + MenuLibrary.MAP_ORIGIN_X;
        y = MenuLibrary.ICON_SIZE * row + MenuLibrary.MAP_ORIGIN_Y;
        longitude = MapConverter.col2lon(col);
        latitude = MapConverter.row2lat(row);
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the column
     */
    public int getColumn() {
        return column;
    }

    /**
     * @param column the column to set
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }
    
}
