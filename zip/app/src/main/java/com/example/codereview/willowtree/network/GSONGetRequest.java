package com.example.codereview.willowtree.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * Created by ExampleCommitter on 9/15/15.
 */

public class GSONGetRequest<T> extends Request<T>
{
    private final Gson gson;
    private final Type type;
    private final Response.Listener<T> listener;

    public GSONGetRequest(final String url, final Type type, final Gson gson, final Response.Listener<T> listener, final Response.ErrorListener errorListener){
        super(Method.GET, url, errorListener);
        this.gson = gson;
        this.type = type;
        this.listener = listener;
    }

    @Override
    protected void deliverResponse(T response)
    {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response)
    {
        try{
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return (Response<T>) Response.success(gson.fromJson(json, type),HttpHeaderParser.parseCacheHeaders(response));
        }catch(UnsupportedEncodingException e){
            return Response.error(new ParseError(e));
        }catch(JsonSyntaxException e){
            return Response.error(new ParseError(e));
        }
    }
}
