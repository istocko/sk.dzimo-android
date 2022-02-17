package sk.dzimo.ui.patreon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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