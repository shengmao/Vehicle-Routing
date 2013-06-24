/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.entitiesinmemory;

/**
 *
 * @author lis
 */
public class Stage2 {
   public Location2 departPoint;
   public Location2 destinationPoint;
   public double startingTime;//after doing the unloading at previous location
   public double arrivingTime;//reaching time to the destination
   public double issuingTime;//time of duing issuing
   public double endTime;//issuing Time + service time
   public double distance;
   public double distanceFromDepot;
   public double pause;
   public double Atraveltime;//duration from depart location -> destination location only without service time consideration
    
    public Stage2(){
    
    }
    
    public Stage2(Location2 departPoint, Location2 destinationPoint, double startingTime, double arrivingTime, double issuingTime, double endTime, double distance, double Atraveltime, double pause) {
        this.departPoint = departPoint;
        this.destinationPoint = destinationPoint;
        this.startingTime = startingTime;
        this.arrivingTime = arrivingTime;
        this.issuingTime = issuingTime;
        this.endTime = endTime;
        this.distance = distance;
        this.Atraveltime = Atraveltime;
        this.pause = pause;
    }

    public Location2 getDepartPoint() {
        return departPoint;
    }

    public Location2 getDestinationPoint() {
        return destinationPoint;
    }

    public double getStartingTime() {
        return startingTime;
    }

    public double getArrivingTime() {
        return arrivingTime;
    }

    public double getIssuingTime() {
        return issuingTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public double getDistance() {
        return distance;
    }

    public double getDistanceFromDepot() {
        return distanceFromDepot;
    }

    public void setDepartPoint(Location2 departPoint) {
        this.departPoint = departPoint;
    }

    public void setDestinationPoint(Location2 destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public void setStartingTime(double startingTime) {
        this.startingTime = startingTime;
    }

    public void setArrivingTime(double arrivingTime) {
        this.arrivingTime = arrivingTime;
    }

    public void setIssuingTime(double issuingTime) {
        this.issuingTime = issuingTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getPause() {
        return pause;
    }

    public void setPause(double pause) {
        this.pause = pause;
    }

    public void setDistanceFromDepot(double distanceFromDepot) {
        this.distanceFromDepot = distanceFromDepot;
    }
}
