package sk.dzimo.ui.youtube;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class YoutubeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public YoutubeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("https://www.youtube.com/channel/UCzQNBCfsiGL-Ax-LCcluI9Q");
    }

    public LiveData<String> getText() {
        return mText;
    }
}