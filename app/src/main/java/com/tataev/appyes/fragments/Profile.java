package com.tataev.appyes.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tataev.appyes.AppConfig;
import com.tataev.appyes.AppController;
import com.tataev.appyes.Defaults;
import com.tataev.appyes.MainActivity;
import com.tataev.appyes.R;
import com.tataev.appyes.helper.SQLiteHandlerUser;
import com.tataev.appyes.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String TAG = "";
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandlerUser db;
    private String codeOutput;
    private SharedPreferences sPref;
    private final String UNIQUE_ID = "unique_id";

    private Button buttonReg;
    private Button buttonEnter;
    private TextView textCode;
    private TextView textRefreshImage;
    private EditText editProfCode;
    private EditText editProfLogin;
    private EditText editProfPswd;
    private Fragment fragment;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Profile() {
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
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Профиль");

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandlerUser(getActivity().getApplicationContext());

        // Session manager
        session = new SessionManager(getActivity().getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            fragment = new UserData();
            Defaults.replaceFragment(fragment, getActivity());
        } else {
            db.deleteUsers();
        }

        //Initialize registration and login buttons
        buttonReg = (Button)rootView.findViewById(R.id.buttonReg);
        buttonEnter = (Button)rootView.findViewById(R.id.buttonEnter);
        editProfLogin = (EditText)rootView.findViewById(R.id.editProfLogin);
        editProfPswd = (EditText)rootView.findViewById(R.id.editProfPswd);
        textCode = (TextView)rootView.findViewById(R.id.textCode);
        editProfCode = (EditText)rootView.findViewById(R.id.editProfCode);
        textRefreshImage = (TextView)rootView.findViewById(R.id.textRefreshImage);

        //Setting random string to textCode field
        codeOutput = Defaults.generateRandomCode();
        textCode.setText(codeOutput);

        buttonReg.setOnClickListener(this);
        buttonEnter.setOnClickListener(this);
        textRefreshImage.setOnClickListener(this);
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
        switch (v.getId()){
            case R.id.textRefreshImage:
                codeOutput = Defaults.generateRandomCode();
                textCode.setText(codeOutput);
                break;
            case R.id.buttonReg:
                fragment = new Registration();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.buttonEnter:
                String login = editProfLogin.getText().toString().trim();
                String password = editProfPswd.getText().toString().trim();
                String code = editProfCode.getText().toString().trim();

                // Check for empty data in the form
                if (!login.isEmpty() && !password.isEmpty() && !code.isEmpty()) {
                    // login user
                    if (code.equals(codeOutput)){
                        checkLogin(login, password);
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Неверный код", Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Пожалуйста, заполните все поля!", Toast.LENGTH_LONG)
                            .show();
                }
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

    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String login, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String login = user.getString("login");
                        String email = user.getString("email");
                        String name = user.getString("name");
                        String surname = user.getString("surname");
                        String photo = user.getString("photo");
                        String birthday = user.getString("birthday");
                        String gender = user.getString("gender");
                        String address = user.getString("address");
                        Integer history = user.getInt("history");
                        Integer recommendations = user.getInt("recommendations");
                        String created_at = user
                                .getString("created_at");
                        String updated_at = user
                                .getString("updated_at");

                        //Saving unique id in SharedPreferences
                        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = sPref.edit();
                        ed.putString(UNIQUE_ID, uid);
                        ed.commit();

                        // Inserting row in users table
                        db.addUser(login, email, uid, name, surname, photo, birthday, gender, address, history,
                                recommendations, created_at, updated_at);

                        fragment = new UserData();
                        Defaults.replaceFragment(fragment, getActivity());
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
//                        Toast.makeText(getActivity().getApplicationContext(),
//                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
//                    Toast.makeText(getActivity().getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
//                Toast.makeText(getActivity().getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("login", login);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
