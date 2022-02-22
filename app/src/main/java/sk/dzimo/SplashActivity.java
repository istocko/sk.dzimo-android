package sk.dzimo;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

public class SplashActivity  extends Activity {
    private Handler mWaitHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView imageView = findViewById(R.id.splashScreenImageView);
        Glide.with(this).load(R.drawable.dzimo_at_work).into(imageView);

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
        }, 5000);  // Give a 5 seconds delay.

        TextView tvLoading = findViewById(R.id.textLoading);

        new CountDownTimer(5000, 500) {

            public void onTick(long millisUntilFinished) {
                tvLoading.setText("Načítavam DJ Džimo aplikáciu ... " + millisUntilFinished / 1000);
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
