package konyvtar.Borrow;

import konyvtar.Member.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import konyvtar.View.MainWindow;

public class BorrowCacheImpl implements BorrowCache{
    private Connection con;
    private Statement st;
    
    private final String url = "jdbc:derby://localhost:1527/NBUSER;create=true";
    private final String user = "nbuser";
    private final String password = "nbuser";
    private int latest_id;
    public List<Borrow> data;
    
    public BorrowCacheImpl(){
        refreshData();
    }
    
    @Override
    public void refreshData(){
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            con = DriverManager.getConnection(url,user,password);
            st = con.createStatement();
            String query = "SELECT * FROM NBUSER.BORROWS ORDER BY STOREID";
            ResultSet rs = st.executeQuery(query);
            ArrayList<Borrow> result = new ArrayList<>();
            while(rs.next()){
                result.add(new Borrow(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4),rs.getString(5),rs.getString(6)));
            }
            data = result;
            if(!result.isEmpty()){
                latest_id = result.get(result.size()-1).getStoreID();
            }
            con.close();
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(BorrowCacheImpl.class.getName()).log(Level.SEVERE, null, ex);
            data = new ArrayList<>();
        }
    }
    
    @Override
    public List<Borrow> getDataList() {
        return this.data;
    }
    
    public void memberHaveCopy(Borrow obj) {
        MemberCacheImpl memberCache = new MemberCacheImpl();
        Member member = memberCache.getMemberById(obj.getTicketID());
        member.setBorrowed(true);
        member.setBorrowpcs(member.getBorrowpcs()+1);
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            con = DriverManager.getConnection(url,user,password);
            st = con.createStatement();
            String query2 = "UPDATE NBUSER.MEMBERS SET BORROWPCS = "+member.getBorrowpcs() +" WHERE TICKETID= "+Integer.valueOf(obj.getTicketID());
            String query = "UPDATE NBUSER.MEMBERS SET BORROWED = true WHERE TICKETID= "+ Integer.valueOf(obj.getTicketID());
            st.executeUpdate(query);
            st.execute(query2);
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(BorrowCacheImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void save(Borrow obj) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            con = DriverManager.getConnection(url,user,password);
            st = con.createStatement();
            memberHaveCopy(obj);
            String query = "INSERT INTO NBUSER.BORROWS VALUES(" + Integer.valueOf(obj.getTicketID()) + ",'" + obj.getMemberName() + "'," +
                    obj.getBookID() + "," + obj.getStoreID() + ",'" + obj.getInDate() + "','" + obj.getOutDate() +"')";
            st.executeUpdate(query);
            if (obj.getInDate().equals("Gave out") || obj.getInDate().equals("Got back") || obj.getInDate().equals("")) {
                String query2="UPDATE NBUSER.COPIES SET BORROWED = true WHERE STOREID = " + obj.getStoreID();
                st.executeUpdate(query2);
            }
            data.add(obj);
            con.close();
            MainWindow.infoBox("Borrow saved successfully", "Success");
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(BorrowCacheImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MemberRetrieveCopy(Borrow obj) {
        MemberCacheImpl memberCache = new MemberCacheImpl();
        Member member = memberCache.getMemberById(obj.getTicketID());
        member.setBorrowpcs(member.getBorrowpcs()-1);
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            con = DriverManager.getConnection(url,user,password);
            st = con.createStatement();
            String query2 = "UPDATE NBUSER.MEMBERS SET BORROWPCS = "+member.getBorrowpcs()+" WHERE TICKETID="+Integer.valueOf(obj.getTicketID());
            if (member.getBorrowpcs()==0) {
                member.setBorrowed(false);
                String query = "UPDATE NBUSER.MEMBERS SET BORROWED = false WHERE TICKETID="+Integer.valueOf(obj.getTicketID());
                st.executeUpdate(query);
            }
            String query3 = "UPDATE NBUSER.BORROWS SET INDATE ='"+obj.getInDate()+"' WHERE STOREID="+obj.getStoreID();
            st.executeUpdate(query2);
            st.executeUpdate(query3);
            con.close();
        }   
        catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(BorrowCacheImpl.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    public void delete(Borrow obj) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            con = DriverManager.getConnection(url,user,password);
            st = con.createStatement();
            String query = "UPDATE NBUSER.COPIES SET BORROWED = false WHERE STOREID = " + obj.getStoreID();
            st.executeUpdate(query);
            MemberRetrieveCopy(obj);
            MainWindow.infoBox("Copy retrieved", "Success");
            con.close();
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(BorrowCacheImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Borrow getBorrowById(int id) {
        Borrow res = null;
        for(int i=0; i<data.size(); i++){
            if(data.get(i).getStoreID() == id){
                res = data.get(i);
            }
        }
        return res;
    }
    
}
