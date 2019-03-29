package labstuff.gcu.me.org.mobileplatformdevelopmentcoursework;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Earthquake {

    private String location;
    private Date date;
    private double geolat;
    private double geolong;
    private double depth;
    private double magnitude;

    public Earthquake() {

    }

    public Earthquake(String location, Date date, double geolat, double geolong, double depth, double magnitude) {
        this.location = location;
        this.date = date;
        this.geolat = geolat;
        this.geolong = geolong;
        this.depth = depth;
        this.magnitude = magnitude;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public String getStringDate(){
        Format formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
        String stringDate = formatter.format(date);
        return stringDate;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getGeolat() {
        return geolat;
    }

    public void setGeolat(double geolat) {
        this.geolat = geolat;
    }

    public double getGeolong() {
        return geolong;
    }

    public void setGeolong(double geolong) {
        this.geolong = geolong;
    }

    public double getDepth() {
        return depth;
    }

    public String getStringDepth(){
        String stringDepth = String.valueOf(depth);
        return "Depth was " + stringDepth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getStringMag(){
        String stringMag = String.valueOf(magnitude);
        return "Magnitude was " + stringMag;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }


    @Override
    public String toString() {
        return "Earthquake{" +
                "location='" + location + '\'' +
                ", date=" + date +
                ", geolat=" + geolat +
                ", geolong=" + geolong +
                ", depth=" + depth +
                ", magnitude=" + magnitude +
                '}';
    }
}