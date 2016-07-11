package com.example.codereview.willowtree.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.codereview.willowtree.R;
import com.example.codereview.willowtree.dataModels.LaureateList;
import com.example.codereview.willowtree.network.ApiHandler;
import com.example.codereview.willowtree.network.GSONGetRequest;
import com.example.codereview.willowtree.util.LaureateListAdapter;
import com.example.codereview.willowtree.util.Singleton;

import java.util.Random;

public class NobelPrizeListActivity extends AppCompatActivity {

    protected static final String TAG = NobelPrizeListActivity.class.getSimpleName();

    private static Context staticContext;

    private ProgressBar progressBar;
    private Button refreshButton;
    private LinearLayout errorView;
    private TextView jsonErrorTextView;
    private RecyclerView listRecyclerView;
    private LaureateListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noble_prize_list);

        staticContext = this;

        errorView = (LinearLayout) findViewById(R.id.error_linearLayout);
        progressBar = (ProgressBar) findViewById(R.id.loading_progressBar);
        refreshButton = (Button) findViewById(R.id.error_refresh_button);
        jsonErrorTextView = (TextView) findViewById(R.id.error_textView);

        setProgressBarColor();

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStart();
            }
        });

        listRecyclerView = (RecyclerView)findViewById(R.id.laureateList_recyclerView);
        RecyclerView.LayoutManager listLayoutManager = new LinearLayoutManager(this);
        listRecyclerView.setLayoutManager(listLayoutManager);
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(isOnline() || connectionIsOn(staticContext)) {
            errorView.setVisibility(View.GONE);
            listRecyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            sendGsonRequest();
        }else{
            showNetworkDialog();
        }
    }

    /**
     * Displays an AlertDialog notifying the user of a lack of connectivity settings and
     * to direct him/her to the appropriate settings menu.
     *
     * One AlertDialog.Builder is used to build a dialog either for airplane mode settings or
     * for wifi/mobile data settings.
     *
     * It is important to note that, to avoid constructing a custom dialog, the "neutral button"
     * is used as the cancel button and the "cancel button" is used to load wifi settings.
     *
     */
    private void showNetworkDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(staticContext);
        if(isAirplaneModeOn(staticContext)){
            builder.setMessage(getString(R.string.airplane_mode_enabled_dialogue_string));
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    startActivity(new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS));
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    progressBar.setVisibility(View.GONE);
                    errorView.setVisibility(View.VISIBLE);
                    jsonErrorTextView.setText(R.string.json_loading_no_connection_error);
                    dialog.cancel();
                }
            });
        }else{
            builder.setMessage(getString(R.string.no_connection_available_dialogue_string));
            builder.setPositiveButton(getString(R.string.mobile), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                    dialog.dismiss();
                }
            });
            builder.setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    progressBar.setVisibility(View.GONE);
                    errorView.setVisibility(View.VISIBLE);
                    jsonErrorTextView.setText(R.string.json_loading_no_connection_error);
                    dialog.cancel();
                }
            });
            builder.setNegativeButton(R.string.wifi, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    dialog.dismiss();
                }
            });
        }
        builder.setTitle(R.string.no_network_dialog_title);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Sets the color of the progressBar to one randomly chosen from the UI colors
     */
    private void setProgressBarColor(){
        Resources res = getResources();
        TypedArray colors = res.obtainTypedArray(R.array.colors);
        Random rand = new Random();
        int randomInt = rand.nextInt(colors.length());
        int color = colors.getColor(randomInt,0);
        progressBar.getIndeterminateDrawable().setColorFilter(color,android.graphics.PorterDuff.Mode.SRC_IN);
    }

    /**
     * @param laureates the list of laureates
     */
    private void setData(final LaureateList laureates)
    {
        listAdapter = new LaureateListAdapter(laureates);
        listRecyclerView.setAdapter(listAdapter);
    }

    /**
     * Method to construct and submit a GSONGetRequest to the Volley requestQueue via the ApiHandler
     */
    private void sendGsonRequest(){
        final GSONGetRequest<LaureateList> gsonGetRequest =
                ApiHandler.getLaureateListObject
                        (
                                new Response.Listener<LaureateList>() {
                                    @Override
                                    public void onResponse(LaureateList laureates) {
                                        progressBar.setVisibility(View.GONE);
                                        setData(laureates);
                                        listRecyclerView.setVisibility(View.VISIBLE);
                                    }
                                }
                                ,
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        // Deal with the error here
                                        progressBar.setVisibility(View.GONE);
                                        errorView.setVisibility(View.VISIBLE);

                                        if (volleyError.networkResponse != null) {
                                            String networkErrorMessage = "Network Error with Status Code: " + volleyError.networkResponse.statusCode;
                                            Log.e(TAG, networkErrorMessage);
                                        }

                                        //Notify the User of the error and prompt for a refresh
                                        if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
                                            jsonErrorTextView.setText(R.string.json_loading_no_connection_error);
                                        } else if (volleyError instanceof ServerError) {
                                            jsonErrorTextView.setText(R.string.json_loading_server_error);
                                        } else if (volleyError instanceof NetworkError) {
                                            jsonErrorTextView.setText(R.string.json_loading_network_error);
                                        } else if (volleyError instanceof ParseError) {
                                            jsonErrorTextView.setText(R.string.json_loading_parse_error);
                                        } else if (volleyError instanceof AuthFailureError) {
                                            jsonErrorTextView.setText(R.string.json_loading_auth_failure_error);
                                        }
                                    }
                                }
                        );
        //Add the constructed Gson request to the Volley requestQueue
        Singleton.addRequest(gsonGetRequest);
    }

    /**
     * @return whether or not the device is able to establish a network connection
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * @param context
     * @return whether or not the user has disabled all network communications
     */
    public boolean connectionIsOn(Context context){
        if(isWifiModeOn(context) || isMobileDataModeOn(context)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @param context
     * @return whether or not the user has enabled airplane mode
     */
    private boolean isAirplaneModeOn(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }
    }

    /**
     * @param context
     * @return whether or not the user has enabled wifi
     */
    private boolean isWifiModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(),Settings.Global.WIFI_ON, 0) != 0;
    }

    /**
     * @param context
     * @return whether or not the user has enabled mobile data
     */
    private boolean isMobileDataModeOn(Context context) {
        boolean mobileYN = false;
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1){
            mobileYN = Settings.Global.getInt(context.getContentResolver(), context.getString(R.string.mobile_data), 1) == 1;
        }else{
            mobileYN = Settings.Secure.getInt(context.getContentResolver(), context.getString(R.string.mobile_data), 1) == 1;
        }
        return mobileYN;
    }

    /**
     * @return static context
     */
    public static Context getStaticContext() {
        return staticContext;
    }
}
