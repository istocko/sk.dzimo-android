package sk.dzimo;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

public class SplashActivity  extends Activity {
    private Handler mWaitHandler = new Handler();
    int lastPlaybackCount = SoundCloudPlaylist.playbackCount;
    boolean newPlaybackCountLoaded = false;
    TickerView tickerView;
    TextView topMixName, topMixLabel, latestMixName, latestMixLabel;

    public void initTickerView(){
        newPlaybackCountLoaded = true;

    }



    public void setVisibleMixStatistics(boolean visible) {
        topMixLabel.setVisibility(visible?View.VISIBLE:View.INVISIBLE);
        topMixName.setVisibility(visible?View.VISIBLE:View.INVISIBLE);
        latestMixLabel.setVisibility(visible?View.VISIBLE:View.INVISIBLE);
        latestMixName.setVisibility(visible?View.VISIBLE:View.INVISIBLE);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView imageView = findViewById(R.id.splashScreenImageView);
        Glide.with(this).load(R.drawable.dzimo_at_work).into(imageView);

        SoundCloudPlaylist.activity = this;
        SoundCloudPlaylist.loadPlaylistFromSC();


        tickerView = findViewById(R.id.newPlaysView);
        tickerView.setCharacterLists(TickerUtils.provideNumberList());
        tickerView.setAnimationDuration(2000);
        tickerView.setAnimationInterpolator(new OvershootInterpolator());
        tickerView.setGravity(Gravity.START);
        tickerView.setPreferredScrollingDirection(TickerView.ScrollingDirection.ANY);
       //tickerView.setTextColor();
        tickerView.setVisibility(View.INVISIBLE);
        tickerView.setText("0");

        topMixLabel = (TextView) findViewById(R.id.topMixTextView);
        topMixName = (TextView) findViewById(R.id.topMixNameTextView);
        latestMixLabel = (TextView) findViewById(R.id.latestMixTextView);
        latestMixName = (TextView) findViewById(R.id.latestMixNameTextView);

        setVisibleMixStatistics(false);




        mWaitHandler.postDelayed(new Runnable() {

            @Override
            public void run() {

                //The following code will execute after the 5 seconds.

                try {

                    //Go to next page i.e, start the next activity.
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    //Let's Finish Splash Activity since we don't want to show this when user press back button.
                    finish();
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }, 15000);  // Give a 5 seconds delay.

        TextView tvLoading = findViewById(R.id.textLoading);

        new CountDownTimer(15000, 500) {

            public void onTick(long millisUntilFinished) {
                // String loadStr = "Načítavam DJ Džimo aplikáciu ... " + millisUntilFinished / 1000;
                String loadStr = "Načítavam štatistiky Soundcloud";
                if (newPlaybackCountLoaded) {
                    //newPlaybackCountLoaded = false;
                    // SoundCloudPlaylist.s("latestPlays: "+SoundCloudPlaylist.latestPlaybackCount);
                    int newest = SoundCloudPlaylist.latestPlaybackCount - lastPlaybackCount;
                   //newest = 0;
                    if ( newest > 0) {
                        if (tickerView.getVisibility() == View.INVISIBLE){
                            tickerView.setVisibility(View.VISIBLE);
                            tickerView.setText(""+newest);
                        }

                        topMixName.setText(SoundCloudPlaylist.topMix2023);
                        latestMixName.setText(SoundCloudPlaylist.latestMix);
                        setVisibleMixStatistics(true);

                        //loadStr += " new "+(SoundCloudPlaylist.latestPlaybackCount - lastPlaybackCount)+" plays ";
                        loadStr = "Celkovo prehratých mixov";

                    } else {
                       loadStr = "Žiadne nové prehratia mixov ";
                    }
                    if (millisUntilFinished < 5000) {
                       //tickerView.setVisibility(View.INVISIBLE);
                        loadStr = "Načítavam DJ Džimo aplikáciu ... " + millisUntilFinished / 1000;
                    }

                    tvLoading.setText(loadStr);
                } else {
                    tvLoading.setText(loadStr);
                }
            }

            public void onFinish() {
                tvLoading.setText("Hotovo!");
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //Remove all the callbacks otherwise navigation will execute even after activity is killed or closed.
        mWaitHandler.removeCallbacksAndMessages(null);
    }
}
