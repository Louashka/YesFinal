package com.tataev.appyes.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.tataev.appyes.Defaults;
import com.tataev.appyes.MainActivity;
import com.tataev.appyes.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Favourites.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Favourites#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Favourites extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView menu_favor_tab;
    private TextView nearby_favor_tab;
    private TextView novelty_favor_tab;
    private TextView favor_favor_tab;
    private TextView reservation_favor_tab;
    private TextView categories_favor_tab;
    private Fragment fragment;
    private SearchView search_view_main;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Favourites.
     */
    // TODO: Rename and change types and number of parameters
    public static Favourites newInstance(String param1, String param2) {
        Favourites fragment = new Favourites();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Favourites() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Избранное");

      //  Defaults.setSearchViewStyle(R.id.searchViewFavourites, rootView, getActivity());

        //Initialize tab menu icons
        menu_favor_tab = (TextView)rootView.findViewById(R.id.menu_favor_tab);
        nearby_favor_tab = (TextView)rootView.findViewById(R.id.nearby_favor_tab);
        novelty_favor_tab = (TextView)rootView.findViewById(R.id.novelty_favor_tab);
        favor_favor_tab = (TextView)rootView.findViewById(R.id.favor_favor_tab);
        reservation_favor_tab = (TextView)rootView.findViewById(R.id.reservation_favor_tab);
        categories_favor_tab = (TextView)rootView.findViewById(R.id.categories_favor_tab);
     //   search_view_main = (SearchView)rootView.findViewById(R.id.searchViewFavourites);

        // Set OnClickListener to menu icons
        menu_favor_tab.setOnClickListener(this);
        nearby_favor_tab.setOnClickListener(this);
        novelty_favor_tab.setOnClickListener(this);
        favor_favor_tab.setOnClickListener(this);
        reservation_favor_tab.setOnClickListener(this);
        categories_favor_tab.setOnClickListener(this);
     //   search_view_main.setOnClickListener(this);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_favor_tab:
                fragment = new MenuItems();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.nearby_favor_tab:
                fragment = new Nearby();
                Defaults.replaceFragment(fragment, getActivity());
//                Uri gmmIntentUri =  Uri.parse("geo:0,0?q=a+Grozniy");
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//                    startActivity(mapIntent);
//                }
                break;
            case R.id.novelty_favor_tab:
                fragment = new Novelty();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.favor_favor_tab:
                fragment = new Favourites();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.reservation_favor_tab:
                fragment = new Reservation();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.categories_favor_tab:
                fragment = new Categories();
                Defaults.replaceFragment(fragment, getActivity());
                break;
         //   case R.id.searchViewFavourites:
            //    search_view_main.onActionViewExpanded();
            //    break;
            default:
                break;
        }
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

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
        public void onFragmentInteraction(Uri uri);
    }

}
