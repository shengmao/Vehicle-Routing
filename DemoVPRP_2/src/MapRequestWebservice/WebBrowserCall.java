/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MapRequestWebservice;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author lis
 */
public class WebBrowserCall {

   public static void getdirection(String args) throws IOException, URISyntaxException{
       //openURL("{locations:[{address:fulda hessen Deutschland},{address:Frankfurt am Main hessen Deutschland},{address:Marburg hessen Deutschland}]}");
       openURL(args);
   }
   
   public static String URLProcess(String query ){
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
        aStr =aStr.replace("ü","u");
        String secPart = "http://www.mapquest.com/routeplanner?format=json&json=";
        aStr = secPart + aStr;
        
        return aStr;
   }
   
    public static void openURL(String query) throws IOException, URISyntaxException{
        
        String url = URLProcess(query);
        
        java.awt.Desktop.getDesktop().browse(new URI(url));
    }
    

    
    
}