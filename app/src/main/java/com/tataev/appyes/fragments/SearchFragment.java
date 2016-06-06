package com.tataev.appyes.fragments;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.tataev.appyes.AppConfig;
import com.tataev.appyes.Defaults;
import com.tataev.appyes.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
SearchView  search_view_main;
    Fragment fragment;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_search, container, false);
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        search_view_main = (SearchView)rootView.findViewById(R.id.search_view_main);

        search_view_main.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        search_view_main.setIconifiedByDefault(false);
        search_view_main.setOnQueryTextListener(this);
        search_view_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_view_main.onActionViewExpanded();
            }
        });






        // Inflate the layout for this fragment
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public void onAttach(Context context) {
        super.onAttach((Activity) context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    public boolean onQueryTextSubmit(String query) {



        switch (query) {
            case "футболка":
                fragment = new ProductsList(AppConfig.url);
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case "поло":
                fragment = new ProductsList(AppConfig.poloUrl);
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case "майки" :
                fragment = new ProductsList(AppConfig.vestUrl);
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case "майка" :
                fragment = new ProductsList(AppConfig.vestUrl);
                Defaults.replaceFragment(fragment, getActivity());
                break;

        }

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
    public void onDestroyView() {
        Fragment parentFragment = getParentFragment();
        FragmentManager manager;
        if (parentFragment != null) {
            // If parent is another fragment, then this fragment is nested
            manager = parentFragment.getChildFragmentManager();
        } else {
            // This fragment is placed into activity
            manager = getActivity().getSupportFragmentManager();
        }
        manager.beginTransaction().remove(this).commitAllowingStateLoss();
        super.onDestroyView();
    }


}
