/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konyvtar.Borrow;

/**
 *
 * @author bzolt
 */
public class Borrow {
    private int bookID;
    private String inDate;
    private String outDate;
    private String ticketID;
    private String memberName;
    private int storeID;

    public Borrow(String ticketID, String memberName,int bookID, int storeID, String inDate, String outDate) {
        this.bookID = bookID;
        this.inDate = inDate;
        this.outDate = outDate;
        this.ticketID = ticketID;
        this.memberName = memberName;
        this.storeID = storeID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }
    
    
    
    

    public int getBookID() {
        return bookID;
    }

    public String getInDate() {
        return inDate;
    }

    public String getOutDate() {
        return outDate;
    }

    public String getTicketID() {
        return ticketID;
    }

    public String getMemberName() {
        return memberName;
    }

    public int getStoreID() {
        return storeID;
    }
    
    
    
}
