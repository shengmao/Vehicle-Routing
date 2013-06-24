/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.entitiesinmemory;
import java.util.Arrays;
import java.util.HashMap;
/**
 *
 * @author lis
 */
public class Fahrzeitrestrikition2 {
    
    //fix varaibles
    double fzmax = 4.5*60;
    double atfzmax = 10*60;
    double tfzmax = 540;
    double wfzmax = 56*60;
    double zwfzmax = 90*60;
    double trzint = 24*60;
    double trz = 11*60;
    double wrzint = 144*60;
    
    //temporary variables1
    double t;//Aktuelle ZeitPunkt;
    //public int atfzg;//Anzahl der breeits in Anspruch genommenen täglichen fahrzeigverlängerung seit Beginn der Aktullen Woche;
    
    //temporary varibales need to be compared
    double fzg;//Fahrzeit seit der letzten Pause;
    double ffzg;//Fahrtzeit seit der letzten Ruhezeit;
    double endtrzg;//Endzeitpunkt der voherigen Ruhezeit;
    double endwrzg;//Endzeitpunkt der voherigen wöchentlichen Ruhezeit;
    double wfzg;//Fahrtzeit eines Fahrers seit Beginn der aktuellen Woche;
    double zwfzg;//Fahrzeit eines Fahrers seit Beginn der vorherigen Woche;
    double vfzij;//verbleibende Fahrzeit vfzij vom letzten Kunden i zum neu ausgewählten Kunden j;
    //put all variables into an arry for easy comparison
    double[] variablearr = {vfzij, fzmax-fzg, endtrzg + trzint - trz - t ,tfzmax-ffzg, endtrzg + wrzint - t , wfzmax - wfzg, zwfzmax - zwfzg};
    //HashMap<String, Double> variablemap = new HashMap<String, Double>(); 
    
    public int getsmallest(double[] variables){
        if(variables[0] >= 270){
            return 99;
        }
        if(variables[0] > variables[2] || variables[0] > variables[3] || variables[0] > variables[4] || variables[0] >variables[5] || variables[0] >variables[6]){
            return 99;
        }
        double[] secarr = (double[])variables.clone();
        double[] tharr = (double[])variables.clone();
        Arrays.sort(tharr);
        for(int i = 0; i <= secarr.length; i++){
            if(secarr[i] == tharr[0])
            {
                return i;
            }               
        }
        return -99;
    }

    public Fahrzeitrestrikition2(double t, double fzg, double ffzg, double endtrzg, double endwrzg, double wfzg, double zwfzg, double vfzij) {
        this.t = t;
        this.fzg = fzg;
        this.ffzg = ffzg;
        this.endtrzg = endtrzg;
        this.endwrzg = endwrzg;
        this.wfzg = wfzg;
        this.zwfzg = zwfzg;
        this.vfzij = vfzij;
        
        //initialize an array;
        this.variablearr[0] = vfzij;
        this.variablearr[1] = fzmax-fzg;
        this.variablearr[2] = endtrzg + trzint - trz - t;
        this.variablearr[4] = endwrzg + wrzint - t;
        this.variablearr[5] = wfzmax - wfzg;
        this.variablearr[6] = zwfzmax - zwfzg;
        
        //initialize a Hashmap
//        this.variablemap.put("vfzij", vfzij);
//        this.variablemap.put("endtrzg + trzint - trz - t", endtrzg + trzint - trz - t);
//        this.variablemap.put("fzmax-fzg", fzmax-fzg);
//        this.variablemap.put("endwrzg + wrzint - t;", endwrzg + wrzint - t);
//        this.variablemap.put("wfzmax - wfzg", wfzmax - wfzg);
//        this.variablemap.put("zwfzmax - zwfzg", zwfzmax - zwfzg);
                
        if((tfzmax -ffzg) < vfzij  && vfzij<= (atfzmax - ffzg)){
            this.variablearr[3] = atfzmax -ffzg;            
        }else{
            this.variablearr[3] = tfzmax-ffzg;
        }
    }
 
    public static void main (String args[]){
        Fahrzeitrestrikition2 anInstance = new Fahrzeitrestrikition2(840, 60, 600, 0, 0, 600, 600, 120);
        //anInstance.getsmallest(anInstance.variablearr);
        for(double dub : anInstance.variablearr){
            System.out.println("the member is:" + dub);
        }       
        System.out.println("the result is:" + anInstance.getsmallest(anInstance.variablearr));                  
        
        anInstance.setVfzij(-50);
        for(double dub : anInstance.variablearr){
            System.out.println("the member is:" + dub);
        }       
        System.out.println("the result is:" + anInstance.getsmallest(anInstance.variablearr));          
        }

    public double getT() {
        return t;
    }

    public double getFzg() {
        return fzg;
    }

    public double getFfzg() {
        return ffzg;
    }

    public double getEndtrzg() {
        return endtrzg;
    }

    public double getEndwrzg() {
        return endwrzg;
    }

    public double getWfzg() {
        return wfzg;
    }

    public double getZwfzg() {
        return zwfzg;
    }

    public double getVfzij() {
        return vfzij;
    }

    public double[] getVariablearr() {
        return variablearr;
    }

    public void setT(double t) {
        this.t = t;
        this.variablearr[2] = endtrzg + trzint - trz - t;
        this.variablearr[4] = endwrzg + wrzint - t;
    }

    public void setFzg(double fzg) {
        this.fzg = fzg;
        this.variablearr[1] = fzmax-fzg;
    }

    public void setFfzg(double ffzg) {
        this.ffzg = ffzg;
    }

    public void setEndtrzg(double endtrzg) {
        this.endtrzg = endtrzg;
        this.variablearr[2] = endtrzg + trzint - trz - t;
    }

    public void setEndwrzg(double endwrzg) {
        this.endwrzg = endwrzg;
        this.variablearr[4] = endwrzg + wrzint - t;
    }

    public void setWfzg(double wfzg) {
        this.wfzg = wfzg;
        this.variablearr[5] = wfzmax - wfzg;
    }

    public void setZwfzg(double zwfzg) {
        this.zwfzg = zwfzg;
        this.variablearr[6] = zwfzmax - zwfzg;
    }

    public void setVfzij(double vfzij) {
        this.vfzij = vfzij;
        this.variablearr[0] = vfzij;
    }

    public void setVariablearr(double[] variablearr) {
        this.variablearr = variablearr;
    }
        
        
    }    
   

    
    
     
