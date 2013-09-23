/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.nullpointerassignment;

/**
 *
 * @author honzaq
 */
public class Item {
    protected boolean dangerous;
    protected int address;
    
    public Item() {
        this.dangerous = true;
    }

    public Item(boolean dangerous) {
        this.dangerous = dangerous;
    }
    
    public boolean isDangerous() {
        return dangerous;
    }

    public void setDangerous(boolean dangerous) {
        this.dangerous = dangerous;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
    
    public String toString() {
        return "Item[" + this.isDangerous() + "]";
    }
}
