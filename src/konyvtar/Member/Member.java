/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konyvtar.Member;

/**
 *
 * @author bzolt
 */
public class Member {
    private String address;
    private boolean borrowed;
    private String name;
    private String ticketID;
    private int borrowpcs;
    
    

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

    public Member(String address, boolean borrowed, String name, String ticketID,int borrowpcs) {
        this.address = address;
        this.borrowed = borrowed;
        this.name = name;
        this.ticketID = ticketID;
        this.borrowpcs=borrowpcs;
    }

    public int getBorrowpcs() {
        return borrowpcs;
    }

    public void setBorrowpcs(int borrowpcs) {
        this.borrowpcs = borrowpcs;
    }

    
    
    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getTicketID() {
        return ticketID;
    }

    public boolean isBorrowed() {
        return borrowed;
    }
    
    public void setTicketID(String value) {
        this.ticketID=value;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }
    
    
}
