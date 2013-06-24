/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MapRequestWebservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author lis
 */
public class Geolocation_JSON_Call {
    
    //main method for testing
//    public static void main(String[] arg) throws Exception{
//        System.out.println(get_distance());
//    }
    
    
    
     public static JSONObject get_Geolocation(String aStr) throws Exception {
        
        //String aStr = "{locations:[{ city:'fulda', state:'Hessen',country: 'Germany'}]}";
        //String aStr = query;
        aStr = aStr.replace(" ", "+");
        aStr = aStr.replace("=","%3D");
        aStr =aStr.replace("{","%7B");
        aStr =aStr.replace("}","%7D");
        aStr =aStr.replace(":","%3A");
        aStr =aStr.replace("'","%27");
        aStr =aStr.replace(",","%2C");
        aStr =aStr.replace("[","%5B");
        aStr =aStr.replace("]","%5D");
        String secPart = "http://www.mapquestapi.com/geocoding/v1/batch?key=Fmjtd%7Cluua2h02n9%2C75%3Do5-96a5dy&ambiguities=ignore&json=";
        aStr = secPart + aStr;
        System.out.println(aStr);
        URL url = new URL(aStr);
        
        URLConnection urlc = url.openConnection();
        BufferedReader in = new BufferedReader (new InputStreamReader(urlc.getInputStream()));                       
        StringBuilder builder  = new StringBuilder();
        
        for (String line =null;(line = in.readLine()) != null;) {
            builder.append(line);            
            //System.out.println(builder);            
        }

        //parsing response using JSON parser
        JSONTokener tokener = new JSONTokener(builder.toString());
        JSONObject obj = new JSONObject(tokener); 
        JSONArray reultsarr = obj.getJSONArray("results");
        JSONObject resultobject1 = reultsarr.getJSONObject(0);
        JSONArray locdearr1 = resultobject1.getJSONArray("locations");
        JSONObject lonlat1 = locdearr1.getJSONObject(0);
        JSONObject lonlat2 = lonlat1.getJSONObject("latLng");
        
        
//        Double lng = lonlat2.getDouble("lng");
//        Double lat = lonlat2.getDouble("lat");                        
//        System.out.println(lng);
//        System.out.println(lat);
        
        return lonlat2;
        
     }
    
    
}
