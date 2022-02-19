package sk.dzimo;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;

public class ActionOpenURLInChrome {

    public ActionOpenURLInChrome(final String url, ImageButton ib, Fragment f) {
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                f.startActivity(intent);
            }
        });
    }


}


/*
class OpenUrlInChromeData {
    public Map<String, OpenUrlInChromeData> hm = new HashMap<String, OpenUrlInChromeData>();
    String url = "";
    Fragment f;
    ImageButton ib;

    public OpenUrlInChromeData() {
        //hm.put("home", )
    }

    public OpenUrlInChromeData(String url, Fragment f, ImageButton ib) {
        this.url = url;
        this.f = f;
        this.ib = ib;
    }
}
*/