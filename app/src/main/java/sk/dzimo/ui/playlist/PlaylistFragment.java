package sk.dzimo.ui.playlist;

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

import sk.dzimo.databinding.FragmentPlaylistBinding;

public class PlaylistFragment extends Fragment {

    private PlaylistViewModel playlistViewModel;
    private FragmentPlaylistBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        playlistViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(PlaylistViewModel.class);

        binding = FragmentPlaylistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPlaylist;
        playlistViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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