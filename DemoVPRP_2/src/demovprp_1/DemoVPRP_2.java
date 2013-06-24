/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demovprp_1;
import Solution.DatabaseConnection;
import Entity.entitiesinmemory.ReadBenchmarkData2;
import Entity.entitiesinmemory.Delivery2;
import Entity.entitiesinmemory.BenchmarkInstance2;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import Solution.MySolution2;
import Solution.ReaddatafromDB;
import java.sql.SQLException;
import UIView.Destop_Test_View;
 


/**
 *
 * @author lis
 */
public class DemoVPRP_2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
 
//		try {
		                   
////                 String filename = "test1.txt";
////                 ReadBenchmarkData2 rbmd = new ReadBenchmarkData2(filename, 5);
////                 rbmd.processlinebyline();
////                 System.out.printf("the length of develiry array is %2$s/%1$s ", Integer.toString(rbmd.dellist.length), rbmd.instancename); 
////                 System.out.println();
////                 for(Delivery2 delivery : rbmd.dellist)
////                 {
////                    System.out.println(delivery.demand);
////
////                 }
//                    ReaddatafromDB rdb = new ReaddatafromDB();
//                    rdb.Startloading();
//                    BenchmarkInstance2 bmInstance = new BenchmarkInstance2(rdb.instancename,
//                    Double.parseDouble(rdb.numberofvehicle),
//                    Double.parseDouble(rdb.vehiclecapacity),
//                    rdb.loclist[0], rdb.dellist,
//                    rdb.loclist);
//                 
//                    MySolution2 aSolution = new MySolution2("Greedy", bmInstance);
                    
                    Destop_Test_View aView = new Destop_Test_View();
                    
                    aView.setVisible(true);
                    
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} 
    }
}
