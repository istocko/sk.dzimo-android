package sk.dzimo.ui.email;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import sk.dzimo.ActionOpenURLInChrome;
import sk.dzimo.databinding.FragmentEmailBinding;
import sk.dzimo.ui.playlist.PlaylistFragment;

public class EmailFragment extends Fragment {

    private EmailViewModel emailViewModel;
    private FragmentEmailBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        emailViewModel =
                new ViewModelProvider(this).get(EmailViewModel.class);

        binding = FragmentEmailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textEmail;
        emailViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        ActionOpenURLInChrome a = new ActionOpenURLInChrome("mailto:dj@dzimo.sk", binding.urlEmailButton, EmailFragment.this);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}