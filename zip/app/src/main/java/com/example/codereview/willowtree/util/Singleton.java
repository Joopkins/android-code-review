package com.example.codereview.willowtree.util;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.codereview.willowtree.activities.NobelPrizeListActivity;

/**
 * Created by ExampleCommitter on 9/15/15.
 */
public class Singleton extends Application {

    private static Singleton singletonInstance;
    private RequestQueue requestQueue;

    @Override
    public void onCreate()
    {
        super.onCreate();
        singletonInstance = this;
    }

    public static Singleton getInstance(){
        if(singletonInstance == null){
            singletonInstance = new Singleton();
        }
        return singletonInstance;
    }

    public RequestQueue getVolleyRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(NobelPrizeListActivity.getStaticContext());
        }
        return requestQueue;
    }

    public static void addRequest(final Request<?> request) {
        getInstance().getVolleyRequestQueue().add(request);
    }
}
