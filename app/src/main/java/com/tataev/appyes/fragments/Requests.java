package com.tataev.appyes.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
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
import com.tataev.appyes.UsersList;
import com.tataev.appyes.adapters.RequestsAdapter;
import com.tataev.appyes.adapters.UsersAdapter;
import com.tataev.appyes.adapters.UsersSearchAdapter;
import com.tataev.appyes.helper.SQLiteHandlerUser;
import com.tataev.appyes.helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Requests.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Requests#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Requests extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = Requests.class.getSimpleName();
    private ListView listViewRequests;
    private RequestsAdapter requestsAdapter;
    private ArrayList<UsersList> requestsList = new ArrayList<UsersList>();
    private Bitmap bitmap;
    private ImageView imageRequest2;
    private SearchView search_view_requests;
    private Map<String, String> params;
    private ProgressDialog pDialog;
    private String uid;
    private SQLiteHandlerUser db;
    private SessionManager session;

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
     * @return A new instance of fragment Requests.
     */
    // TODO: Rename and change types and number of parameters
    public static Requests newInstance(String param1, String param2) {
        Requests fragment = new Requests();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Requests() {
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
        View rootView = inflater.inflate(R.layout.fragment_requests, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Заявки");

        Defaults.setSearchViewStyle(R.id.searchViewRequests, rootView, getActivity());

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandlerUser(getActivity().getApplicationContext());

        // Session manager
        session = new SessionManager(getActivity().getApplicationContext());


        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        uid =  user.get("uid");

        //Initialize request icon, search field and users ListView
        imageRequest2 = (ImageView)rootView.findViewById(R.id.imageRequest2);
        listViewRequests = (ListView)rootView.findViewById(R.id.listViewRequests);

        search_view_requests = (SearchView)rootView.findViewById(R.id.searchViewRequests);
        search_view_requests.setOnClickListener(this);
        imageRequest2.setOnClickListener(this);

        requestsAdapter = new RequestsAdapter(getActivity(), requestsList, uid);
        listViewRequests.setAdapter(requestsAdapter);

        pDialog.setMessage("Loading ...");
        showDialog();
        getRequests();

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
            case R.id.searchViewRequests:
                search_view_requests.onActionViewExpanded();
                break;
            case R.id.imageRequest2:
                Fragment fragment = new Requests();
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

    private ArrayList<UsersList> getRequests() {
        // Tag used to cancel the request
        String tag_string_req = "req_get_requests";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_REQUEST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Loading Response: " + response.toString());

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Boolean history;
                    Boolean recommendations;
                    if (jsonArray != null) {
                        for (int i = 0; i < jsonArray.length(); i++){

                            JSONObject jObj = jsonArray.getJSONObject(i);
                            String unique_id = jObj.getString("id");
                            String login = jObj.getString("login");
                            String name = jObj.getString("name");
                            String surname = jObj.getString("surname");
                            String photo = jObj.getString("photo");
                            Integer historyInt = jObj.getInt("history");
                            Integer recommendationsInt = jObj.getInt("recommendations");

                            if (historyInt == 1) {
                                history = true;
                            } else {
                                history = false;
                            }
                            if (recommendationsInt == 1) {
                                recommendations = true;
                            } else {
                                recommendations = false;
                            }
                            requestsList.add(new UsersList(unique_id, login, photo, surname + " " + name,
                                    history,  recommendations));
                        }

                        requestsAdapter.notifyDataSetChanged();

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        Toast.makeText(getActivity().getApplicationContext(),
                                "error loading", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Loading Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                params = new HashMap<String, String>();
                params.put("id_receiver", uid);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        return requestsList;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
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
