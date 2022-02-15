package sk.dzimo.ui.playlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class PlaylistViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PlaylistViewModel() {
        mText = new MutableLiveData<>();

        mText.setValue("https://www.dzimo.sk/playlist.html");
    }

    public LiveData<String> getText() {
        return mText;
    }
}