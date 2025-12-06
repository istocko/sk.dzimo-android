package sk.dzimo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class testAPI extends AppCompatActivity {

    EditText etLog;
    // ZMENA: Správna URL pre rok 2024/2025
    String targetUrl = "https://soundcloud.com/imrich-stolar/sets/dj-d-imo-2023";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api);

        // Nájdi element na výpis logov (v xml sa volá editTextTextMultiLine)
        etLog = findViewById(R.id.editTextTextMultiLine);
        etLog.setText("Pripravený na test URL:\n" + targetUrl + "\n\nStlač tlačidlo...");
    }

    public void click(View view) {
        logToScreen("Začínám sťahovanie zo SoundCloudu...");

        RequestQueue queue = Volley.newRequestQueue(this);

        // Používame User-Agent, aby sme vyzerali ako prehliadač, nie ako bot
        StringRequest stringRequest = new StringRequest(Request.Method.GET, targetUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        analyzeResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        logToScreen("CHYBA SIETE: " + error.getMessage());
                    }
                });

        queue.add(stringRequest);
    }

    private void analyzeResponse(String html) {
        StringBuilder report = new StringBuilder();
        report.append("Dáta stiahnuté (").append(html.length()).append(" znakov).\n-----------------\n");

        try {
            Document doc = Jsoup.parse(html);

            // 1. TEST: Hľadáme starý __NEXT_DATA__
            Element nextData = doc.getElementById("__NEXT_DATA__");

            if (nextData != null) {
                report.append("✅ NÁJDENÉ: Element '__NEXT_DATA__' existuje!\n");
                report.append("Dĺžka JSONu: ").append(nextData.data().length()).append("\n");
                // Ak toto existuje, problém je len v ceste k dátam (napr. premenovali 'tracks')
            } else {
                report.append("❌ POZOR: Element '__NEXT_DATA__' sa NENAŠIEL!\n");
            }

            // 2. TEST: Hľadáme nový Hydration systém
            report.append("Hľadám 'hydration' skripty...\n");
            Elements scripts = doc.getElementsByTag("script");
            boolean foundHydration = false;

            for (Element script : scripts) {
                if (script.data().contains("__SC_HYDRATION_DATA__")) {
                    foundHydration = true;
                    report.append("⚠️ NÁJDENÉ: '__SC_HYDRATION_DATA__'\n");
                    report.append("SoundCloud prešiel na nový systém!\n");
                }
            }

            if (!foundHydration && nextData == null) {
                report.append("💀 KRITICKÉ: Žiadne dáta. Možno blokujú prístup (Captcha/Cloudflare).\n");
            }

            logToScreen(report.toString());

        } catch (Exception e) {
            logToScreen("Chyba pri analýze: " + e.getMessage());
        }
    }

    private void logToScreen(String msg) {
        String current = etLog.getText().toString();
        etLog.setText(msg + "\n\n" + current);
        Log.d("DZIMO_TEST", msg); // Výpis aj do Logcatu
    }

    // Prázdna metóda pre druhé tlačidlo, aby nepadala appka
    public void saveResponseClick(View view) {
        Toast.makeText(this, "Výsledok je v okne vyššie", Toast.LENGTH_SHORT).show();
    }
}