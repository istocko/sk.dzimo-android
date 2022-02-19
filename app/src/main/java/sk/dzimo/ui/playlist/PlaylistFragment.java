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
import android.widget.ImageButton;
import android.widget.TextView;

import sk.dzimo.ActionOpenURLInChrome;
import sk.dzimo.databinding.FragmentPlaylistBinding;
import sk.dzimo.ui.home.HomeFragment;

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

        final ImageButton ib = binding.urlPLaylistButton;
        String url = "https://www.dzimo.sk/playlist.html";
        ActionOpenURLInChrome a = new ActionOpenURLInChrome(url, ib, PlaylistFragment.this);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}