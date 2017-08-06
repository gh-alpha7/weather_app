package com.example.subham.weat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1= (Button) findViewById(R.id.button);
        final EditText search= (EditText) findViewById(R.id.editText);
        final TextView tv= (TextView) findViewById(R.id.textView2);
        final TextView result= (TextView) findViewById(R.id.textView);
        final ImageView few_clouds= (ImageView) findViewById(R.id.few_clouds);
        final ImageView light_rain= (ImageView) findViewById(R.id.light_rain);
        final ImageView clear_sky= (ImageView) findViewById(R.id.clear_sky);
        final ImageView wea= (ImageView) findViewById(R.id.wea);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setText("");
            }
        });


        assert b1 != null;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1="http://api.openweathermap.org/data/2.5/weather?q=",s3="&APPID=e63d92979457956aee7659b8eed733be";
                String s2=search.getText().toString();
                final String url=s1+s2+s3;
                clear_sky.animate().alpha(0).setDuration(1000);
                few_clouds.animate().alpha(0).setDuration(1000);
                light_rain.animate().alpha(0).setDuration(1000);
                JsonObjectRequest jor=new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonobject) {
                                Log.i("JSON", "jsonwa" + jsonobject);
                                String ans="",ans1="";
                                String tempMin="",tempMax="";
                                try {
                                    String temp=jsonobject.getString("main");
                                    Log.i("main","jsonwa"+temp);
                                    JSONObject jo=new JSONObject(temp);
                                    int t1=jo.getInt("temp_min");
                                    t1=t1-273;

                                    int t2=jo.getInt("temp_max");
                                    t2=t2-273;
                                    tempMin=tempMin+ t1;
                                    tempMax=tempMax+t2;

                                    ans1="Temp is in b/w "+tempMin+" to "+tempMax;
                                    tv.setText(ans1);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                                try {
                                    String weather=jsonobject.getString("weather");
                                    JSONArray ja= new JSONArray(weather);
                                    for(int i=0;i<ja.length();i++){
                                        JSONObject jo= ja.getJSONObject(i);
                                        ans=jo.getString("description");
                                    }
                                    result.setText(ans);

                                    if(ans.equals("broken clouds")||ans.equals("scattered clouds")||ans.equals("few clouds")||ans.equals("overcast clouds"))
                                    {
                                        wea.animate().alpha(0).setDuration(1000);
                                        few_clouds.animate().alpha(1).setDuration(1000);
                                    }
                                    else
                                        if(ans.equals("light rain")||ans.equals("moderate rain")||ans.equals("haze")){
                                           light_rain.animate().alpha(1).setDuration(1000);
                                            wea.animate().alpha(0).setDuration(1000);
                                        }
                                    else
                                        {
                                            clear_sky.animate().alpha(1).setDuration(1000);
                                            wea.animate().alpha(0).setDuration(1000);
                                        }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("error","muh me le lo");
                            }
                        }
                );
                mySingleton.getinstance(MainActivity.this).addtoreq(jor);
            }
        });

    }
}
