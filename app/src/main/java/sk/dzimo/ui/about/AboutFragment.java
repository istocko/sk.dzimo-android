package sk.dzimo.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import sk.dzimo.R;
import sk.dzimo.databinding.FragmentAboutBinding;

public class AboutFragment extends Fragment {

    private AboutViewModel aboutViewModel;
    private FragmentAboutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aboutViewModel =
                new ViewModelProvider(this).get(AboutViewModel.class);

        binding = FragmentAboutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAbout;
        aboutViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        ImageView iv = (ImageView) getActivity().findViewById(R.id.imageViewClef);
        if (iv != null) {
            iv.setImageResource(R.drawable.about3);
        }

        return root;
    }

    @Override
    public void onResume() {
        ImageView iv = (ImageView) getActivity().findViewById(R.id.imageViewClef);
        if (iv != null) {
            iv.setImageResource(R.drawable.about3);
        }
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ImageView iv = (ImageView) getActivity().findViewById(R.id.imageViewClef);
        if (iv != null) {
            iv.setImageResource(R.drawable.clef_square);
        }
        binding = null;
    }
}