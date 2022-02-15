package sk.dzimo.ui.patreon;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class PatreonViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PatreonViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("https://www.patreon.com/djdzimo");
    }

    public LiveData<String> getText() {
        return mText;
    }
}