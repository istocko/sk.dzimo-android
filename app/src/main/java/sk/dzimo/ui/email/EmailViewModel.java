package sk.dzimo.ui.email;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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