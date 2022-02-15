package sk.dzimo.ui.patreon;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import sk.dzimo.databinding.FragmentPatreonBinding;

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
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}