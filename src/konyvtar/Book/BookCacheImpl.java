package konyvtar.Book;
        
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

public class BookCacheImpl implements BookCache{
    private Connection con;
    private Statement st;
    
    private final String url = "jdbc:derby://localhost:1527/NBUSER;create=true";
    private final String user = "nbuser";
    private final String password = "nbuser";
    public List<Book> data; 
    
    public BookCacheImpl(){
        refreshData();
    }
    
    @Override
    public List<Book> getDataList() {
        return this.data;
    }
    

    public Book getBookByID(int id) {
        Book res = null;
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getBookID() == id){
                res = data.get(i);
            }
        }
        return res;
    }
    
    @Override
    public void lock(Book book) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            con = DriverManager.getConnection(url,user,password);
            st = con.createStatement();
            book.setLock(true);
            String query = "UPDATE NBUSER.BOOKS SET LOCK = true WHERE ID = "+book.getBookID();
            st.executeUpdate(query);
            con.close();
            MainWindow.infoBox("Book locked successfully", "Success");
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(BookCacheImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    @Override
    public void unlock(Book book) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            con = DriverManager.getConnection(url,user,password);
            st = con.createStatement();
            book.setLock(false);
            String query = "UPDATE NBUSER.BOOKS SET LOCK = false WHERE ID = "+book.getBookID();
            st.executeUpdate(query);
            con.close();
            MainWindow.infoBox("Book unlocked successfully", "Success");
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(BookCacheImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    @Override
    public void refreshData(){
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            con = DriverManager.getConnection(url,user,password);
            st = con.createStatement();
            ArrayList<Book> result = new ArrayList<>();
            String query = "SELECT * FROM NBUSER.BOOKS ORDER BY ID";
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                result.add(new Book(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),rs.getInt(5),rs.getBoolean(6)));
            }
            con.close();
            this.data = result;
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(BookCacheImpl.class.getName()).log(Level.SEVERE, null, ex);
            data = new ArrayList<>();
        }
    }
    
    public Book getBookById(int id) {
        Book res = null;
        for(int i=0; i<data.size(); i++){
            if(data.get(i).getBookID() == id){
                res = data.get(i);
            }
        }
        return res;
    }
}