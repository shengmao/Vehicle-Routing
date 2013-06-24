/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.entitiesinmemory;

import java.util.Comparator;

/**
 *
 * @author lis
 */
public class Delivery2 {

    public double demand;
    public double servicetime;
    public double timewindowfrom;
    public double timewindowto;
    public Location2 locationfrom;
    public Location2 locationto;
    public int deliveryindex;

    public Delivery2() {
    }

    ;
    
    public Delivery2(double demand, double servicetime, double timewindowfrom, double timewindowto, Location2 locationfrom, Location2 locationto) {
        this.demand = demand;
        this.servicetime = servicetime;
        this.timewindowfrom = timewindowfrom;
        this.timewindowto = timewindowto;
        this.locationfrom = locationfrom;
        this.locationto = locationto;


    }

    public Delivery2(double demand, double servicetime, double timewindowfrom, double timewindowto, Location2 locationfrom, Location2 locationto, int deliveryindex) {
        this(demand, servicetime, timewindowfrom, timewindowto, locationfrom, locationto);
        this.deliveryindex = deliveryindex;
    }

    public Delivery2(Delivery2 del) {
        this(del.demand, del.servicetime, del.timewindowfrom, del.timewindowto, del.locationfrom, del.locationto);
        this.deliveryindex = del.deliveryindex;
    }

    public double getDemand() {
        return demand;
    }

    public double getServicetime() {
        return servicetime;
    }

    public double getTimewindowfrom() {
        return timewindowfrom;
    }

    public double getTimewindowto() {
        return timewindowto;
    }

    public Location2 getLocationfrom() {
        return locationfrom;
    }

    public Location2 getLocationto() {
        return locationto;
    }

    public int getDeliveryindex() {
        return deliveryindex;
    }

    public void setDemand(double demand) {
        this.demand = demand;
    }

    public void setServicetime(double servicetime) {
        this.servicetime = servicetime;
    }

    public void setTimewindowfrom(double timewindowfrom) {
        this.timewindowfrom = timewindowfrom;
    }

    public void setTimewindowto(double timewindowto) {
        this.timewindowto = timewindowto;
    }

    public void setLocationfrom(Location2 locationfrom) {
        this.locationfrom = locationfrom;
    }

    public void setLocationto(Location2 locationto) {
        this.locationto = locationto;
    }

    public void setDeliveryindex(int deliveryindex) {
        this.deliveryindex = deliveryindex;
    }

     public static Comparator<Delivery2> getComparator(SortParameter... sortParameters) {
        return new DeliveryComparator(sortParameters);
    }
    
    public enum SortParameter {

        EARLIEST_DATELINE_ASC, DISTANCE_DES, DEMAND_DES, DISTANCE_ASC;
    }

    private static class DeliveryComparator implements Comparator<Delivery2> {

        private SortParameter[] parameters;

        private DeliveryComparator(SortParameter[] parameters) {
            this.parameters = parameters;
        }

        private double norm(Location2 a, Location2 b) {
            double xdiff = a.cordinatex - b.cordinatex;
            double ydiff = a.cordinatey - b.cordinatey;
            return Math.sqrt(xdiff * xdiff + ydiff * ydiff);
        }

        public int compare(Delivery2 o1, Delivery2 o2) {
            int comparison;
            for (SortParameter parameter : parameters) {
                switch (parameter) {
                    case EARLIEST_DATELINE_ASC:
                        comparison = Double.compare(o2.timewindowfrom, o1.timewindowfrom);
                        if (comparison != 0) {
                            return comparison;
                        }
                        break;
                    case DISTANCE_ASC:
                        comparison = Double.compare(norm(o1.locationfrom, o1.locationto), norm(o2.locationfrom, o2.locationto));
                        if (comparison != 0) {
                            return comparison;
                        }
                        break;
                    case DISTANCE_DES:
                        double dist1 = norm(o1.locationfrom, o1.locationto);
                        double dist2 = norm(o2.locationfrom, o2.locationto);
                        comparison = Double.compare(dist2, dist1);
                        if (comparison != 0) {
                            return comparison;
                        }
                        break;
                    case DEMAND_DES:
//                        double dem1=norm(o1.DeliveryLocation,o1.ShipToParty);
//                        double dem2=norm(o2.DeliveryLocation,o2.ShipToParty);
                        comparison = Double.compare(o2.demand, o1.demand);
                        if (comparison != 0) {
                            return comparison;
                        }
                        break;
                }
            }
            return 0;
        }
    }
}
