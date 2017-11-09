package com.techiesatish.youtubeintegration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChannelActivity extends AppCompatActivity {
    ListView lvVideo;
    ArrayList<VideoDetails> videoDetailsArrayList;
    CustomListAdapter customListAdapter;
    String searchName;
    String TAG="ChannelActivity";
    String URL="https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UC9CYT9gSNLevX5ey2_6CK0Q&maxResults=25&key=AIzaSyAoRYyYO2OwNU2MZhYHcltkbGoGoTqXR5g";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        lvVideo=(ListView)findViewById(R.id.videoList);
        videoDetailsArrayList=new ArrayList<>();
        customListAdapter=new CustomListAdapter(ChannelActivity.this,videoDetailsArrayList);
                showVideo();



    }

    private void showVideo() {

     //   String URL="https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=10&q="+searchName+"&key=AIzaSyAoRYyYO2OwNU2MZhYHcltkbGoGoTqXR5g";
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("items");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonVideoId=jsonObject1.getJSONObject("id");
                        JSONObject jsonsnippet= jsonObject1.getJSONObject("snippet");
                        JSONObject jsonObjectdefault = jsonsnippet.getJSONObject("thumbnails").getJSONObject("medium");
                        VideoDetails videoDetails=new VideoDetails();

                        String videoid=jsonVideoId.getString("videoId");

                        Log.e(TAG," New Video Id" +videoid);
                        videoDetails.setURL(jsonObjectdefault.getString("url"));
                        videoDetails.setVideoName(jsonsnippet.getString("title"));
                        videoDetails.setVideoDesc(jsonsnippet.getString("description"));
                           videoDetails.setVideoId(videoid);

                        videoDetailsArrayList.add(videoDetails);

//                         videoDetailsArrayList.clear();
                    }
                    lvVideo.setAdapter(customListAdapter);

                    customListAdapter.notifyDataSetChanged();
                    // lvVideo.setAdapter(null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }
}
