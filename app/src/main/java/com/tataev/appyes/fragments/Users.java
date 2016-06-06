package com.tataev.appyes.fragments;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.costum.android.widget.LoadMoreListView;
import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullToRefreshListView;
import com.tataev.appyes.AppConfig;
import com.tataev.appyes.AppController;
import com.tataev.appyes.Defaults;
import com.tataev.appyes.MainActivity;
import com.tataev.appyes.R;
import com.tataev.appyes.RoundImage;
import com.tataev.appyes.UsersList;
import com.tataev.appyes.adapters.UsersAdapter;
import com.tataev.appyes.adapters.UsersSearchAdapter;
import com.tataev.appyes.helper.SQLiteHandlerUser;
import com.tataev.appyes.helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Users.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Users#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Users extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ExpandableListView exListView;
    private UsersSearchAdapter usersSearchAdapter;
    private LoadMoreListView loadMoreListView;
    private PullAndLoadListView pullToRefreshListView;
    private UsersAdapter friendsAdapter;
    private UsersAdapter usersAdapter;
    private Fragment fragment;
    private ArrayList<UsersList> usersFriends = new ArrayList<UsersList>();
    private ArrayList<UsersList> friendsDB = new ArrayList<UsersList>();
    private ArrayList<UsersList> usersFriendsJson = new ArrayList<UsersList>();
    private ArrayList<UsersList> allUsers = new ArrayList<UsersList>();
    private ArrayList<UsersList> allUsersJson = new ArrayList<UsersList>();
    private ArrayList<UsersList> requestFriend = new ArrayList<UsersList>();
    private Bitmap bitmap;
    private ImageView imageRequest;
    private SearchView search_view_main;
    private static final String TAG = Users.class.getSimpleName();
    private Map<String, String> params;
    private int k = 10;
    private ProgressDialog pDialog;
    private SQLiteHandlerUser db;
    private SessionManager session;
    private String uid;
    private AppController userGlobalClass;
    private String query_default = "SELECT * FROM users";
    private String query_condition = "";
    private Calendar dateFrom;
    private Calendar dateTo;
    private Format f;

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
     * @return A new instance of fragment Users.
     */
    // TODO: Rename and change types and number of parameters
    public static Users newInstance(String param1, String param2) {
        Users fragment = new Users();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Users() {
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
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_users, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Пользователи");

        Defaults.setSearchViewStyle(R.id.searchViewUsers, rootView, getActivity());
        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandlerUser(getActivity().getApplicationContext());

        // Session manager
        session = new SessionManager(getActivity().getApplicationContext());

        // Check if user is already logged in or not
        if (!session.isLoggedIn()) {
            db.deleteFriends();
            Toast.makeText(getActivity().getApplicationContext(),
                    "Вы не авторизованы!", Toast.LENGTH_LONG).show();
            fragment = new Profile();
            Defaults.replaceFragment(fragment, getActivity());
        }

        // Fetching friends details from sqlite
        friendsDB = db.getFriendDetails();

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        uid =  user.get("uid");

        if(friendsDB.isEmpty()) {
            pDialog.setMessage("Loading ...");
            showDialog();
            getUsersFriends();
        } else {
            usersFriends.addAll(friendsDB);
        }

        //Initialize request icon, search field and users ListView
        imageRequest = (ImageView)rootView.findViewById(R.id.imageRequest);
        exListView = (ExpandableListView) rootView.findViewById(R.id.exListView);
        loadMoreListView = (LoadMoreListView)rootView.findViewById(R.id.loadMoreListView);
        pullToRefreshListView = (PullAndLoadListView) rootView.findViewById(R.id.pullToRefreshListView);
        userGlobalClass = (AppController)getActivity().getApplicationContext();
        dateFrom = Calendar.getInstance();
        dateTo= Calendar.getInstance();

        f = new SimpleDateFormat("yyyy-MM-dd");

        search_view_main = (SearchView)rootView.findViewById(R.id.searchViewUsers);
        search_view_main.setOnClickListener(this);
        imageRequest.setOnClickListener(this);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        //change the position of the group dropdown indicator
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            exListView.setIndicatorBounds(width-GetPixelFromDips(35), width-GetPixelFromDips(5));
        } else {
            exListView.setIndicatorBoundsRelative(width-GetPixelFromDips(35), width-GetPixelFromDips(5));
        }

        usersSearchAdapter = new UsersSearchAdapter(getActivity());
        usersSearchAdapter.hasStableIds();
        exListView.setAdapter(usersSearchAdapter);

        exListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                pullToRefreshListView.setVisibility(View.GONE);
                loadMoreListView.setVisibility(View.VISIBLE);
                usersAdapter = new UsersAdapter(getActivity(), allUsers);
                loadMoreListView.setAdapter(usersAdapter);
                allUsers.clear();
                allUsersJson.clear();
                usersAdapter.notifyDataSetChanged();
                query_condition = "";
                dateFrom.setTime(new Date());
                dateTo.setTime(new Date());
                if (userGlobalClass.getRadioGender() == R.id.radioButtonM) {
                    query_condition += " WHERE gender = 'm'";
                } else if (userGlobalClass.getRadioGender() == R.id.radioButtonF) {
                    query_condition += " WHERE gender = 'f'";
                } else {
                    query_condition += " WHERE gender LIKE '%'";
                }
                if (userGlobalClass.getSpinnerCountryItem() != 0) {
                    if (userGlobalClass.getSpinnerCityItem() != 0) {
                        if (userGlobalClass.getCity() != null) {
                            query_condition += " AND address LIKE '%" + userGlobalClass.getCity() + "%'";
                        }
                    } else if (userGlobalClass.getCountry() != null) {
                        query_condition += " AND address LIKE '%" + userGlobalClass.getCountry() + "%'";
                    }
                } else {
                    query_condition += " AND address LIKE '%'";
                }
                if (userGlobalClass.getAgeFrom() != 0 && userGlobalClass.getAgeTo() != 0) {
                    dateFrom.add(Calendar.YEAR,-userGlobalClass.getAgeFrom()-11);
                    dateTo.add(Calendar.YEAR,-userGlobalClass.getAgeTo()-11);
                    query_condition += " AND birthday between '" + f.format(dateTo.getTime())
                    + "' AND '" + f.format(dateFrom.getTime()) + "'";
                } else {
                    if (userGlobalClass.getAgeFrom() != 0) {
                        dateFrom.add(Calendar.YEAR,-userGlobalClass.getAgeFrom()-11);
                        query_condition += " AND birthday <= " + f.format(dateFrom.getTime()) ;
                    }
                    if (userGlobalClass.getAgeTo() != 0) {
                        dateTo.add(Calendar.YEAR,-userGlobalClass.getAgeTo()-11);
                        query_condition += " AND birthday >= " + f.format(dateTo.getTime());
                    }
                }
                pDialog.setMessage("Loading ...");
                showDialog();
                getSearchedUsers();
            }
        });

        //*** setOnQueryTextListener ***
        search_view_main.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                query_condition = " WHERE login LIKE '%" + query + "%'" + " OR name LIKE '%" + query + "%'"
                        + " OR surname LIKE '%" + query + "%'";
                pullToRefreshListView.setVisibility(View.GONE);
                loadMoreListView.setVisibility(View.VISIBLE);
                usersAdapter = new UsersAdapter(getActivity(), allUsers);
                loadMoreListView.setAdapter(usersAdapter);
                allUsers.clear();
                allUsersJson.clear();
                usersAdapter.notifyDataSetChanged();
                pDialog.setMessage("Loading ...");
                showDialog();
                getSearchedUsers();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub

                return false;
            }
        });

        friendsAdapter = new UsersAdapter(getActivity(), usersFriends);
        pullToRefreshListView.setAdapter(friendsAdapter);

        pullToRefreshListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final String userId = usersFriends.get(position).getUserId();
                final String userLogin = usersFriends.get(position).getUserLogin();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Удалить из списка друзей?")
                        .setCancelable(true)
                        .setPositiveButton("Да",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        showDialog();
                                        deleteFriend(userLogin, userId, position);
                                    }
                                })
                        .setNegativeButton("Нет",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
                return false;
            }
        });

        (pullToRefreshListView)
                .setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {

                    public void onRefresh() {
                        // Do work to refresh the list here.
                        db.deleteFriends();
                        usersFriends = new ArrayList<UsersList>();
                        new PullToRefreshDataTask().execute();
                    }
                });

        loadMoreListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String userId = allUsers.get(position).getUserId();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Добавить пользователя в друзья?")
                        .setCancelable(true)
                        .setPositiveButton("Да",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        showDialog();
                                        addFriend(uid, userId);
                                    }
                                })
                        .setNegativeButton("Нет",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
                return false;
            }
        });

        (loadMoreListView)
                .setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
                    public void onLoadMore() {
                        // Do the work to load more items at the end of list here
                        new LoadAllUsersDataTask().execute();
                    }
                });

        final String[] tag_string_req = {"req_get_request"};

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
                        imageRequest.setVisibility(View.VISIBLE);
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
                            requestFriend.add(new UsersList(unique_id, login, photo, surname + " " + name,
                                    history,  recommendations));
                        }

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        Toast.makeText(getActivity().getApplicationContext(),
                                "error loading", Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Loading Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> param = new HashMap<String, String>();
                param.put("id_receiver", uid);

                return param;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req[0]);

        return rootView;
    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
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
            case R.id.searchViewUsers:
                search_view_main.onActionViewExpanded();
                break;
            case R.id.imageRequest:
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private class PullToRefreshDataTask extends AsyncTask<Void, Void, Void> {

        private Map<String, String> parametres;

        @Override
        protected Void doInBackground(Void... params) {

            if (isCancelled()) {
                return null;
            }
            // Simulates a background task
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            getUsersFriends();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // We need notify the adapter that the data have been changed
            friendsAdapter.notifyDataSetChanged();

            // Call onLoadMoreComplete when the LoadMore task, has finished
            pullToRefreshListView.onRefreshComplete();

            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {
            // Notify the loading more operation has finished
            pullToRefreshListView.onLoadMoreComplete();
        }
    }

    private class LoadAllUsersDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            int count;

            if (isCancelled()) {
                return null;
            }

            // Simulates a background task
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

            if (k + 10 < allUsersJson.size()) {
                count = k + 10;
            } else {
                count = allUsersJson.size();
            }
            for (int i = k; i < count; i++) {
                allUsers.add(allUsersJson.get(i));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // We need notify the adapter that the data have been changed
            usersAdapter.notifyDataSetChanged();

            // Call onLoadMoreComplete when the LoadMore task, has finished
            loadMoreListView.onLoadMoreComplete();
            k +=10;

            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {
            // Notify the loading more operation has finished
            loadMoreListView.onLoadMoreComplete();
        }
    }

    private ArrayList<UsersList> getUsersFriends() {
        // Tag used to cancel the request
        String tag_string_req = "req_get_friends";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_FRIENDS, new Response.Listener<String>() {

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

                            // Inserting row in users table
                            db.addFriend(unique_id, login, name, surname, photo, historyInt, recommendationsInt);

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
                            usersFriendsJson.add(new UsersList(unique_id, login, photo, surname + " " + name,
                                    history,  recommendations));
                        }

                        usersFriends.addAll(usersFriendsJson);
                        friendsAdapter.notifyDataSetChanged();

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
                params.put("id_user", uid);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        return usersFriends;
    }

    private void deleteFriend (final String login, final String id_friend, final int position) {
        String tag_string_req = "req_delete_friend";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DELETE_FRIEND, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Loading Response: " + response.toString());
                db.deleteFriendByLogin(login);
                usersFriends.remove(position - 1);
                friendsAdapter.notifyDataSetChanged();
                hideDialog();
                Toast.makeText(getActivity().getApplicationContext(),
                        "Пользователь удален из друзей", Toast.LENGTH_LONG).show();
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
                params.put("id_user", uid);
                params.put("id_friend", id_friend);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private ArrayList<UsersList> getSearchedUsers() {
        // Tag used to cancel the request
        String tag_string_req = "req_get_users";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_USERS, new Response.Listener<String>() {

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
                            allUsersJson.add(new UsersList(unique_id, login, photo, surname + " " + name,
                                    history,  recommendations));
                        }

                        if (allUsersJson.size() > 10) {
                            for (int i = 0; i < 10; i++) {
                                allUsers.add(allUsersJson.get(i));
                            }
                        } else {
                            allUsers.addAll(allUsersJson);
                        }

                        usersAdapter.notifyDataSetChanged();

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
                params.put("query", query_default + query_condition);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        return usersFriends;
    }

    private void addFriend (final String id_sender, final String id_receiver) {

        String tag_string_req = "req_add_friend";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADD_FRIEND, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Loading Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Запрос отправлен", Toast.LENGTH_LONG).show();
                    } else {
                        // Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getActivity().getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                params.put("id_sender", id_sender);
                params.put("id_receiver", id_receiver);

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

    @Override
    public void onResume()
    {
        super.onResume();
        // This call on your list view to update rows
        usersFriends = new ArrayList<UsersList>();
        friendsDB = new ArrayList<UsersList>();
        usersFriendsJson = new ArrayList<UsersList>();
        allUsers = new ArrayList<UsersList>();
        allUsersJson = new ArrayList<UsersList>();
        requestFriend = new ArrayList<UsersList>();
    }

}
