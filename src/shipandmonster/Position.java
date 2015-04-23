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
    public double latitude;
    public double longitude;
    public int column;
    public int row;
    public int x;//pixel location
    public int y;
    
    public Position(int col, int row)
    {
        this.column = col;
        this.row = row;
        x = MenuLibrary.ICON_SIZE * col + MenuLibrary.MAP_ORIGIN_X;
        y = MenuLibrary.ICON_SIZE * row + MenuLibrary.MAP_ORIGIN_Y;
        longitude = MapConverter.col2lon(col);
        latitude = MapConverter.row2lat(row);
    }
}
