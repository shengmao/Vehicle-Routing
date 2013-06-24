/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.entitiesinmemory;

import Solution.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author lis
 */
public class BenchmarkInstance2 {
    public String instancename;
    public double numberofvehicle;
    public double vehiclecapacity;
    public Location2 startlocation;
    public Location2[] loclist;
    public Delivery2[] dellist;
    public double[][] distancematrix;
    
    public BenchmarkInstance2(String instancename, double numberofvehicle, double vehiclecapacity, Location2 startlocation, Delivery2[] dellist, Location2[] loclist) throws SQLException{
        this.instancename = instancename;
        this.numberofvehicle = numberofvehicle;
        this.vehiclecapacity = vehiclecapacity;
        this.startlocation = startlocation;
        this.dellist = dellist;
        this.loclist = loclist;
        setDistancematrix();
    }

    public String getInstancename() {
        return instancename;
    }

    public double getNumberofvehicle() {
        return numberofvehicle;
    }

    public double getVehiclecapacity() {
        return vehiclecapacity;
    }

    public Location2 getStartlocation() {
        return startlocation;
    }

    public Delivery2[] getDellist() {
        return dellist;
    }

    public double[][] getDistancematrix() {
        return distancematrix;
    }

    public void setInstancename(String instancename) {
        this.instancename = instancename;
    }

    public void setNumberofvehicle(double numberofvehicle) {
        this.numberofvehicle = numberofvehicle;
    }

    public void setVehiclecapacity(double vehiclecapacity) {
        this.vehiclecapacity = vehiclecapacity;
    }

    public void setStartlocation(Location2 startlocation) {
        this.startlocation = startlocation;
    }

    public void setLoclist(Location2[] loclist) {
        this.loclist = loclist;
    }

    public void setDellist(Delivery2[] dellist) {
        this.dellist = dellist;
    }

    public void setDistancematrix() throws SQLException {
        if(loclist != null){
              this.distancematrix = initdistancematrix(loclist);    
        }
        else{
            System.out.println("location list doesn't exist, Distancematrix can not be instantiated");
        }
    }
            
    private double[][] initdistancematrix(Location2[] loclist ) throws SQLException{
        
        System.out.println("initdistancematrix starts to execute");
         double[][] matr = new double[loclist.length][loclist.length];
         for (int j = 0; j < loclist.length; j++) {
            for (int k = 0; k < loclist.length; k++) {
//                matr[j][k] = norm(loclist[j], loclist[k]);
//                load  disctance between two customer location from database.
                  
                  String query = "SELECT * FROM distance WHERE ID_Cus1=" + j + " AND ID_Cus2=" +k;
                  System.out.println(query);
                  ResultSet rs = DatabaseConnection.DBconection(query);
                  //move the cursor to the first line of resultset, this line of code is vital.
                  rs.next();
                  matr[j][k] = Double.parseDouble(rs.getString("Distance"));
            }
        }
        return matr;
    }
    
//    private double norm(Location2 a, Location2 b){
//        double xdiff = a.cordinatex -b.cordinatex;
//        double ydiff = a.cordinatey -b.cordinatey;
//        return Math.sqrt(xdiff*xdiff + ydiff*ydiff);
//        
//    }
}
