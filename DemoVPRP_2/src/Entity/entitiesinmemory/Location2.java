/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.entitiesinmemory;

/**
 *
 * @author lis
 */
public class Location2 {
    public String locationname;
    public double cordinatex;
    public double cordinatey;
    
    public Location2(){};
    public Location2(String locationname, double cordinatex, double cordinatey)
    {
        this.locationname = locationname;
        this.cordinatex = cordinatex;
        this.cordinatey = cordinatey;
    }

    public String getLocationname() {
        return locationname;
    }

    public double getCordinatex() {
        return cordinatex;
    }

    public double getCordinatey() {
        return cordinatey;
    }

    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }

    public void setCordinatex(double cordinatex) {
        this.cordinatex = cordinatex;
    }

    public void setCordinatey(double cordinatey) {
        this.cordinatey = cordinatey;
    }
    
    
    
}
