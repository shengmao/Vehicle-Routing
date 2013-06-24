/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIView;
import java.util.ArrayList;
/**
 *
 * @author lis
 */
public class StringParser {
    
    static String[] tokens1;
    static String[] tokens2; 
    static String[] tokens3;
    static ArrayList arrlist;
    public static ArrayList convert(String arg){
        
//        String str = "route0: 0 ->8 ->9 ->7 ->1 ->4 ->0\n" +
//                    "Total travel time: 8.82\n" +
//                    "Total delivery demand of route is: 10.0!route1: 0 ->6 ->2 ->5 ->0\n" +
//                    "Total travel time: 8.47\n" +
//                    "Total delivery demand of route is: 7.0!route2: 0 ->3 ->0\n" +
//                    "Total travel time: 2.2\n" +
//                    "Total delivery demand of route is: 1.0!";
       
        String delims = "[!]";
        tokens1 = arg.split(delims);        
        arrlist = new ArrayList();        
        
        for (int i = 0; i < tokens1.length; i++)
        {
           //System.out.println("---!"+i+"----!"+tokens1[i]);                    
            String delims2 = "[\n]+";
            tokens2= tokens1[i].split(delims2);            
            arrlist.add(tokens2);
        }
        
        for(int i=0; i<arrlist.size(); i++){
            String[] subtoken = (String[])arrlist.get(i);
            for(int j=0; j< subtoken.length; j++)
            {
                subtoken[j] = StringParser(subtoken[j]);
                System.out.println(subtoken[j]);                
            }
        }
        
        return arrlist;        
    }    
    
     public static String[] convert2(String arg){    
         String delims = "[->]+";
         tokens3 = arg.split(delims);
         return tokens3;
     }
    
    public static String StringParser(String str){
        String substr = str.substring(str.indexOf(":")+2,str.length());       
        return substr;
    }
    
   
}

