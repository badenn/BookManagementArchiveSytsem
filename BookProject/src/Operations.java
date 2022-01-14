
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;





public class Operations { /// her sınıfın bir construtoru vardır javanın kendi metodurudr
   //Kullanılacak import edilecekler
    Connection con = null;
    Statement sta= null;
    PreparedStatement pata=null;
  
    public int bookCount() {
       int label = 0;
        String sorgu="Select Count(*) From book_database";
        try {
            sta = con.createStatement();
            ResultSet rs = sta.executeQuery(sorgu);
            rs.next();
            label = rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
     return 0;
        }
  return label ;
    }
    
    
    
    
    public void bookDelete(int id){
        String sorgu = "Delete from book_database WHERE id=?";
        try {
            pata=con.prepareStatement(sorgu);
            pata.setInt(1,id);
            pata.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
        }
                
       
    }
    
    public void bookUpdate(int id,String new_name, String new_writer, String new_type,String new_publisher){
  String sorgu ="Update books_databse SET id,book_name=?,book_writer=?,book_type=?,book_publisher=? WHERE id=?";
        try {
         
            pata=con.prepareStatement(sorgu);
   pata.setString(1,new_name);
    pata.setString(2,new_writer);
     pata.setString (3,new_type);
      pata.setString(4,new_publisher);
       pata.setInt(5,id);
       pata.executeUpdate();
      
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
        }
   } 
    
    
    public void bookAdd(String name, String writer, String type, String publisher){
    
    String sorgu ="Insert ınto books_database(book_name,book_writer,book_type,book_publisher) VALUES(?,?,?,?)";
        try {
            pata = con.prepareStatement(sorgu);
            pata.setString(1,name);
            pata.setString(2,writer);
            pata.setString(3,type);
            pata.setString(4,publisher);
            pata.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public ArrayList<Book> BookCome() { //kitapları getirmke için bir metotoluşturduk
        ArrayList<Book> list = new ArrayList<Book>();//book sınıfından bir array list yaptık kitapları lsteledik
        String sorgu = "Select * from books_database";//her dönen değeri while yardımıya bir değişkene aktardık
        
        try {
            sta = con.createStatement();
           ResultSet rs= sta.executeQuery(sorgu);
           
           while(rs.next()) {
               int id = rs.getInt("id");
               String book_name = rs.getString("book_name");
               String book_writer = rs.getString("book_writer");
               String book_type = rs.getString("book_type");
               String book_publisher = rs.getString("book_publisher");
               list.add(new Book(id,book_name,book_writer,book_type,book_publisher));              
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
       return null;
        }
        

    }

  
    
    public Operations() {// url _ jdbc:mysql://host :port/db_name;id;password
   
        String url = "jdbc:mysql://"+Database.host+":"+Database.port+"/"+Database.db_name+"?useUnicode=true&characterEncoding=UTF-8";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection(url,Database.id,Database.password);
            System.out.println("Veritabanı başarıyla bağlandı");// driver ve connection bağlandı
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Driver çalışmadı :/");
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connection çalışamadı :/");
        }
   
    
    }
    
    public boolean Login(String id, String password) {
        String sorgu = "Select * from admin where id= ? and password =?";
        try {
            pata = con.prepareStatement(sorgu);
        pata.setString(1,id);
        pata.setString(2,password);        
        ResultSet rs = pata.executeQuery();
        return rs.next();
        
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
       return false;
                
        }
       
    }
    
    
    public static void main(String []args){
        Operations op = new Operations() ;
    }
}
