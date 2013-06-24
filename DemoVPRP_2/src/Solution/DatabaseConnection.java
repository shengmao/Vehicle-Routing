/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Solution;
import java.sql.* ;
/**
 *
 * @author Admin
 */
public class DatabaseConnection {
    
//   public static void main(String args[]){
//       String databasename = "lisehngmao";
//       String DBname = "jdbc:mysql://localhost:3306/" + databasename;
//       System.out.println(DBname);
//   }
    
     static int numofexecution= 0;

     public static ResultSet DBconection(String query )
     {       
          try
         {
          numofexecution++;
          System.out.println("program has been executed " + numofexecution +" times");
          // Load the database driver
          Class.forName( "com.mysql.jdbc.Driver" ).newInstance();           
    
          // Get a connection to the database
          Connection conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/vpr1","root","" ) ;

          // Get a statement from the connection
          Statement stmt = conn.createStatement() ;

          // Execute the query
          ResultSet rs = stmt.executeQuery(query) ;
         
          // Loop through the result set
          //should not execute next(), otherwise the cursor would reach the end of the resultset
//          while( rs.next() )
//          System.out.println( rs.getString(1) ) ;
           
          // Close the result set, statement and the connection
          //should not close here otherwise the resultset can not be used outside the class
//          rs.close() ;
//          stmt.close() ;
//          conn.close() ;
         
           //return Resultset
          return rs;
         }
    
      catch( SQLException se )
         {
          System.out.println( "SQL Exception:" ) ;

          // Loop through the SQL Exceptions        
             {
              System.out.println( "State  : " + se.getSQLState()  ) ;
              System.out.println( "Message: " + se.getMessage()   ) ;
              System.out.println( "Error  : " + se.getErrorCode() ) ;        
             }
         }
      catch( Exception e )
         {
          System.out.println( e +"second exception is caught") ;
         }
        return null;
       
     }
     
     
       public static int DBUpdateconection(String query )
 {       
          try
         {
          numofexecution++;
          System.out.println("program has been executed " + numofexecution +" times");
          // Load the database driver
          Class.forName( "com.mysql.jdbc.Driver" ).newInstance();           
    
          // Get a connection to the database
          Connection conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/vpr1","root","" ) ;

          // Get a statement from the connection
          Statement stmt = conn.createStatement() ;

          // Execute the query
          int rs = stmt.executeUpdate(query) ;
         
          // Loop through the result set
          //should not execute next(), otherwise the cursor would reach the end of the resultset
//          while( rs.next() )
//          System.out.println( rs.getString(1) ) ;
           
          // Close the result set, statement and the connection
          //should not close here otherwise the resultset can not be used outside the class
//          rs.close() ;
//          stmt.close() ;
//          conn.close() ;
         
           //return Resultset
          return rs;
         }
    
      catch( SQLException se )
         {
          System.out.println( "SQL Exception:" ) ;

          // Loop through the SQL Exceptions      
             {
              System.out.println( "State  : " + se.getSQLState()  ) ;
              System.out.println( "Message: " + se.getMessage()   ) ;
              System.out.println( "Error  : " + se.getErrorCode() ) ;        
             }
         }
      catch( Exception e )
         {
          System.out.println( e +"second exception is caught") ;
         }
        return -1;
       
     }
        
    }
