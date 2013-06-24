/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Jgraphics;

/**
 *
 * @author lis
 */

import Solution.DatabaseConnection;
import java.awt.*;
import java.awt.geom.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import org.jgraph.*;
import org.jgraph.graph.*;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;
import org.jgrapht.graph.DefaultEdge;
import org.json.JSONException;
import org.json.JSONObject;

public class JGraphAdapterDemo2 extends JApplet{
    
    private ArrayList fal;
    
    public void create_map(ArrayList fal ) throws SQLException, JSONException{
        
        
         //get cutstomer names and store it in an array for access in JGraphAdapterDemo class
        String query1 ="select * from VPR1.customer";
        ResultSet rs1 = DatabaseConnection.DBconection(query1);
        ArrayList al1 = new ArrayList();
        while (rs1.next()) {
            al1.add(rs1.getString("Cityname"));
        }   
        String[] strarr = (String[]) al1.toArray(new String[al1.size()]);

        String query2 ="select * from VPR1.geolocation";
        ResultSet rs2 = DatabaseConnection.DBconection(query2);
        ArrayList al2 = new ArrayList();
        Geocaculator geo_culculator = new Geocaculator();
        while (rs2.next()){
            double lon = Double.parseDouble(rs2.getString("Longitude"));
            double lat = Double.parseDouble(rs2.getString("Latitude"));
            JSONObject  json_object = geo_culculator.caculate(lat,lon);  
            al2.add(json_object);
        }
        
        

        
        
        //instantiate JGraphAdapterDemo and create map
        JGraphAdapterDemo applet = new JGraphAdapterDemo();
        applet.init(fal, strarr, al2);
        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("JGraphT Adapter to JGraph Demo haha");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        
    }
     
    
    
    
    
    
    
    
    
    public static void main(String [] args)
    {
        JGraphAdapterDemo applet = new JGraphAdapterDemo();
        applet.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("JGraphT Adapter to JGraph Demo haha");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
       

}
