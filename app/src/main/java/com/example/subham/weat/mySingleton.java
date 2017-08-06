package com.example.subham.weat;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by subham on 8/2/2017.
 */
public class mySingleton {
    private static mySingleton minstance;
    private RequestQueue rq;
    private static Context mcont;

    private mySingleton (Context context){
        mcont=context;
        rq=getrequestqueue();
    }
    public RequestQueue getrequestqueue(){

        if(rq==null){
            rq= Volley.newRequestQueue(mcont.getApplicationContext());
        }
        return rq;
    }

    public static synchronized mySingleton getinstance(Context context){
        if(minstance==null){
            minstance=new mySingleton(context);
        }
        return minstance;
    }

    public void addtoreq(Request request){
        rq.add(request);

    }
}
