/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.nullpointerassignment;

import java.util.List;

/**
 *
 * @author honzaq
 */
public class ItemArray extends Item {

    private Item[] items;

    public ItemArray() {
        super();
        this.items = new Item[100];
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public Item getItem(int index) {
        Item result;
        if (this.indexExists(index)) {
            result = items[index];
        } else {
            result = new Item(true);
            this.items[index] = result;
        }

        return result;
    }

    public void putItem(int index, Item item) {
        if (index < items.length) {
            this.items[index] = item;
        } else {
            throw new IllegalStateException("This example expects max index value 100");
        }
    }

    private boolean indexExists(int index) {
        if (this.items[index] == null) {
            return false;
        }

        return true;
    }
}
