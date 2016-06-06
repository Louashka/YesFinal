package com.tataev.appyes.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.tataev.appyes.Defaults;
import com.tataev.appyes.MainActivity;
import com.tataev.appyes.R;
import com.tataev.appyes.adapters.RatingAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Rating.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Rating#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Rating extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView menu_rating_tab;
    private TextView nearby_rating_tab;
    private TextView novelty_rating_tab;
    private TextView favor_rating_tab;
    private TextView reservation_rating_tab;
    private TextView categories_rating_tab;
    private Fragment fragment;
    private ListView listViewRating;
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
     * @return A new instance of fragment Rating.
     */
    // TODO: Rename and change types and number of parameters
    public static Rating newInstance(String param1, String param2) {
        Rating fragment = new Rating();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Rating() {
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
        View rootView = inflater.inflate(R.layout.fragment_rating, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Рейтинг");

        Defaults.setSearchViewStyle(R.id.searchViewRating, rootView, getActivity());

        //Initialize tab menu icons
        menu_rating_tab = (TextView)rootView.findViewById(R.id.menu_rating_tab);
        nearby_rating_tab = (TextView)rootView.findViewById(R.id.nearby_rating_tab);
        novelty_rating_tab = (TextView)rootView.findViewById(R.id.novelty_rating_tab);
        favor_rating_tab = (TextView)rootView.findViewById(R.id.favor_rating_tab);
        reservation_rating_tab = (TextView)rootView.findViewById(R.id.reservation_rating_tab);
        categories_rating_tab = (TextView)rootView.findViewById(R.id.categories_rating_tab);
        listViewRating = (ListView)rootView.findViewById(R.id.listViewRating);
        search_view_main = (SearchView)rootView.findViewById(R.id.searchViewRating);

        // Set OnClickListener to menu icons
        menu_rating_tab.setOnClickListener(this);
        nearby_rating_tab.setOnClickListener(this);
        novelty_rating_tab.setOnClickListener(this);
        favor_rating_tab.setOnClickListener(this);
        reservation_rating_tab.setOnClickListener(this);
        categories_rating_tab.setOnClickListener(this);
        search_view_main.setOnClickListener(this);

        listViewRating.setAdapter(new RatingAdapter(getActivity()));
        listViewRating.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment;
                switch (position){
                    case 0:
                        fragment = new ShopsRating();
                        Defaults.replaceFragment(fragment, getActivity());
                        break;
                    case 1:
                        fragment = new BuyersRating();
                        Defaults.replaceFragment(fragment, getActivity());
                        break;
                    case 2:
                        fragment = new ProductsRating();
                        Defaults.replaceFragment(fragment, getActivity());
                        break;
                    case 3:
                        fragment = new ServicesRating();
                        Defaults.replaceFragment(fragment, getActivity());
                        break;
                    case 4:
                        fragment = new CategoriesRating();
                        Defaults.replaceFragment(fragment, getActivity());
                        break;
                    case 5:
                        fragment = new ObjectsRating();
                        Defaults.replaceFragment(fragment, getActivity());
                        break;
                    default:
                        break;
                }
            }
        });

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
            case R.id.menu_rating_tab:
                fragment = new MenuItems();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.nearby_rating_tab:
                fragment = new Nearby();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.novelty_rating_tab:
                fragment = new Novelty();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.favor_rating_tab:
                fragment = new Favourites();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.reservation_rating_tab:
                fragment = new Reservation();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.categories_rating_tab:
                fragment = new Categories();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.searchViewRating:
                search_view_main.onActionViewExpanded();
                break;
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
