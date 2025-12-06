package sk.dzimo;

import android.app.Activity;
import android.util.Log;
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
import org.jsoup.nodes.Element;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SoundCloudPlaylist {
    public static int playbackCount = 0;
    // URL pre rok 2024/2025
    public static String urlPlaylist = "https://soundcloud.com/imrich-stolar/sets/dj-d-imo-2024";
    public static Activity activity;
    public static int latestPlaybackCount = playbackCount;

    // Inicializácia - už nikdy žiadne "null"!
    public static String topMix2025 = "Načítavam dáta...";
    public static String latestMix = "Čakám na SoundCloud...";

    public static void loadPlaylistFromSC() {
        try {
            RequestQueue mRequestQueue = Volley.newRequestQueue(activity);

            StringRequest mStringRequest = new StringRequest(Request.Method.GET, urlPlaylist, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Document doc = Jsoup.parse(response);

                        // 1. FÁZA: Hľadáme JSON dáta
                        Element scriptElement = doc.getElementById("__NEXT_DATA__");
                        if (scriptElement == null) {
                            Log.e("SC_FIX", "NEXT_DATA chýba, skúšam Fallback cez Regex");
                            parseViaRegex(response); // Záchranný plán
                            return;
                        }

                        String jsonData = scriptElement.data();
                        JSONObject root = new JSONObject(jsonData);
                        JSONObject pageProps = root.getJSONObject("props").getJSONObject("pageProps");

                        JSONObject tracks = null;

                        // 2. FÁZA: Hľadáme 'tracks' na rôznych miestach (Bypass)
                        try {
                            // Pokus A: Stará cesta
                            tracks = pageProps.getJSONObject("initialStoreState")
                                    .getJSONObject("entities")
                                    .getJSONObject("tracks");
                        } catch (Exception e1) {
                            try {
                                // Pokus B: Nová cesta (Hydratable)
                                tracks = pageProps.getJSONObject("hydratableStore")
                                        .getJSONObject("entities")
                                        .getJSONObject("tracks");
                            } catch (Exception e2) {
                                Log.e("SC_FIX", "Ani cesta A, ani B nevyšla.");
                            }
                        }

                        if (tracks != null) {
                            processTracksJSON(tracks);
                        } else {
                            // Ak JSON zlyhal úplne, skúsime vytiahnuť názvy priamo z HTML textu
                            parseViaRegex(response);
                        }

                    } catch (Exception exc) {
                        Log.e("SC_ERROR", "Chyba parsovania: " + exc.getMessage());
                        setFallbackData("Chyba spracovania", "Skúsim neskôr");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError exc) {
                    Log.e("VOLLEY_ERROR", "Chyba siete: " + exc.getMessage());
                    setFallbackData("Offline režim", "Skontroluj internet");
                }
            });

            mRequestQueue.add(mStringRequest);

        } catch (Exception exc) {
            ss("Critical error: " + exc.getMessage());
        }
    }

    // Spracovanie čistého JSONu
    private static void processTracksJSON(JSONObject tracks) {
        try {
            Iterator<String> keys = tracks.keys();
            int playbackCountSum = 0;
            int topPlays = 0;
            String tempTopMix = "Žiadne dáta";
            String tempLatestMix = "";

            while (keys.hasNext()) {
                JSONObject track = tracks.getJSONObject(keys.next());
                try {
                    JSONObject data = track.getJSONObject("data"); // Niekedy je to priamo, niekedy v 'data'
                    if (data == null) data = track; // Poistka

                    int plays = data.optInt("playback_count", 0);
                    String title = data.optString("title", "Neznámy mix");
                    String createdAt = data.optString("created_at", "");

                    // Filter pre nové mixy (koniec 2023, 2024, 2025)
                    boolean isNew = createdAt.contains("2024") || createdAt.contains("2025") || createdAt.contains("2023");

                    if (isNew && plays > topPlays) {
                        topPlays = plays;
                        tempTopMix = title;
                    }

                    // Prvý nájdený považujeme za latest (SoundCloud zvyčajne radí od najnovšieho)
                    if (tempLatestMix.isEmpty()) {
                        tempLatestMix = title;
                    }

                    playbackCountSum += plays;
                } catch (Exception e) {
                    Log.e("SC_PARSE", "Chyba pri tracku: " + e.getMessage());
                }
            }

            // Úspech!
            updateGlobalData(playbackCountSum, tempTopMix, tempLatestMix);

        } catch (Exception e) {
            Log.e("SC_PROC", "Chyba processingu: " + e.getMessage());
        }
    }

    // Záchranný plán: "Hrubá sila" cez Regex (ak zlyhá JSON)
    private static void parseViaRegex(String html) {
        try {
            // Hľadáme vzor: "title":"NIEČO","created_at"
            Pattern pattern = Pattern.compile("\"title\":\"(.*?)\",\"created_at\"");
            Matcher matcher = pattern.matcher(html);

            String firstFound = "";
            if (matcher.find()) {
                firstFound = matcher.group(1); // Prvý nájdený mix
            }

            if (!firstFound.isEmpty()) {
                updateGlobalData(0, firstFound, firstFound); // Nevieme plays, ale máme aspoň názov
            } else {
                setFallbackData("SoundCloud zmenil web", "Skontroluj appku");
            }
        } catch (Exception e) {
            setFallbackData("Chyba", "Regex fail");
        }
    }

    private static void updateGlobalData(int plays, String top, String latest) {
        latestPlaybackCount = plays;
        topMix2025 = top;
        latestMix = latest;
        updateUI();
    }

    private static void updateUI() {
        if (activity != null && activity instanceof SplashActivity) {
            final SplashActivity sa = (SplashActivity) activity;
            sa.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    sa.initTickerView();
                }
            });
        }
    }

    private static void setFallbackData(String top, String latest) {
        topMix2025 = top;
        latestMix = latest;
        latestPlaybackCount = 0;
        updateUI();
    }

    public static void ss(String message) {
        if (activity != null) {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
        }
    }
}