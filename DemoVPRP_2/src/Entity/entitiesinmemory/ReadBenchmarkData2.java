/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.entitiesinmemory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author lis
 */
public class ReadBenchmarkData2 {
    public String filename;
    public String numberofvehicle;
    public String vehiclecapacity;
    public String instancename;
    public int nlocation;
    public Location2[] loclist;
    public Delivery2[] dellist;
    
    public ReadBenchmarkData2(String filename, int numoflocation){
        this.filename = filename;
        this.nlocation = numoflocation;
        this.loclist =new Location2[nlocation];
        this.dellist = new Delivery2[nlocation];
    }
    
    public void processlinebyline() throws FileNotFoundException{
        File ffile = new File(filename);
        Scanner scanner = new Scanner(new FileReader(ffile));
        try{
            int linenumber = 1;
            while(scanner.hasNextLine()){
                processline(scanner.nextLine(), linenumber);
                linenumber++;
            }
        }finally{
            scanner.close();
        }
        System.out.println("this has been executedhhahahhah1");
    }   
        
    public void processline(String aline, int linenumber){
        Scanner scanner = new Scanner(aline);
        if(linenumber == 1){
            if(scanner.hasNext()) {
                this.instancename = scanner.next();
            }         
        }
        if(linenumber == 5){
            if(scanner.hasNext()){                          
            this.numberofvehicle = scanner.next();
            this.vehiclecapacity = scanner.next();
            }
        }
        if(linenumber == 10) {
             if(scanner.hasNext()){
                 String CustNo = scanner.next();
                 String CoordX = scanner.next();
                 String CoordY = scanner.next();
                 String Demand = scanner.next();
                 String FromWindow = scanner.next();
                 String ToWindow = scanner.next();
                 String ServiceTime = scanner.next();          
                 
                 Location2 dept = new Location2(CustNo, Double.parseDouble(CoordX), Double.parseDouble(CoordY));
                 Delivery2 deltmp = new Delivery2(Double.parseDouble(Demand), Double.parseDouble(ServiceTime), Double.parseDouble(FromWindow), Double.parseDouble(ToWindow), dept, dept, 0);             
                 
                 this.loclist[0] = dept;
                 this.dellist[0] = deltmp;               
             }
        }if(linenumber > 10 && linenumber <= nlocation +9){
            if(scanner.hasNext()){
                String CustNo = scanner.next();
                 String CoordX = scanner.next();
                 String CoordY = scanner.next();
                 String Demand = scanner.next();
                 String FromWindow = scanner.next();
                 String ToWindow = scanner.next();
                 String ServiceTime = scanner.next(); 
                 
                 Location2 shipto = new Location2(CustNo, Double.parseDouble(CoordX), Double.parseDouble(CoordY));
                 Delivery2 deltmp = new Delivery2(Double.parseDouble(Demand), Double.parseDouble(ServiceTime), Double.parseDouble(FromWindow), Double.parseDouble(ToWindow), loclist[0], shipto, linenumber -10);             
                 
                 this.loclist[linenumber -10] = shipto;
                 this.dellist[linenumber -10] = deltmp;
             }    
        }
        System.out.println("this has been executedhhahahhah2");
     }
    
    public void aoutputmethod(){
           System.out.println("this has been executed3");
    }
}
