/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Solution;

import Entity.entitiesinmemory.Delivery2;
import Entity.entitiesinmemory.Location2;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class ReaddatafromDB {
    
    public String numberofvehicle;
    public String vehiclecapacity;
    public String instancename;
    public Location2[] loclist;
    public Delivery2[] dellist;
    
    public ReaddatafromDB(){}
    
    //main class for testing use
//    public static void main(String arg[]){
//        String query1 = "SELECT * FROM distance WHERE ID_Cus1 =4 AND ID_Cus2 =0";
//        ResultSet rs = DatabaseConnection.DBconection(query1);
//    }
     
    public void Startloading() throws SQLException{
       
        String query ="SELECT customer.ID, customer.Starttime, customer.Endtime, delivery.ID_Delivery, delivery.Demand, delivery.Servicetime FROM customer INNER JOIN delivery ON customer.ID = delivery.ID_Delivery";
         ResultSet rs = DatabaseConnection.DBconection(query);
         
         //Get the size of the resultset 
         int rowcount = 0;
         if (rs.last()) {
             rowcount = rs.getRow();
             rs.beforeFirst(); 
         }
         this.loclist = new Location2[rowcount +1];
         this.dellist = new Delivery2[rowcount +1];
        
         {
             String CoordX = "0.00";
             String CoordY = "0.00";
             String Depot = "Depot";
             this.loclist[0] = new Location2(Depot, Double.parseDouble(CoordX), Double.parseDouble(CoordY));
             this.dellist[0] = new Delivery2(0.00, 0.00, 0.00, 2000.00,  this.loclist[0],  this.loclist[0], 0);

             while( rs.next() ){

                 String deliveryindex = rs.getString("ID_Delivery");
                 String CustNo = rs.getString("ID");     
                 String Demand = rs.getString("Demand");
                 String FromWindow = rs.getString("Starttime");
                 String ToWindow = rs.getString("Endtime");
                 String ServiceTime =rs.getString("Servicetime");


                 Location2 shipto = new Location2(CustNo, Double.parseDouble(CoordX), Double.parseDouble(CoordY));
                 Delivery2 deltmp = new Delivery2(Double.parseDouble(Demand), Double.parseDouble(ServiceTime)*60, Double.parseDouble(FromWindow)*60, Double.parseDouble(ToWindow)*60, this.loclist[0], shipto, Integer.parseInt(deliveryindex) );             


                 this.loclist[Integer.parseInt(deliveryindex)] = shipto;
                 this.dellist[Integer.parseInt(deliveryindex)] = deltmp;

             }
         }
         get_company_info();
         
 ;
    }

    private void get_company_info() throws SQLException{
        String query = "select * from VPR1.company";
        DatabaseConnection db = new DatabaseConnection();
        ResultSet rs = db.DBconection(query);
        
        while( rs.next() ){
            this.numberofvehicle = rs.getString("Vehicle Number");
            this.vehiclecapacity = rs.getString("Vehicle Capacity");
        }                
    }
    
}