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
public abstract class SeaMonster {
    
    protected Position position;
    protected String label;
    protected int currentCount;
    protected char symbol;
    
    abstract public String battleCry();    
    abstract public String displayMonster();

    /**
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }
    
    public char getSymbol()
    {
        return this.symbol;
    }
    
    public void setSymbol(char s)
    {
        this.symbol = s;
    }
}
