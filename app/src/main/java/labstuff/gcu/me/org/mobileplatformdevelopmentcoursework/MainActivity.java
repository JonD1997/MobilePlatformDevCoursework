package labstuff.gcu.me.org.mobileplatformdevelopmentcoursework;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private ListView listView;
    private QuakeAdapter mAdapter;
    SearchView mySearchView = null;
    ArrayList<Earthquake> earthquakesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.quake_listview);

        try{
            Exception otherthreadresult = new otherThread().execute().get();
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }
        mAdapter = new QuakeAdapter(this,earthquakesList);
        listView.setAdapter(mAdapter);


    }
    public void Map (MenuItem menuItem){
        Intent intent = new Intent(getApplicationContext(),MapActivity.class);
        intent.putExtra("Earthquake", earthquakesList);
        startActivity(intent);
    }


    //---------------------------//MENU SETUP //---------------------------//
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        MenuItem refreshButton = menu.findItem(R.id.refresh);
        SearchView searchView = (SearchView)myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){
                    mAdapter.filter("");
                    listView.clearTextFilter();
                }
                else {
                    mAdapter.filter(s);
                }
                return true;
            }
        });


        return true;
    }
    //---------------------------//MENU SETUP END //---------------------------//


    public void Refresh(MenuItem menuItem){
        Collections.sort(earthquakesList, Collections.reverseOrder(new CustomComparatorDate()));
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Refresh Successful!", Toast.LENGTH_LONG).show();
        System.out.println("works");
    }

    //---------------------------//SORTING METHODS //---------------------------//
    public void sortLocation(MenuItem menuItem){
        Collections.sort(earthquakesList, new CustomComparatorLocation());
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Sorted by Location!", Toast.LENGTH_LONG).show();
        System.out.println("works");
    }
    public void sortDate(MenuItem menuItem){
        Collections.sort(earthquakesList, new CustomComparatorDate());
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Sorted by Oldest to Newest!", Toast.LENGTH_LONG).show();
        System.out.println("works");
    }
    public void sortMag(MenuItem menuItem){
        Collections.sort(earthquakesList, Collections.reverseOrder(new CustomComparatorMag()));
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Sorted by Largest Magnitude!", Toast.LENGTH_LONG).show();
        System.out.println("works");
    }
    public void sort_north(MenuItem menuItem){
        Collections.sort(earthquakesList, new CustomComparatorLat());
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Sorted by Most Southern Earthquake!", Toast.LENGTH_LONG).show();
        System.out.println("works");
    }
    public void sort_south(MenuItem menuItem){
        Collections.sort(earthquakesList, Collections.reverseOrder(new CustomComparatorLat()));
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Sorted by Most Northern Earthquake!", Toast.LENGTH_LONG).show();
        System.out.println("works");
    }
    public void sort_east(MenuItem menuItem){
        Collections.sort(earthquakesList, Collections.reverseOrder(new CustomComparatorLong()));
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Sorted by Most Eastern Earthquake!", Toast.LENGTH_LONG).show();
        System.out.println("works");
    }
    public void sort_west(MenuItem menuItem){
        Collections.sort(earthquakesList, new CustomComparatorLong());
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Sorted by Most Western Earthquake!", Toast.LENGTH_LONG).show();
        System.out.println("works");
    }
    public void sort_shallow(MenuItem menuItem){
        Collections.sort(earthquakesList, new CustomComparatorDepth());
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Sorted by Shallowest Earthquake!", Toast.LENGTH_LONG).show();
        System.out.println("works");
    }
    public void sort_deepest(MenuItem menuItem){
        Collections.sort(earthquakesList, Collections.reverseOrder(new CustomComparatorDepth()));
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Sorted by Deepest Earthquake!", Toast.LENGTH_LONG).show();
        System.out.println("works");
    }

    private class CustomComparatorLocation implements Comparator<Earthquake>{
        @Override
        public int compare(Earthquake o1, Earthquake o2) {
            return o1.getLocation().compareTo(o2.getLocation());
        }
    }
    private class CustomComparatorDate implements Comparator<Earthquake>{
        @Override
        public int compare(Earthquake o1, Earthquake o2) {
            return o1.getDate().compareTo(o2.getDate());
        }
    }
    private class CustomComparatorMag implements Comparator<Earthquake>{
        @Override
        public int compare(Earthquake o1, Earthquake o2) {
            return Double.valueOf(o1.getMagnitude()).compareTo(Double.valueOf(o2.getMagnitude()));
        }
    }
    private class CustomComparatorLat implements Comparator<Earthquake>{
        @Override
        public int compare(Earthquake o1, Earthquake o2) {
            return  Double.valueOf(o1.getGeolat()).compareTo(Double.valueOf(o2.getGeolat()));
        }
    }
    private class CustomComparatorLong implements Comparator<Earthquake>{
        @Override
        public int compare(Earthquake o1, Earthquake o2) {
            return  Double.valueOf(o1.getGeolong()).compareTo(Double.valueOf(o2.getGeolong()));
        }
    }
    private class CustomComparatorDepth implements Comparator<Earthquake>{
        @Override
        public int compare(Earthquake o1, Earthquake o2) {
            return  Double.valueOf(o1.getDepth()).compareTo(Double.valueOf(o2.getDepth()));
        }
    }
    //---------------------------//SORTING METHODS ENDING//---------------------------//


    public InputStream getInputStream(URL url) {

        try {
            //Opens the connection to the link so we can read from it
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    //---------------------------//OTHER THREAD START //---------------------------//
    //Set up other thread to run in background
    public class otherThread extends AsyncTask<Integer, Integer, Exception> {

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        Exception exception = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... integers) {

            try {
                URL url = new URL("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");

                //tells program if we are inside the item
                boolean insideItem = false;


                int eventType = xpp.getEventType();
                Earthquake newQuake = null;
                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {
                        //finds the start tag of the item tag which is what we need
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            newQuake = new Earthquake();
                            insideItem = true;
                        } else if (xpp.getName().equalsIgnoreCase("description")) {

                            if (insideItem) {

                                String text = xpp.nextText();
                                String[] splitarray = text.split(";");
                                String date = splitarray[0];
                                String location = splitarray[1];
                                String depth = splitarray[3];
                                String magnitude = splitarray[4];

                                date = date.replace("Origin date/time: ", "");
                                SimpleDateFormat correctDate = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
                                try {
                                    Date earthquakedate = correctDate.parse(date);
                                    newQuake.setDate(earthquakedate);


                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                location = location.replace(" Location: ", "");
                                newQuake.setLocation(location);

                                //newQuakes.add(newQuake);

                                depth = depth.replace(" Depth: ", "");
                                depth = depth.replace(" km ", "");
                                double earthquakeDepth = Double.parseDouble(depth);
                                newQuake.setDepth(earthquakeDepth);

                                magnitude = magnitude.replace(" Magnitude: ", "");
                                double earthquakeMag = Double.parseDouble(magnitude);
                                newQuake.setMagnitude(earthquakeMag);
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("geo:lat")) {
                            if (insideItem) {
                                String text = xpp.nextText();
                                double geolat = Double.parseDouble(text);
                                newQuake.setGeolat(geolat);
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("geo:long")) {
                            if (insideItem) {
                                String text = xpp.nextText();
                                double geolong = Double.parseDouble(text);
                                newQuake.setGeolong(geolong);
                            }
                        }
                    }
                    //check if its the end of the item tag
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        //set inside item back to false
                        insideItem = false;
                        earthquakesList.add(newQuake);
                    }

                    eventType = xpp.next();
                }
                //earthquakesList.remove(0);
                System.out.println("Jon" + earthquakesList.toString());


            } catch (MalformedURLException e) {
                exception = e;
            } catch (XmlPullParserException e) {
                exception = e;
            } catch (IOException e) {
                exception = e;
            }


            return exception;
        }
        //---------------------------//OTHER THREAD END //---------------------------//

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);



            progressDialog.dismiss();
        }
    }
}

