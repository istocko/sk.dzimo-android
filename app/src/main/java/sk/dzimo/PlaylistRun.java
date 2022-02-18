package sk.dzimo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PlaylistRun extends AppCompatActivity {

    private WebView play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_run);
        play = (WebView) findViewById(R.id.webPlaylist);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                play.setWebViewClient(new WebViewClient());
                play.getSettings().setJavaScriptEnabled(true);
                play.getSettings().setAllowContentAccess(true);
                play.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                play.getSettings().setMediaPlaybackRequiresUserGesture(false);
                play.setWebContentsDebuggingEnabled(true);
                play.setLayerType(View.LAYER_TYPE_HARDWARE, null);

                play.loadUrl("https://www.dzimo.sk/playlist.html");

            }
        });



    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        play.destroy();
        finish();
    }


}