package com.techiesatish.youtubeintegration;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Config;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
YouTubePlayerView youTubePlayerView;
String API_KEY="AIzaSyAoRYyYO2OwNU2MZhYHcltkbGoGoTqXR5g";
    private static final int RECOVERY_REQUEST = 1;
    String TAG="VideoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
       youTubePlayerView=(YouTubePlayerView)findViewById(R.id.youtubeview);
       youTubePlayerView.initialize(API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

       // Bundle bundle = getIntent().getExtras();
       // Log.e(TAG, "Video Id " + bundle.getString("videoId") );
      //  youTubePlayer.cueVideo(bundle.getString("videoId"));

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("videoId");
        Log.e(TAG,"Video" +message);
        youTubePlayer.cueVideo(message);
       // youTubePlayer.loadVideo(bundle.getString("url"));

       // youTubePlayer.play();

     /*   if(!b){
        Bundle bundle=getIntent().getExtras();
        String id=bundle.getString("videoId");
            Log.e(TAG,"running" +id);
           youTubePlayer.cueVideo(id);
        }*/

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {

            Toast.makeText(this, "Error Intializing Youtube Player", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubePlayerView;
    }


}
