/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konyvtar.Book;

import java.util.ArrayList;
import java.util.List;
import konyvtar.Copy.Copy;
import konyvtar.Copy.CopyCacheImpl;

/**
 *
 * @author bzolt
 */
public class Book {
    
    private final String author;
    private final int bookID;
    private List<Copy> copies;
    private final int outYear;
    private final String name;
    private int pcs;
    private boolean lock;

    public Book(int bookID, String author, int outYear, String name, int pcs, boolean lock) {
        this.author = author;
        this.bookID = bookID;
        this.outYear = outYear;
        this.name = name;
        this.pcs = pcs;
        this.lock = lock;
        CopyCacheImpl copy= new CopyCacheImpl();
        copies = new ArrayList();
        for (Copy cop : copy.getDataList()) {
            if (cop.getBookID()==this.bookID) {
                copies.add(cop);
            }
        }
    }

    public void setCopies(ArrayList<Copy> copies) {
        this.copies = copies;
    }

    public String getAuthor() {
        return author;
    }

    public int getBookID() {
        return bookID;
    }

    public  List<Copy> getCopies() {
        return copies;
    }

    public int getOutYear() {
        return outYear;
    }

    public String getName() {
        return name;
    }

    
    
    

    public int getPcs() {
        return pcs;
    }

    public void setPcs(int pcs) {
        this.pcs = pcs;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }
    
    

   
    
}
