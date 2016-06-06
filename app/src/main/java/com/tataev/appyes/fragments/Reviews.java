package com.tataev.appyes.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.tataev.appyes.Defaults;
import com.tataev.appyes.MainActivity;
import com.tataev.appyes.R;
import com.tataev.appyes.ReviewsObject;
import com.tataev.appyes.adapters.ReviewsAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Reviews.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Reviews#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reviews extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView menu_tab;
    private TextView nearby_tab;
    private TextView novelty_tab;
    private TextView favor_tab;
    private TextView reservation_tab;
    private TextView categories_tab;
    private ListView listViewReviews;
    private ReviewsObject draft = new ReviewsObject();
    private ArrayList<String> objectFeaturesDraft = new ArrayList<String>();
    private ArrayList<String> objectSizeDraft = new ArrayList<String>();
    private Bitmap imgDraft;
    private ArrayList<ReviewsObject> RODraft = new ArrayList<ReviewsObject>();
    private Fragment fragment;
    private SearchView search_view_main;
    private Button buttonCreateReview;
    private Button buttonReviewsHistory;

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
     * @return A new instance of fragment Reviews.
     */
    // TODO: Rename and change types and number of parameters
    public static Reviews newInstance(String param1, String param2) {
        Reviews fragment = new Reviews();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Reviews() {
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
        View rootView = inflater.inflate(R.layout.fragment_reviews, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Отзывы");

        Defaults.setSearchViewStyle(R.id.searchViewReviews, rootView, getActivity());

        //Initialize tab menu icons
        menu_tab = (TextView)rootView.findViewById(R.id.menu_tab);
        nearby_tab = (TextView)rootView.findViewById(R.id.nearby_tab);
        novelty_tab = (TextView)rootView.findViewById(R.id.novelty_tab);
        favor_tab = (TextView)rootView.findViewById(R.id.favor_tab);
        reservation_tab = (TextView)rootView.findViewById(R.id.reservation_tab);
        categories_tab = (TextView)rootView.findViewById(R.id.categories_tab);
        listViewReviews = (ListView)rootView.findViewById(R.id.listViewReviews);
        search_view_main = (SearchView)rootView.findViewById(R.id.searchViewReviews);
        buttonCreateReview = (Button)rootView.findViewById(R.id.buttonCreateReview);
        buttonReviewsHistory = (Button)rootView.findViewById(R.id.buttonReviewsHistory);

        // Set OnClickListener to menu icons
        menu_tab.setOnClickListener(this);
        nearby_tab.setOnClickListener(this);
        novelty_tab.setOnClickListener(this);
        favor_tab.setOnClickListener(this);
        reservation_tab.setOnClickListener(this);
        categories_tab.setOnClickListener(this);
        search_view_main.setOnClickListener(this);
        buttonCreateReview.setOnClickListener(this);
        buttonReviewsHistory.setOnClickListener(this);

        //Example data
        objectFeaturesDraft.add("2 футболки по цене 1!");
        objectFeaturesDraft.add("На любой случай");
        objectFeaturesDraft.add("100% хлопка");
        objectFeaturesDraft.add("Отлично комбинируется");
        objectFeaturesDraft.add("Просто незаменимая модель");

        objectSizeDraft.add("40/42(XS)");
        objectSizeDraft.add("44/46(S)");
        objectSizeDraft.add("48/50(M)");
        objectSizeDraft.add("52/54(L)");
        objectSizeDraft.add("56/58(XL)");
        objectSizeDraft.add("60/62(XXL)");

        imgDraft = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.novelty_draft);

        draft.setObjectUserName("Минкаил");
        draft.setObjectImage(imgDraft);
        draft.setObjectType("Футболка");
        draft.setObjectCompany("Arizona");
        draft.setObjectNumber(375125341);
        draft.setObjectFeatures(objectFeaturesDraft);
        draft.setObjectColor("хаки + белый");
        draft.setObjectSize(objectSizeDraft);
        draft.setObjectPrice(1499.00);
        draft.setObjectFirstValue(45);
        draft.setObjectSecondValue(85);
        draft.setObjectThirdValue(73);

        RODraft.add(draft);

        ReviewsAdapter adapter = new ReviewsAdapter(getActivity(), RODraft, null);
        adapter.setRatingListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingBar ratingBar1;
                TextView textRating1;
                RatingBar ratingBar2;
                TextView textRating2;
                RatingBar ratingBar3;
                TextView textRating3;
                for (int i = 0; i < listViewReviews.getChildCount(); i++) {
                    ratingBar1 = (RatingBar)listViewReviews.getChildAt(i).findViewById(R.id.noveltyRatingBar1);
                    textRating1 = (TextView)listViewReviews.getChildAt(i).findViewById(R.id.noveltyRating1);
                    ratingBar2 = (RatingBar)listViewReviews.getChildAt(i).findViewById(R.id.noveltyRatingBar2);
                    textRating2 = (TextView)listViewReviews.getChildAt(i).findViewById(R.id.noveltyRating2);
                    ratingBar3 = (RatingBar)listViewReviews.getChildAt(i).findViewById(R.id.noveltyRatingBar3);
                    textRating3 = (TextView)listViewReviews.getChildAt(i).findViewById(R.id.noveltyRating3);
                    textRating1.setVisibility(View.VISIBLE);
                    textRating2.setVisibility(View.VISIBLE);
                    textRating3.setVisibility(View.VISIBLE);
                    listViewReviews.getChildAt(i).findViewById(R.id.reviews_text_like).setVisibility(View.INVISIBLE);
                    listViewReviews.getChildAt(i).findViewById(R.id.reviews_text_not_like).setVisibility(View.INVISIBLE);
                    listViewReviews.getChildAt(i).findViewById(R.id.reviews_text_not_know).setVisibility(View.INVISIBLE);
                    if (v == listViewReviews.getChildAt(i).findViewById(R.id.reviews_text_like)){
                        textRating1.setText(draft.getObjectFirstValue() + "%");
                        textRating2.setText(draft.getObjectSecondValue() + "%");
                        textRating3.setText(draft.getObjectThirdValue() + "%");
                        ratingBar1.setVisibility(View.VISIBLE);
                        ratingBar2.setVisibility(View.VISIBLE);
                        ratingBar3.setVisibility(View.VISIBLE);
                        ratingBar1.setRating((float)draft.getObjectFirstValue() / 100);
                        ratingBar2.setRating((float)draft.getObjectSecondValue() / 100);
                        ratingBar3.setRating((float)draft.getObjectThirdValue() / 100);
                    }
                    if (v == listViewReviews.getChildAt(i).findViewById(R.id.reviews_text_not_like)){
                        textRating1.setText(draft.getObjectFirstValue() + "%");
                        textRating2.setText(draft.getObjectSecondValue() + "%");
                        textRating3.setText(draft.getObjectThirdValue() + "%");
                        ratingBar1.setVisibility(View.VISIBLE);
                        ratingBar2.setVisibility(View.VISIBLE);
                        ratingBar3.setVisibility(View.VISIBLE);
                        ratingBar1.setRating((float)draft.getObjectFirstValue() / 100);
                        ratingBar2.setRating((float)draft.getObjectSecondValue() / 100);
                        ratingBar3.setRating((float)draft.getObjectThirdValue() / 100);
                    }
                    if (v == listViewReviews.getChildAt(i).findViewById(R.id.reviews_text_not_know)){
                        textRating1.setText(draft.getObjectFirstValue() + "%");
                        textRating2.setText(draft.getObjectSecondValue() + "%");
                        textRating3.setText(draft.getObjectThirdValue() + "%");
                        ratingBar1.setVisibility(View.VISIBLE);
                        ratingBar2.setVisibility(View.VISIBLE);
                        ratingBar3.setVisibility(View.VISIBLE);
                        ratingBar1.setRating((float)draft.getObjectFirstValue() / 100);
                        ratingBar2.setRating((float)draft.getObjectSecondValue() / 100);
                        ratingBar3.setRating((float)draft.getObjectThirdValue() / 100);
                    }

                }
            }
        });
        listViewReviews.setAdapter(adapter);

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
            case R.id.menu_tab:
                fragment = new MenuItems();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.nearby_tab:
                fragment = new Nearby();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.novelty_tab:
                fragment = new Novelty();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.favor_tab:
                fragment = new Favourites();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.reservation_tab:
                fragment = new Reservation();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.categories_tab:
                fragment = new Categories();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.searchViewReviews:
                search_view_main.onActionViewExpanded();
                break;
            case R.id.buttonReviewsHistory:
                fragment = new ReviewsHistory();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.buttonCreateReview:
                fragment = new CreateInterview();
                Defaults.replaceFragment(fragment, getActivity());
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

    @Override
    public void onResume()
    {
        super.onResume();
        // This call on your list view to update rows
        objectFeaturesDraft = new ArrayList<String>();
        objectSizeDraft = new ArrayList<String>();
        RODraft = new ArrayList<ReviewsObject>();
    }

}
