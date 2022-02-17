package sk.dzimo.ui.facebook;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FacebookViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FacebookViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("https://sk-sk.facebook.com/djdzimo/");
    }

    public LiveData<String> getText() {
        return mText;
    }
}