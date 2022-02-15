package sk.dzimo.ui.email;

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

import sk.dzimo.databinding.FragmentEmailBinding;

public class EmailFragment extends Fragment {

    private EmailViewModel emailViewModel;
    private FragmentEmailBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        emailViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(EmailViewModel.class);

        binding = FragmentEmailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textEmail;
        emailViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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