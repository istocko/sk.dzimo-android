package sk.dzimo.ui.email;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class EmailViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EmailViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("dj.dzimo@gmail.com");
    }

    public LiveData<String> getText() {
        return mText;
    }
}