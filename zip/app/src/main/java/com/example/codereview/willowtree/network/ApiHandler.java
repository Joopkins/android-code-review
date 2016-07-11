package com.example.codereview.willowtree.network;

import com.android.volley.Response;
import com.example.codereview.willowtree.R;
import com.example.codereview.willowtree.activities.NobelPrizeListActivity;
import com.example.codereview.willowtree.dataModels.LaureateList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by ExampleCommitter on 9/15/15.
 */
public class ApiHandler {

    protected static final String APIURL = NobelPrizeListActivity.getStaticContext().getString(R.string.api_url);
    protected static final String BORNCOUNTRY = NobelPrizeListActivity.getStaticContext().getString(R.string.bornCountry);
    protected static final String POLAND = NobelPrizeListActivity.getStaticContext().getString(R.string.poland);

    public static GSONGetRequest<LaureateList> getLaureateListObject(final Response.Listener<LaureateList> listener, final Response.ErrorListener errorListener)
    {
        //Construct the api url and return a new GSONGetRequest
        final String url = APIURL+"?"+BORNCOUNTRY+"="+POLAND;
        final Gson gson = new Gson();
        return new GSONGetRequest(url, new TypeToken<LaureateList>() {}.getType(), gson, listener, errorListener);
    }
}
