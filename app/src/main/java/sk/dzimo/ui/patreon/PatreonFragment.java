package sk.dzimo.ui.patreon;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import sk.dzimo.ActionOpenURLInChrome;
import sk.dzimo.databinding.FragmentPatreonBinding;
import sk.dzimo.ui.youtube.YoutubeFragment;

public class PatreonFragment extends Fragment {

    private PatreonViewModel patreonViewModel;
    private FragmentPatreonBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        patreonViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(PatreonViewModel.class);

        binding = FragmentPatreonBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPatreon;
        patreonViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        ActionOpenURLInChrome a = new ActionOpenURLInChrome( "https://www.patreon.com/djdzimo", binding.urlPatreonButton, PatreonFragment.this);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}