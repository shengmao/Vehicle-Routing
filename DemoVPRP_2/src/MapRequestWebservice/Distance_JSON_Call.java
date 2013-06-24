/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MapRequestWebservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;



/**
 *
 * @author lis
 */
public class Distance_JSON_Call {
    
    
    public static Double get_distance(String query ) throws Exception {
        
        //String aStr = "{locations:[{ city:'fulda', state:'Hessen',country: 'Germany'},{city:  'Frankfurt am Main', state:  'Hessen', country: 'Germany'}],options:{allToAll: FALSE, unit: 'k'}}";
        String aStr = query;
        aStr = aStr.replace(" ", "+");
        aStr = aStr.replace("=","%3D");
        aStr =aStr.replace("{","%7B");
        aStr =aStr.replace("}","%7D");
        aStr =aStr.replace(":","%3A");
        aStr =aStr.replace("'","%27");
        aStr =aStr.replace(",","%2C");
        aStr =aStr.replace("[","%5B");
        aStr =aStr.replace("]","%5D");
        String secPart = "http://www.mapquestapi.com/directions/v1/routematrix?key=Fmjtd%7Cluua2h02n9%2C75%3Do5-96a5dy&ambiguities=ignore&outFormat=json&json=";
        aStr = secPart + aStr;
        System.out.println(aStr);
        URL yahoo = new URL(aStr);
        
        URLConnection yc = yahoo.openConnection();
        BufferedReader in = new BufferedReader (new InputStreamReader(yc.getInputStream()));                       
        StringBuilder builder  = new StringBuilder();
        
        for (String line =null;(line = in.readLine()) != null;) {
            builder.append(line);            
            System.out.println(builder);            
        }

        JSONTokener tokener = new JSONTokener(builder.toString());
        JSONObject obj = new JSONObject(tokener); 
        JSONArray orj_1 = obj.getJSONArray("distance");
        Double distance = orj_1.getDouble(1);
        
        return distance;
        
    }
}
