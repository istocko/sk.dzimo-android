package sk.dzimo.ui.facebook;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

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