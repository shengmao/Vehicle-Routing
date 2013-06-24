/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Jgraphics;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author lis
 */
public class Geocaculator {
 
    
    static Integer x;
    static Integer y;
    
    
    static double fdlon = 9.67469;
    static double fdlat = 50.553558;
    static double ffmlon = 8.68341;
    static double ffmlat = 50.112061;
    static double slon = 9.17192;
    static double slat = 48.76767;
    static double hlon = 9.99245;
    static double hlat = 53.553341;
    static double blon = 13.37698;
    static double blat = 52.516071;
    static double alon = 6.08849;
    static double alat = 50.77813;
    static double klon = 9.17758;
    static double klat = 47.663849;
    
    public static void main(String [] args) throws JSONException{    
        
        caculate(50.553558, 9.67469);

    }
    
    public  static JSONObject caculate(double lat, double lon) throws JSONException{
        
    
        double alt =100; //altitude 
        double Re = 6378137; //Raidus of earth
        double Rp = 6356752.31424518;  //Radius 

        double latrad = lat/180.0*Math.PI;
        double lonrad = lon/180.0*Math.PI;

        double coslat = Math.cos(latrad);
        double sinlat = Math.sin(latrad);
        double coslon = Math.cos(lonrad);
        double sinlon = Math.sin(lonrad);

        double term1 = (Re*Re*coslat)/
          Math.sqrt(Re*Re*coslat*coslat + Rp*Rp*sinlat*sinlat);

        double term2 = alt*coslat + term1;
        
        Double dx=coslon*term2/10000;
        Double dy=sinlon*term2/10000;
        
        x = dx.intValue();
        y = dy.intValue();
        
        //rescale x and y, so they can be shown properly in the map
        x=(x-350)*13-250;
        y=y*13-500;
        
        String json_str = String.format("{'x':%d,'y':%d}",x,y);
        JSONObject  json_object=new JSONObject(json_str);
        System.out.println("x: "+ x);
        System.out.println("y: "+ y);
        
        return json_object;

    }
    
   
}
