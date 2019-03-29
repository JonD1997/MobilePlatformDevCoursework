package labstuff.gcu.me.org.mobileplatformdevelopmentcoursework;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuakeAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    List<Earthquake> earthquakeList;
    ArrayList<Earthquake> arrayList;

    public QuakeAdapter(Context context, List<Earthquake> earthquakeList) {
        mContext = context;
        this.earthquakeList = earthquakeList;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<Earthquake>();
        this.arrayList.addAll(earthquakeList);
    }

    public class ViewHolder{
        TextView mlistview_location, mlistview_date, mlistview_mag, mlistview_depth;
        ImageView marrow_button;
    }

    @Override
    public int getCount() {
        return earthquakeList.size();
    }

    @Override
    public Object getItem(int i) {
        return earthquakeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.quakelist_item, null);

            holder.mlistview_location = view.findViewById(R.id.listview_location);
            holder.marrow_button = view.findViewById(R.id.arrow_button);
            holder.mlistview_date = view.findViewById(R.id.listview_date);
            holder.mlistview_mag = view.findViewById(R.id.listview_mag);
            holder.mlistview_depth = view.findViewById(R.id.listview_depth);

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder)view.getTag();
        }

        holder.mlistview_location.setText(earthquakeList.get(position).getLocation());
        holder.mlistview_date.setText(earthquakeList.get(position).getStringDate());
        holder.mlistview_mag.setText(earthquakeList.get(position).getStringMag());
        holder.mlistview_depth.setText(earthquakeList.get(position).getStringDepth());

        holder.marrow_button.setOnClickListener(new View.OnClickListener() {
            private boolean hasBeenClicked = false;
            @Override
            public void onClick(View v) {
                if (!hasBeenClicked) {
                    holder.marrow_button.setRotation(holder.marrow_button.getRotation() + 90);
                    holder.mlistview_mag.setVisibility(View.VISIBLE);
                    holder.mlistview_depth.setVisibility(View.VISIBLE);
                }
                else{
                    holder.marrow_button.setRotation(holder.marrow_button.getRotation() - 90);
                    holder.mlistview_mag.setVisibility(View.GONE);
                    holder.mlistview_depth.setVisibility(View.GONE);
                }
                hasBeenClicked = ! hasBeenClicked;
            }
        });
        return view;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        earthquakeList.clear();
        if(charText.length()==0){
            earthquakeList.addAll(arrayList);
        }
        else {
            for(Earthquake earthquake : arrayList ){
                if (earthquake.getLocation().toLowerCase(Locale.getDefault()).contains(charText) ||
                        earthquake.getStringMag().toLowerCase(Locale.getDefault()).contains(charText) ||
                        earthquake.getStringDate().toLowerCase(Locale.getDefault()).contains(charText)){
                    earthquakeList.add(earthquake);
                }
            }
        }
        notifyDataSetChanged();
    }
}