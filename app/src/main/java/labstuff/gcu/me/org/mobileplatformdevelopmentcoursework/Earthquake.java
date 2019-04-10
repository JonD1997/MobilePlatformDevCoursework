package labstuff.gcu.me.org.mobileplatformdevelopmentcoursework;

//
// Name                 Jon Doherty
// Student ID           S1514958
// Programme of Study   Computing
//

import android.os.Parcel;
import android.os.Parcelable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Earthquake implements Parcelable {

    private String location;
    private Date date;
    private double geolat;
    private double geolong;
    private double depth;
    private double magnitude;

    public Earthquake()
    {

    }

    private Earthquake(Parcel in) {
        location = in.readString();
        date = new Date(in.readLong());
        geolat = in.readDouble();
        geolong = in.readDouble();
        depth = in.readDouble();
        magnitude = in.readDouble();

    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(location);
        dest.writeLong(date.getTime());
        dest.writeDouble(geolat);
        dest.writeDouble(geolong);
        dest.writeDouble(depth);
        dest.writeDouble(magnitude);

    }

    public static final Parcelable.Creator<Earthquake> CREATOR = new Parcelable.Creator<Earthquake>() {
        public Earthquake createFromParcel(Parcel in) {
            return new Earthquake(in);
        }

        public Earthquake[] newArray(int size) {
            return new Earthquake[size];

        }
    };

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