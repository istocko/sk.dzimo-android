package sk.dzimo.ui.playlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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