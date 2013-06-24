/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Solution;

import MapRequestWebservice.Distance_JSON_Call;
import MapRequestWebservice.Geolocation_JSON_Call;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONObject;

/**
 *
 * @author lis
 */
public class GetGeolocation {
    
    
     //  main method for testing
    public static void main(String[] arg) throws Exception{
        System.out.println(update_Geolocation());
    }
    
    
     public static String update_Geolocation() throws SQLException, Exception{
         
         String query_cus ="SELECT * FROM customer";
         ResultSet rs_cus = DatabaseConnection.DBconection(query_cus);

         //Get the size of the resultset
         int rowcount = 0;
         if (rs_cus.last()) {
           rowcount = rs_cus.getRow();
           rs_cus.beforeFirst(); 
         }
         System.out.println("update_all_dist has been executed");
         System.out.println("the szie of the customer table is:"+rowcount);
         
         for(int i=0; i<=rowcount-1; i++){
             
                 String i_str = Integer.toString(i);
                 String  query_dist="SELECT Longitude, Latitude FROM geolocation WHERE Customer_ID="+ i_str;
                 ResultSet rs_geoloc = DatabaseConnection.DBconection(query_dist);
                        
                 if(!rs_geoloc.next())
                     {
                  //get customer i info from  table "customer"
                     rs_cus.absolute(i+1);
                     String Cityname_i = rs_cus.getString("Cityname");
                     String Statename_i = rs_cus.getString("Statename");
                     String Countryname_i = rs_cus.getString("Countryname");
                                      
                     
                     //call webservice to retrieve distance between i and j                     
                     String request =String.format("{locations:[{ city:%s, state:%s,country: %s}]}", Cityname_i,Statename_i,Countryname_i);
                     //System.out.println(request);
                     JSONObject lon_lat = Geolocation_JSON_Call.get_Geolocation(request);
                     
                     double lng = lon_lat.getDouble("lng");
                     double lat = lon_lat.getDouble("lat"); 
                     
                     
                     //insert  retrieved geolocation into the table distance in database 
                     String query_insert =String.format("INSERT INTO geolocation (Customer_ID, Longitude, Latitude) VALUES (%d,%f,%f)", i,lng, lat);                     
                     //System.out.println(query_insert);
                     int result_int = DatabaseConnection.DBUpdateconection(query_insert);
                     }
                     else 
                     {
                       System.out.println("Geolation for customer"+i+"already exists");
                     }
           }
        
        return("Update has been completed");
     }     
    
}
