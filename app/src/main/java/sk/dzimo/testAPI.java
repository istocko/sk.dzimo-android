package sk.dzimo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class testAPI extends AppCompatActivity {

    String dataResponseHTML = "";
    String dataResponseJSON = "";
    String dataPlaybackCounter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api);

        EditText et = findViewById(R.id.editTextTextMultiLine);
        et.setText("Press load from SC ... ");

    }

    public void saveResponseClick(View view) {
        s("save response to file");

        Context context = getApplicationContext();
        File pathDownloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File app_dir = new File(pathDownloads+"/sk.dzimo/");
        if (app_dir.exists()) {
            ss(app_dir.toString());
        } else {
            ss("storage not exist "+app_dir.toString()+"! creating ... ");
            app_dir.mkdir();
        }
        try
        {
            File outFile = new File(app_dir, "responseHTML.txt");
            Files.asCharSink(outFile, Charsets.UTF_8).write(dataResponseHTML);


            outFile = new File(app_dir, "responseJSON.txt");
            Files.asCharSink(outFile, Charsets.UTF_8).write(dataResponseJSON);

            outFile = new File(app_dir, "responseCounter.txt");
            Files.asCharSink(outFile, Charsets.UTF_8).write(dataPlaybackCounter);


        }
        catch (Exception exc) {
            ss(exc.getMessage());
        }

    }

    public void click(View view) {
        s("click");
        try {
            //Document doc = Jsoup.connect("https://soundcloud.com/imrich-stolar/sets/dj-d-imo-2023").get();
            //s(doc.title());
            RequestQueue mRequestQueue;
            mRequestQueue = Volley.newRequestQueue(this);
            StringRequest mStringRequest;
            String url = "https://soundcloud.com/imrich-stolar/sets/dj-d-imo-2023";
            mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    s("get response: "+response.toString().length());
                    dataResponseHTML = response.toString();
                    try {
                        Document doc = Jsoup.parse(response.toString());
                        EditText et = findViewById(R.id.editTextTextMultiLine);
                        String jsonData = doc.getElementById("__NEXT_DATA__").data();
                        dataResponseJSON = jsonData;
                        JSONObject tracks = new JSONObject(jsonData)
                                .getJSONObject("props")
                                .getJSONObject("pageProps")
                                .getJSONObject("initialStoreState")
                                .getJSONObject("entities")
                                .getJSONObject("tracks")
                                ;
                        Iterator<String> keys = tracks.keys();
                        int playbackCountSum = 0;
                        String playbackCountTxt = "Playback counts:\n";
                        //int tracksCount = 0;
                        while (keys.hasNext()) {
                            //tracksCount++;
                            JSONObject track = tracks.getJSONObject(keys.next());
                            int plays = track.getJSONObject("data").getInt("playback_count");
                            playbackCountSum += plays;
                            playbackCountTxt += track.getJSONObject("data").getString("title") + ": plays: "+plays+"\n";
                        }
                        dataPlaybackCounter = ""+playbackCountSum;
                        playbackCountTxt += "\n\n";

                        String totalPlays = "Total plays: "+playbackCountSum+"\n";

                        String trackCounts = "Tracks: "+tracks.length()+"\n";

                        et.setText(totalPlays+trackCounts+playbackCountTxt + tracks.toString(4));
                        //s(json.getString);

                        Button saveResponseBtn = (Button) findViewById(R.id.button2);
                        saveResponseBtn.setEnabled(true);
                        //Elements newsHeadlines = doc.getElementsByAttributeValue("itemprop","numTracks");
                        //for (Element headline : newsHeadlines) {
                        //   log("%s\n\t%s",
                        //            s(headline.attr("content"));
                        //}
                    } catch (Exception exc) {
                        ss("exc: "+exc.getMessage());
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ss(error.getMessage());
                }
            });
            mRequestQueue.add(mStringRequest);


        } catch (Exception exc) {
            ss(exc.getMessage());
        }

    }

    public void s(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void ss(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}