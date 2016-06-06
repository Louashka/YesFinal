package com.tataev.appyes.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.tataev.appyes.AppConfig;
import com.tataev.appyes.Defaults;
import com.tataev.appyes.MainActivity;
import com.tataev.appyes.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuItems.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuItems#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuItems extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Fragment fragment;
    private FragmentTransaction fTrans;
    private FragmentManager fragmentManager;
    private TextView reviews_text;
    private TextView novelty_text;
    private TextView profile_text;
    private TextView users_text;
    private TextView categories_text;
    private TextView rating_text;
    private TextView discuss_text;
    private TextView reservation_text;
    private TextView abProg_text;
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
     * @return A new instance of fragment MenuItems.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuItems newInstance(String param1, String param2) {
        MenuItems fragment = new MenuItems();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MenuItems() {
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
        View rootView = inflater.inflate(R.layout.fragment_menu_items, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("");

        Defaults.setSearchViewStyle(R.id.search_view_main, rootView, getActivity());

        // Initialize menu buttons (TextViews)
        reviews_text = (TextView)rootView.findViewById(R.id.reviews_text);
        novelty_text = (TextView)rootView.findViewById(R.id.novelty_text);
        profile_text = (TextView)rootView.findViewById(R.id.profile_text);
        users_text = (TextView)rootView.findViewById(R.id.users_text);
        categories_text = (TextView)rootView.findViewById(R.id.categories_text);
        rating_text = (TextView)rootView.findViewById(R.id.rating_text);
        discuss_text = (TextView)rootView.findViewById(R.id.discuss_text);
        reservation_text = (TextView)rootView.findViewById(R.id.reservation_text);
        abProg_text = (TextView)rootView.findViewById(R.id.abProg_text);
/*
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        search_view_main = (SearchView)rootView.findViewById(R.id.search_view_main);

        search_view_main.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        search_view_main.setIconifiedByDefault(false);
        search_view_main.setOnQueryTextListener(this);
*/
        // Set OnClickListener to menu TextViews
        reviews_text.setOnClickListener(this);
        novelty_text.setOnClickListener(this);
        profile_text.setOnClickListener(this);
        users_text.setOnClickListener(this);
        categories_text.setOnClickListener(this);
        rating_text.setOnClickListener(this);
        discuss_text.setOnClickListener(this);
        reservation_text.setOnClickListener(this);
        abProg_text.setOnClickListener(this);


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
        // Replace fragment matching to specific TextView
        switch (v.getId()){
            case R.id.reviews_text:
                fragment = new Reviews();
                replaceFragment(fragment);
                break;
            case R.id.novelty_text:
                fragment = new Novelty();
                replaceFragment(fragment);
                break;
            case R.id.profile_text:
                fragment = new Profile();
                replaceFragment(fragment);
                break;
            case R.id.users_text:
                fragment = new Users();
                replaceFragment(fragment);
                break;
            case R.id.categories_text:
                fragment = new Categories();
                replaceFragment(fragment);
                break;
            case R.id.rating_text:
                fragment = new Rating();
                replaceFragment(fragment);
                break;
            case R.id.discuss_text:
                fragment = new Discussions();
                replaceFragment(fragment);
                break;
            case R.id.reservation_text:
                fragment = new Reservation();
                replaceFragment(fragment);
                break;
            case R.id.abProg_text:
                fragment = new AboutProgram();
                replaceFragment(fragment);
                break;
            case R.id.search_view_main:
                search_view_main.onActionViewExpanded();
                break;
            default:
                break;
        }
    }

    // Open fragment when clicking menu TextView
    public void replaceFragment(Fragment frag){
        if (frag != null) {
            fragmentManager = getActivity().getSupportFragmentManager();
            fTrans = fragmentManager.beginTransaction();
            // Add Fragment MenuItems to content_frame
            fTrans.replace(R.id.content_frame, frag);
            fTrans.addToBackStack(null);
            fTrans.commit();
        }
    }

    @Override
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
//
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
