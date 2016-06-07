package com.tataev.appyes.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.tataev.appyes.RoundImage;
import com.tataev.appyes.helper.SQLiteHandlerUser;
import com.tataev.appyes.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Registration.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Registration#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Registration extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Uri mImageCaptureUri;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private ImageView imageRegLogo;
    private Button buttonReg;
    private String insertTableSQL;
    private static final String TAG = Registration.class.getSimpleName();
    private EditText editRegLogin;
    private EditText editRegPassword;
    private EditText editRegRepeatPswd;
    private EditText editRegEmail;
    private CheckBox checkBoxShowHistory;
    private CheckBox checkShowRecom;
    private CheckBox checkBoxConfReg;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandlerUser db;
    private Fragment fragment;
    private Bitmap mBitmap;
    private Map<String, String> params;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Registration.
     */
    // TODO: Rename and change types and number of parameters
    public static Registration newInstance(String param1, String param2) {
        Registration fragment = new Registration();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Registration() {
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
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Регистрация");
        View rootView = inflater.inflate(R.layout.fragment_registration, container, false);
        imageRegLogo = (ImageView)rootView.findViewById(R.id.imageRegLogo);
        buttonReg = (Button)rootView.findViewById(R.id.buttonReg);
        editRegLogin = (EditText) rootView.findViewById(R.id.editRegLogin);
        editRegPassword = (EditText) rootView.findViewById(R.id.editRegPassword);
        editRegRepeatPswd = (EditText) rootView.findViewById(R.id.editRegRepeatPswd);
        editRegEmail = (EditText) rootView.findViewById(R.id.editRegEmail);
        checkBoxShowHistory = (CheckBox)rootView.findViewById(R.id.checkBoxShowHistory);
        checkShowRecom = (CheckBox)rootView.findViewById(R.id.checkShowRecom);
        checkBoxConfReg = (CheckBox)rootView.findViewById(R.id.checkBoxConfReg);

        checkBoxConfReg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonReg.setEnabled(true);
                } else {
                    buttonReg.setEnabled(false);
                }
            }
        });

        imageRegLogo.setMaxWidth(350);
        imageRegLogo.setMaxHeight(350);
        imageRegLogo.setOnClickListener(this);


        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getActivity().getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandlerUser(getActivity().getApplicationContext());

        buttonReg.setOnClickListener(this);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                mImageCaptureUri = data.getData();
                doCrop();
                break;
            case CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();
                if (extras != null) {
                    try {
                        mBitmap = extras.getParcelable("data");
                        RoundImage roundedImage = new RoundImage(mBitmap, 350, 350);
                        imageRegLogo.setImageDrawable(roundedImage);
                    } catch(Exception e){
                        Toast.makeText(getActivity(), "Failed loading image from gallery", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                break;
        }

    }

    private void doCrop() {
        /**
         * Open image crop app by starting an intent
         * ‘com.android.camera.action.CROP‘.
         */
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        /**
         * Check if there is image cropper app installed.
         */
        List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(
                intent, 0);

        int size = list.size();

        /**
         * If there is no image cropper app, display warning message
         */
        if (size == 0) {

            Toast.makeText(getActivity(), "Can not find image crop app",
                    Toast.LENGTH_SHORT).show();

            return;
        } else {
            /**
             * Specify the image path, crop dimension and scale
             */
            intent.setData(mImageCaptureUri);

            intent.putExtra("aspectX", 50);
            intent.putExtra("aspectY", 50);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            /**
             * There is posibility when more than one image cropper app exist,
             * so we have to check for it first. If there is only one app, open
             * then app.
             */

            Intent i = new Intent(intent);
            ResolveInfo res = list.get(0);

            i.setComponent(new ComponentName(res.activityInfo.packageName,
                    res.activityInfo.name));

            startActivityForResult(i, CROP_FROM_CAMERA);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageRegLogo:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
            case R.id.buttonReg:
                String login = editRegLogin.getText().toString().trim();
                String email = editRegEmail.getText().toString().trim();
                String password = editRegPassword.getText().toString().trim();
                String repeatPassword = editRegRepeatPswd.getText().toString().trim();
                String history = "0";
                String recommendations = "0";
                if (checkBoxShowHistory.isChecked()) history = "1";
                if (checkShowRecom.isChecked()) recommendations = "1";

                if (!login.isEmpty() && !email.isEmpty() && !password.isEmpty() && !repeatPassword.isEmpty()) {
                    if (password.length() > 6) {
                        if (password.equals(repeatPassword)) {
                            if (Defaults.isEmailValid(email)) {
                                registerUser(login, email, password, history, recommendations);
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Неверный e-mail", Toast.LENGTH_LONG)
                                        .show();
                            }
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Пароли не совпадают", Toast.LENGTH_LONG)
                                    .show();
                        }
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Пароль слишком короткий", Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Пожалуйста, заполните все поля!", Toast.LENGTH_LONG)
                            .show();
                }
                break;
            default:
                break;
        }
    }

    private void registerUser(final String login, final String email, final String password,
                              final String history, final String recommendations) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
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

                        // Inserting row in users table
                        db.addUser(login, email, uid, name, surname, photo, birthday, gender, address, history,
                                recommendations, created_at, updated_at);

                        Toast.makeText(getActivity().getApplicationContext(), "User successfully registered!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        fragment = new Profile();
                        Defaults.replaceFragment(fragment, getActivity());
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
//                        Toast.makeText(getActivity().getApplicationContext(),
//                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
//                Toast.makeText(getActivity().getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                //Converting Bitmap to String
                String photo = "";
                if (mBitmap != null) photo = Defaults.getStringImage(mBitmap);
                // Posting params to register url
                params = new HashMap<String, String>();
                params.put("login", login);
                params.put("email", email);
                params.put("password", password);
                params.put("history", history);
                params.put("recommendations", recommendations);
                params.put("photo", photo);

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
