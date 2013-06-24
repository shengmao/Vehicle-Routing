/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.entitiesinmemory;
import java.util.ArrayList;
/**
 *
 * @author lis
 */
public class Aroute2 {
   
    public ArrayList listOfLocation;//For easy to access, actually location information stay in Delivery
    public ArrayList listOfDelivery;
    public double totalDemand;
    public double totalDistance;
    public double totalTravelTime;
    public  ArrayList listOfStage;
    
    public Aroute2(){
        
    }
    
    public Aroute2(ArrayList ListOfLocation, ArrayList ListOfDelivery, double TotalDemand, double TotalDistance, double TotalTravelTime, ArrayList ListOfStage) {
        this.listOfLocation = ListOfLocation;
        this.listOfDelivery = ListOfDelivery;
        this.totalDemand = TotalDemand;
        this.totalDistance = TotalDistance;
        this.totalTravelTime = TotalTravelTime;
        this.listOfStage = ListOfStage;
    }

    public ArrayList getListOfLocation() {
        return listOfLocation;
    }

    public ArrayList getListOfDelivery() {
        return listOfDelivery;
    }

    public double getTotalDemand() {
        return totalDemand;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public double getTotalTravelTime() {
        return totalTravelTime;
    }

    public ArrayList getListOfStage() {
        return listOfStage;
    }

    public void setListOfLocation(ArrayList listOfLocation) {
        this.listOfLocation = listOfLocation;
    }

    public void setListOfDelivery(ArrayList listOfDelivery) {
        this.listOfDelivery = listOfDelivery;
    }

    public void setTotalDemand(double totalDemand) {
        this.totalDemand = totalDemand;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void setTotalTravelTime(double totalTravelTime) {
        this.totalTravelTime = totalTravelTime;
    }

    public void setListOfStage(ArrayList listOfStage) {
        this.listOfStage = listOfStage;
    }
    
}
