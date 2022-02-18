package sk.dzimo.ui.playlist;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import sk.dzimo.PlaylistRun;
import sk.dzimo.R;
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

        //View rootView = inflater.inflate(R.layout.content_main,container,false);


        Button clickButton = (Button) root.findViewById(R.id.prehrat);

        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("TAG", "click AT "+clickButton);

                Intent intent = new Intent(root.getContext(), PlaylistRun.class);
                startActivity(intent);


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