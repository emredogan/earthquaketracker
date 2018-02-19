package com.example.android.quakereport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.graphics.drawable.GradientDrawable;

import org.w3c.dom.Text;

/**
 * Created by emredogan on 18/02/2018.
 */

public class EarthQuakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = " of ";


    public EarthQuakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, earthquakes);
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        final Context context = getContext();





        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        final Earthquake currentQuake = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView

        Double magnitude = currentQuake.getMagnitude();

        DecimalFormat formatter = new DecimalFormat("0.0");
        String magnitudeFormatted = formatter.format(magnitude);



        magnitudeTextView.setText(magnitudeFormatted);



        String originalLocation = currentQuake.getLocation();

        String primaryLocation;
        String locationOffset;

        Log.v("TotalLocation", originalLocation);

        String[] parts = originalLocation.split("of");

        if (originalLocation.contains(LOCATION_SEPARATOR)) {

            String distanceLocation = parts[0];
            String cityLocation = parts[1];

            Log.v("DistanceLocation", distanceLocation);
            Log.v("CityLocation", cityLocation);


            // Find the TextView with view ID location
            TextView cityLocationView = (TextView) listItemView.findViewById(R.id.primary_location);
            // Display the location of the current earthquake in that TextView
            cityLocationView.setText(cityLocation.trim());

            // Find the TextView with view ID location
            TextView distanceLocationView = (TextView) listItemView.findViewById(R.id.location_offset);
            // Display the location of the current earthquake in that TextView
            distanceLocationView.setText(distanceLocation + "of");

        } else {

            // Find the TextView with view ID location
            TextView cityLocationView = (TextView) listItemView.findViewById(R.id.primary_location);
            // Display the location of the current earthquake in that TextView
            cityLocationView.setText(originalLocation.trim());

            // Find the TextView with view ID location
            TextView distanceLocationView = (TextView) listItemView.findViewById(R.id.location_offset);
            // Display the location of the current earthquake in that TextView
            distanceLocationView.setText("Near the");



        }

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentQuake.getMagnitude());

        Log.i("Amount", Double.toString(currentQuake.getMagnitude()));

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);




        Date dateObject = new Date(currentQuake.getTimeInMilliseconds());

        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        timeView.setText(formattedTime);

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String url = currentQuake.getUrl();
                Log.i("URL", url);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });



        return listItemView;
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
