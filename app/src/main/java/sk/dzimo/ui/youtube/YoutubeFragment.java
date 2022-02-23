package sk.dzimo.ui.youtube;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import sk.dzimo.ActionOpenURLInChrome;
import sk.dzimo.databinding.FragmentYoutubeBinding;
import sk.dzimo.ui.email.EmailFragment;

public class YoutubeFragment extends Fragment {

    private YoutubeViewModel slideshowViewModel;
    private FragmentYoutubeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(YoutubeViewModel.class);

        binding = FragmentYoutubeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        ActionOpenURLInChrome a = new ActionOpenURLInChrome("https://www.youtube.com/channel/UCzQNBCfsiGL-Ax-LCcluI9Q", binding.urlYoutubeButton, YoutubeFragment.this);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}