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
    
    abstract public void battleCry();
    
    
}
