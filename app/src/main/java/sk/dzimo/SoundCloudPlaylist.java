package sk.dzimo;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Iterator;

public class SoundCloudPlaylist {
    public static int playbackCount = 0;
    public static String urlPlaylist = "https://soundcloud.com/imrich-stolar/sets/dj-d-imo-2023";
    public static Activity  activity;
    public static String dataResponseJSON = "";
    public static int latestPlaybackCount = playbackCount;
    public static String topMix2023 = "";
    public static String latestMix = "";

    public static void loadPlaylistFromSC(){
        try {
            RequestQueue mRequestQueue;
            mRequestQueue = Volley.newRequestQueue(activity);
            StringRequest mStringRequest;
            mStringRequest = new StringRequest(Request.Method.GET, urlPlaylist, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        Document doc = Jsoup.parse(response.toString());
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
                        //ss("tracksCount: "+tracks.length());
                        int playbackCountSum = 0;
                        //int tracksCount = 0;
                        int topPlays2023 = 0 ;
                        while (keys.hasNext()) {
                            //tracksCount++;
                            JSONObject track = tracks.getJSONObject(keys.next());
                            try {
                                int plays = track.getJSONObject("data").getInt("playback_count");
                                boolean isCreated2023 = track.getJSONObject("data").getString("created_at").startsWith("2023-");
                                if (isCreated2023 && plays > topPlays2023){
                                    topPlays2023 = plays;
                                    topMix2023 = track.getJSONObject("data").getString("title");
                                }
                                if (latestMix.equals("")) {
                                    latestMix = track.getJSONObject("data").getString("title");
                                }
                                playbackCountSum += plays;
                            }
                            catch (Exception exc) {

                            }
                        }

                        latestPlaybackCount = playbackCountSum;

                        if (activity != null && activity instanceof SplashActivity) {
                            SplashActivity sa = (SplashActivity) activity;
                            sa.initTickerView();

                        }

                    } catch (Exception exc) {
                        ss(exc.getMessage());
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError exc) {
                    ss(exc.getMessage());
                }
            });
            //s("SC request starting");
            mRequestQueue.add(mStringRequest);


        } catch (Exception exc) {
            ss(exc.getMessage());
        }

    }

    public static void s(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public static void ss(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

}
