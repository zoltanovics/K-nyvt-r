package konyvtar.Member;

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

public class MemberCacheImpl implements MemberCache{
    private Connection con;
    private Statement st;
    
    private final String url = "jdbc:derby://localhost:1527/NBUSER;create=true";
    private final String user = "nbuser";
    private final String password = "nbuser";
    private int latest_id = 0;
    public List<Member> data;
    
    public MemberCacheImpl(){
        refreshData();
    }
    
    @Override
    public void refreshData(){
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            con = DriverManager.getConnection(url,user,password);
            st = con.createStatement();
            String query = "SELECT * FROM NBUSER.MEMBERS ORDER BY TICKETID";
            ResultSet rs = st.executeQuery(query);
            ArrayList<Member> result = new ArrayList<>();
            while(rs.next()){
                result.add(new Member(rs.getString(2), rs.getBoolean(4), rs.getString(3), rs.getString(1),rs.getInt(5)));
            }
            data = result;
            if(!result.isEmpty()){
                latest_id = Integer.valueOf(result.get(result.size()-1).getTicketID());
            }
            con.close();
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(MemberCacheImpl.class.getName()).log(Level.SEVERE, null, ex);
            data = new ArrayList<>();
        }
    }
    
    @Override
    public List<Member> getDataList() {
        return this.data;
    }

    @Override
    public void save(Member obj) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            con = DriverManager.getConnection(url,user,password);
            st = con.createStatement();
            latest_id++;
            String query = "INSERT INTO NBUSER.MEMBERS VALUES(" + latest_id + ",'" + obj.getAddress() + "','" + obj.getName() + "'," +
                    false + 0 + ")";
            System.out.println(query);
            st.executeUpdate(query);
            data.add(obj);
            con.close();
            MainWindow.infoBox("Member saved successfully", "Success");
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(MemberCacheImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getLatestID() {
        return latest_id;
    }
    
    @Override
    public void modify(Member obj) {
        boolean elso=false;
        boolean masodik=false;
        for (Member member : data) {
            if (member.getTicketID().equals(obj.getTicketID())) {
                if (!obj.getAddress().equals("Address")) {
                    member.setAddress(obj.getAddress());
                }
                if (!obj.getName().equals("Name")) {
                    member.setName(obj.getName());
                }
            }
        }
        try { 
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            con = DriverManager.getConnection(url,user,password);
            st = con.createStatement();
            String query = "UPDATE NBUSER.MEMBERS SET ADDRESS ='"+obj.getAddress()+"' WHERE TICKETID="+Integer.valueOf(obj.getTicketID());
            String query2 = "UPDATE NBUSER.MEMBERS SET NAME ='"+obj.getName()+"' WHERE TICKETID="+Integer.valueOf(obj.getTicketID());
            if (!obj.getAddress().equals("Address")) {
                st.executeUpdate(query);
                elso=true;
            }
            if (!obj.getName().equals("Name")) {
                st.executeUpdate(query2);
                masodik = true;
            }
            con.close();
            if (elso || masodik) {
                MainWindow.infoBox("Member modified successfully", "Success");
            }
            else {
                MainWindow.infoBox("You didnt want to modify","Information");
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(MemberCacheImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void delete(Member obj) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            con = DriverManager.getConnection(url,user,password);
            st = con.createStatement();
            if (obj.isBorrowed()==true) {
                MainWindow.infoBox("The member has not returned a book yet, can not be deleted", "Failure");
            }
            else {
                String query = "DELETE FROM NBUSER.MEMBERS WHERE TICKETID = " +Integer.valueOf(obj.getTicketID());
                st.executeUpdate(query);
                data.remove(obj);
                MainWindow.infoBox("Member deleted successfully", "Success");
            }
            con.close();
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(MemberCacheImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Member getMemberById(String id) {
        Member res = null;
        for(int i=0; i<data.size(); i++){
            if(Integer.valueOf(data.get(i).getTicketID()) == Integer.valueOf(id)){
                res = data.get(i);
            }
        }
        return res;
    }
    
}
