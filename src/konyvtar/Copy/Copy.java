/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konyvtar.Copy;

/**
 *
 * @author bzolt
 */
public class Copy {
    private int bookID;
    private int storeID;
    private boolean borrowed;

    public Copy(int bookID, int storeID,boolean borrowed) {
        this.bookID = bookID;
        this.storeID = storeID;
        this.borrowed = borrowed;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    
    public int getBookID() {
        return bookID;
    }

    public int getStoreID() {
        return storeID;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.bookID;
        hash = 97 * hash + this.storeID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Copy other = (Copy) obj;
        if (this.bookID != other.bookID) {
            return false;
        }
        if (this.storeID != other.storeID) {
            return false;
        }
        if (this.borrowed != other.borrowed) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
}
