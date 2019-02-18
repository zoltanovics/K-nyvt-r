package konyvtar.Copy;
        
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CopyCacheImpl implements CopyCache{
    private Connection con;
    private Statement st;
    
    private final String url = "jdbc:derby://localhost:1527/NBUSER;create=true";
    private final String user = "nbuser";
    private final String password = "nbuser";
    public List<Copy> data; 
    
    public CopyCacheImpl(){
        refreshData();
    }
    
    @Override
    public List<Copy> getDataList() {
        return this.data;
    }
    

    public Copy getCopyByID(int id) {
        Copy res = null;
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getStoreID()== id){
                res = data.get(i);
            }
        }
        return res;
    }
    
    @Override
    public void refreshData(){
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            con = DriverManager.getConnection(url,user,password);
            st = con.createStatement();
            ArrayList<Copy> result = new ArrayList<>();
            String query = "SELECT * FROM NBUSER.COPIES ORDER BY STOREID";
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                result.add(new Copy(rs.getInt(1), rs.getInt(2),rs.getBoolean(3)));
            }
            con.close();
            this.data = result;
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(CopyCacheImpl.class.getName()).log(Level.SEVERE, null, ex);
            data = new ArrayList<>();
        }
    }
    
  
}