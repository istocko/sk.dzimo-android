package sk.dzimo.ui.priroda;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import sk.dzimo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrirodaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrirodaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PrirodaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrirodaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrirodaFragment newInstance(String param1, String param2) {
        PrirodaFragment fragment = new PrirodaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        ImageView iv = (ImageView) getActivity().findViewById(R.id.imageViewClef);
        if (iv != null) {
            iv.setVisibility(View.INVISIBLE);
        }

        iv = (ImageView) getActivity().findViewById(R.id.imageViewDJDZIMOLOGO);
        if (iv != null) {
            iv.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onResume() {
        ImageView iv = (ImageView) getActivity().findViewById(R.id.imageViewClef);
        if (iv != null) {
            iv.setVisibility(View.INVISIBLE);
        }

        iv = (ImageView) getActivity().findViewById(R.id.imageViewDJDZIMOLOGO);
        if (iv != null) {
            iv.setVisibility(View.INVISIBLE);
        }
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_priroda, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ImageView iv = (ImageView) getActivity().findViewById(R.id.imageViewClef);
        if (iv != null) {
            iv.setVisibility(View.VISIBLE);
        }

        iv = (ImageView) getActivity().findViewById(R.id.imageViewDJDZIMOLOGO);
        if (iv != null) {
            iv.setVisibility(View.VISIBLE);
        }

    }
}