package com.tataev.appyes.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tataev.appyes.Defaults;
import com.tataev.appyes.R;
import com.tataev.appyes.RoundImage;
import com.tataev.appyes.helper.SQLiteHandlerUser;
import com.tataev.appyes.helper.SessionManager;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserData.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserData#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserData extends Fragment implements View.OnClickListener{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private SQLiteHandlerUser db;
    private SessionManager session;
    private Fragment fragment;
    private ImageView imageUserDataLogo;
    private TextView editUserDataLogin;
    private TextView editUserDataEmail;
    private Button buttonSettings;
    private Button buttonLogOut;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserData.
     */
    public static UserData newInstance(String param1, String param2) {
        UserData fragment = new UserData();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public UserData() {
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
        View rootView = inflater.inflate(R.layout.fragment_user_data, container, false);
        editUserDataLogin = (TextView)rootView.findViewById(R.id.editUserDataLogin);
        editUserDataEmail = (TextView)rootView.findViewById(R.id.editUserDataEmail);
        buttonSettings = (Button)rootView.findViewById(R.id.buttonSettings);
        buttonLogOut = (Button)rootView.findViewById(R.id.buttonLogOut);
        imageUserDataLogo = (ImageView) rootView.findViewById(R.id.imageUserDataLogo);

        // SqLite database handler
        db = new SQLiteHandlerUser(getActivity().getApplicationContext());
        // session manager
        session = new SessionManager(getActivity().getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String login = user.get("login");
        String email = user.get("email");
        String photo = user.get("photo");

        // Displaying the user details on the screen
        editUserDataLogin.setText(login);
        editUserDataEmail.setText(email);
        if (!photo.isEmpty()) {
            try {
                byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                RoundImage roundedImage = new RoundImage(decodedByte, 350, 350);
                imageUserDataLogo.setImageDrawable(roundedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        buttonSettings.setOnClickListener(this);
        buttonLogOut.setOnClickListener(this);

        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSettings:
                fragment = new ProfileSettings();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.buttonLogOut:
                logoutUser();
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
        public void onFragmentInteraction(Uri uri);
    }

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        db.deleteFriends();

        // Launching the login activity
        fragment = new Profile();
        Defaults.replaceFragment(fragment, getActivity());
    }

}
