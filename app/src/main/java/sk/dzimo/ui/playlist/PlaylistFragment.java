package sk.dzimo.ui.playlist;

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