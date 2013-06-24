/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Solution;

import java.sql.ResultSet;
import java.sql.SQLException;
import MapRequestWebservice.Distance_JSON_Call;
/**
 *
 * @author lis
 */
public class CalculateDistance {
     
    
    public static void main(String arg[]) throws SQLException, Exception{
        Startcalculating();
    }
            
            
     //check Distance between each two customer in table "distance", when not existing, retrieve the distance 
     //using webservice and update the database
     public static String Startcalculating() throws SQLException{
     try{
         String output_str;  
         StringBuilder builder  = new StringBuilder();
         int num = 0;
         String query_cus ="SELECT * FROM customer";
         ResultSet rs_cus = DatabaseConnection.DBconection(query_cus);
         
         //Get the size of the resultset
         int rowcount = 0;
         if (rs_cus.last()) {
           rowcount = rs_cus.getRow();
           rs_cus.beforeFirst(); 
         }
         System.out.println("the szie of the customer table is:"+rowcount);
         
         
         for(int i=0; i<=rowcount-1; i++){
             for(int j=0; j<=rowcount-1; j++){
                 String i_str = Integer.toString(i);
                 String j_str = Integer.toString(j);
                  String  query_dist="SELECT ID_Distance, Distance FROM distance WHERE ID_Cus1="+ i_str+" AND ID_Cus2=" +j_str;
                  System.out.println(query_dist);
                  ResultSet rs_dist = DatabaseConnection.DBconection(query_dist);
                  
                  
                  //add a new row in table "distance" if there is no row exists for two certain customers
                  if(!rs_dist.next()){
                      
                  //get customer i info from  table "customer"
                     rs_cus.absolute(i+1);
                     String Cityname_i = rs_cus.getString("Cityname");
                     String Statename_i = rs_cus.getString("Statename");
                     String Countryname_i = rs_cus.getString("Countryname");
                     
                     //get customer j info from  table "customer"
                     rs_cus.absolute(j+1);
                     String Cityname_j = rs_cus.getString("Cityname");
                     String Statename_j = rs_cus.getString("Statename");
                     String Countryname_j = rs_cus.getString("Countryname");
                     
                     //call webservice to retrieve distance between i and j                     
                     String request =String.format("{locations:[{ city:%s, state:%s,country: %s},{city:  %s, state:  %s, country: %s}],options:{allToAll: FALSE, unit: 'k'}}", Cityname_i,Statename_i,Countryname_i,Cityname_j,Statename_j, Countryname_j);                                                    
                     //System.out.println("------------>distance is null");
                     System.out.println(request);
                     Double dist = Distance_JSON_Call.get_distance(request);
                     
                     output_str = String.format("Distance between %s and %s is %f ", Cityname_i, Cityname_j, dist);                     
                     builder.append(output_str);
                     
                     
                     //insert  retrieved distance into the table distance in database 
                     String query_insert =String.format("INSERT INTO Distance (ID_Cus1, ID_Cus2, Distance) VALUES (%d,%d,%f)", i,j, dist);                     
                     ++num;
                     System.out.println(query_insert);
                     int result_int = DatabaseConnection.DBUpdateconection(query_insert);
                     System.out.println("result is:" + result_int);
                     
                 }
                  else {   
                      //add value column "Distance" if the column "distance" is empty in a row 
                      if(rs_dist.getString("Distance").length() ==0){
                         
                         //get customer i info from  table "customer"
                         rs_cus.absolute(i+1);
                         String Cityname_i = rs_cus.getString("Cityname");
                         String Statename_i = rs_cus.getString("Statename");
                         String Countryname_i = rs_cus.getString("Countryname");
     
                       
                         //get customer j info from  table "customer"
                         rs_cus.absolute(j+1);
                         String Cityname_j = rs_cus.getString("Cityname");
                         String Statename_j = rs_cus.getString("Statename");
                         String Countryname_j = rs_cus.getString("Countryname");

                         //call webservice to retrieve distance between i and j
                         String request =String.format("{locations:[{ city:%s, state:%s,country: %s},{city:  %s, state:  %s, country: %s}],options:{allToAll: FALSE, unit: 'k'}}", Cityname_i,Statename_i,Countryname_i,Cityname_j,Statename_j, Countryname_j);                                                    
                         System.out.println(request);
                         Double dist = Distance_JSON_Call.get_distance(request);
                         
                         output_str = String.format("Distance between %s and %s is %f ", Cityname_i, Cityname_j, dist); 
                         builder.append(output_str);
                         
                         
                         //update the table distance in database with retrieved distance
                         String query_insert =String.format("UPDATE  Distance SET ID_Cus1 =%d, ID_Cus2 =%d, Distance =%f WHERE ID_Distance = %s", i,j, dist, rs_dist.getString("ID_Distance"));
                         ++num;
                         System.out.println(query_insert);
                         int result_int = DatabaseConnection.DBUpdateconection(query_insert);
                         System.out.println("result is:" + result_int);
                         
                          }
                          else{
                              String distance = rs_dist.getString("Distance");
                              System.out.println("----------->"+distance);  
                              System.out.println("----------->length:" + distance.length());                                                            
                      }                                              
                 }                                                
             } 
         }
          if (builder.length() ==0){
              builder.append("No new distance was added");
              return builder.toString();
          }
          else{
             System.out.println(builder.toString());
             String str =String.format("%d distances have been added", num) ;
              return str;
          }
          
     }
     catch(Exception ex){
         return("The customer info can not be used to get distance, please refer to http://www.mapquest.com/");
         
     }
     }
     
     
     public static String update_all_dist() throws SQLException, Exception{
         
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
             for(int j=0; j<=rowcount-1; j++){
                 String i_str = Integer.toString(i);
                 String j_str = Integer.toString(j);
                 String  query_dist="SELECT ID_Distance, Distance FROM distance WHERE ID_Cus1="+ i_str+" AND ID_Cus2=" +j_str;
                 ResultSet rs_dist = DatabaseConnection.DBconection(query_dist);
                                                                
                  //get customer i info from  table "customer"
                     rs_cus.absolute(i+1);
                     String Cityname_i = rs_cus.getString("Cityname");
                     String Statename_i = rs_cus.getString("Statename");
                     String Countryname_i = rs_cus.getString("Countryname");
                     
                     //get customer j info from  table "customer"
                     rs_cus.absolute(j+1);
                     String Cityname_j = rs_cus.getString("Cityname");
                     String Statename_j = rs_cus.getString("Statename");
                     String Countryname_j = rs_cus.getString("Countryname");
                     
                     //call webservice to retrieve distance between i and j                     
                     String request =String.format("{locations:[{ city:%s, state:%s,country: %s},{city:  %s, state:  %s, country: %s}],options:{allToAll: FALSE, unit: 'k'}}", Cityname_i,Statename_i,Countryname_i,Cityname_j,Statename_j, Countryname_j);                                                    
                     //System.out.println("------------>distance is null");
                     System.out.println(request);
                     Double dist = Distance_JSON_Call.get_distance(request);                                         
                     
                     if(!rs_dist.next())
                     {
                     //insert  retrieved distance into the table distance in database 
                     String query_insert =String.format("INSERT INTO Distance (ID_Cus1, ID_Cus2, Distance) VALUES (%d,%d,%f)", i,j, dist);                     
                     System.out.println(query_insert);
                     int result_int = DatabaseConnection.DBUpdateconection(query_insert);
                     }
                     else 
                     {
                         if(rs_dist.getString("Distance").length() ==0){
                             String query_insert =String.format("UPDATE  Distance SET ID_Cus1 =%d, ID_Cus2 =%d, Distance =%f WHERE ID_Distance = %s", i,j, dist, rs_dist.getString("ID_Distance"));
                             int result_int = DatabaseConnection.DBUpdateconection(query_insert);
                             System.out.println(query_insert);  
                         }else{
                             if(rs_dist.getString("Distance") ==  String.valueOf(dist)){
                                 
                             }else{
                                  String query_insert =String.format("UPDATE  Distance SET ID_Cus1 =%d, ID_Cus2 =%d, Distance =%f WHERE ID_Distance = %s", i,j, dist, rs_dist.getString("ID_Distance"));
                                  int result_int = DatabaseConnection.DBUpdateconection(query_insert);
                                  System.out.println(query_insert);
                             }
                            
                         }
                     }
           }
        }
        return("Update has been completed");
     }     
     
}
