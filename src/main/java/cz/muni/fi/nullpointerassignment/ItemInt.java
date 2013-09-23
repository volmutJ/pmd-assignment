/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.nullpointerassignment;

/**
 *
 * @author honzaq
 */
public class ItemInt extends Item {
    private int value;

    ItemInt(boolean dangerous) {
        super(dangerous);
    }
      
    public int getValue() {
        return this.value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
        
        
}
