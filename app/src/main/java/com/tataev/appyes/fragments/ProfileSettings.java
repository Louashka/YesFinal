package com.tataev.appyes.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.tataev.appyes.AppConfig;
import com.tataev.appyes.AppController;
import com.tataev.appyes.Defaults;
import com.tataev.appyes.LruBitmapCache;
import com.tataev.appyes.MySingleton;
import com.tataev.appyes.PhotoMultipartRequest;
import com.tataev.appyes.R;
import com.tataev.appyes.RoundImage;
import com.tataev.appyes.helper.SQLiteHandlerUser;
import com.tataev.appyes.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileSettings.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileSettings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSettings extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = ProfileSettings.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String codeOutput;
    private ProgressDialog pDialog;
    private SQLiteHandlerUser db;
    private SessionManager session;
    private String uid;
    private String login;
    private Fragment fragment;
    private Bitmap mBitmap;
    private Map<String, String> params;
    private Uri mImageCaptureUri;
    private ImageLoader mImageLoader;
    private File imageFile;
    private String[] months;
    private int calendarYear;
    private int calendarMonth;
    private int calendarDay;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int CROP_FROM_CAMERA = 2;

    private OnFragmentInteractionListener mListener;
    private ImageView imageLogoSettings;
    private EditText editNameSettings;
    private EditText editSurnameSettings;
    private EditText dateSettings;
    private RadioGroup radioGroupSettings;
    private int radioMale = R.id.radioMaleSetting;
    private int radioFemale = R.id.radioFemaleSettings;
    private EditText editAddressSettings;
    private EditText editEmailSettings;
    private CheckBox checkBoxShowHistorySettings;
    private CheckBox checkShowRecomSettings;
    private EditText editCodeSettings;
    private TextView textCodeSettings;
    private Button buttonSave;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog datePickerDialog;
    private Calendar birthDate;

    public ProfileSettings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileSettings.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileSettings newInstance(String param1, String param2) {
        ProfileSettings fragment = new ProfileSettings();
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile_settings, container, false);

        imageLogoSettings = (ImageView)rootView.findViewById(R.id.imageLogoSettings);
        editNameSettings = (EditText)rootView.findViewById(R.id.editNameSettings);
        editSurnameSettings = (EditText)rootView.findViewById(R.id.editSurnameSettings);
        dateSettings = (EditText)rootView.findViewById(R.id.dateSettings);
        radioGroupSettings = (RadioGroup)rootView.findViewById(R.id.radioGroupSettings);
        editAddressSettings = (EditText)rootView.findViewById(R.id.editAddressSettings);
        editEmailSettings = (EditText)rootView.findViewById(R.id.editEmailSettings);
        checkBoxShowHistorySettings = (CheckBox)rootView.findViewById(R.id.checkBoxShowHistorySettings);
        checkShowRecomSettings = (CheckBox)rootView.findViewById(R.id.checkShowRecomSettings);
        textCodeSettings = (TextView)rootView.findViewById(R.id.textCodeSettings);
        editCodeSettings = (EditText)rootView.findViewById(R.id.editCodeSettings);
        buttonSave = (Button)rootView.findViewById(R.id.buttonSave);
        dateSettings.setInputType(InputType.TYPE_NULL);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        birthDate = Calendar.getInstance();

//        imageLogoSettings.setDefaultImageResId(R.drawable.reg_logo);
//        imageLogoSettings.setErrorImageResId(R.drawable.reg_logo);

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
        login = user.get("login");
        String name = user.get("name");
        String surname = user.get("surname");
        String birthday = user.get("birthday");
        String gender = user.get("gender");
        String address = user.get("address");
        String email = user.get("email");
        String history = user.get("history");
        String recommendations = user.get("recommendations");
        String photo = user.get("photo");

        months = getResources().getStringArray(R.array.months);
        if (!photo.isEmpty()){
            try {
                byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                RoundImage roundedImage = new RoundImage(decodedByte, 350, 350);
                imageLogoSettings.setImageDrawable(roundedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Retrieves an image specified by the URL, displays it in the UI.
//            ImageRequest request = new ImageRequest(url,
//                    new Response.Listener<Bitmap>() {
//                        @Override
//                        public void onResponse(Bitmap bitmap) {
//                            RoundImage roundedImage = new RoundImage(bitmap, 350, 350);
//                            imageLogoSettings.setImageDrawable(roundedImage);
//                        }
//                    }, 0, 0, null,
//                    new Response.ErrorListener() {
//                        public void onErrorResponse(VolleyError error) {
//                            imageLogoSettings.setImageResource(R.drawable.reg_logo);
//                        }
//                    });
//            // Access the RequestQueue through your singleton class.
//            MySingleton.getInstance(getActivity()).addToRequestQueue(request);
//            mImageLoader = MySingleton.getInstance(getActivity()).getImageLoader();
//            mImageLoader.get(url, new ImageLoader.ImageListener() {
//                @Override
//                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
//                    Bitmap bitmap = response.getBitmap();
//                    if (bitmap != null) {
//                        RoundImage roundedImage = new RoundImage(bitmap, 350, 350);
//                        imageLogoSettings.setImageDrawable(roundedImage);
//                    }
//                }
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e(TAG, "Logo Error: " + error.getMessage());
//                    Toast.makeText(getActivity().getApplicationContext(),
//                            error.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            });
//            imageLogoSettings.setImageUrl(url, mImageLoader);
        }

        Calendar newCalendar = Calendar.getInstance();
        if (birthday != null) {
            String year = birthday.split("-")[0];
            String month = birthday.split("-")[1];
            String day = birthday.split("-")[2];
            if (birthday.equals("0000-00-00")) {
                dateSettings.setText("Выберите дату");
                calendarYear = newCalendar.get(Calendar.YEAR);
                calendarMonth = newCalendar.get(Calendar.MONTH);
                calendarDay = newCalendar.get(Calendar.DAY_OF_MONTH);
            } else {
                dateSettings.setText(day + " " + months[Integer.parseInt(month)] + " " + year);
                calendarYear = Integer.parseInt(year);
                calendarMonth = Integer.parseInt(month);
                calendarDay = Integer.parseInt(day);
            }
            birthDate.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        } else {
            calendarYear = newCalendar.get(Calendar.YEAR);
            calendarMonth = newCalendar.get(Calendar.MONTH);
            calendarDay = newCalendar.get(Calendar.DAY_OF_MONTH);
        }

        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                birthDate.set(year, monthOfYear, dayOfMonth);
                dateSettings.setText(dayOfMonth + " " + months[monthOfYear] + " " + year);
            }

        },calendarYear, calendarMonth, calendarDay);

        dateSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        editNameSettings.setText(name);
        editSurnameSettings.setText(surname);
        editAddressSettings.setText(address);
        if (gender.equals("m")) radioGroupSettings.check(radioMale);
        if (gender.equals("f")) radioGroupSettings.check(radioFemale);
        editEmailSettings.setText(email);
        if (history.equals("1")) checkBoxShowHistorySettings.setChecked(true);
        if (recommendations.equals("1")) checkShowRecomSettings.setChecked(true);

        //Setting random string to textCodeSettings field
        codeOutput = Defaults.generateRandomCode();
        textCodeSettings.setText(codeOutput);

        buttonSave.setOnClickListener(this);
        imageLogoSettings.setMaxWidth(350);
        imageLogoSettings.setMaxHeight(350);
        imageLogoSettings.setOnClickListener(this);

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
//                        imageFile = Defaults.persistImage(getActivity(), mBitmap, login);
                        RoundImage roundedImage = new RoundImage(mBitmap, 350, 350);
                        imageLogoSettings.setImageDrawable(roundedImage);
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
        switch (v.getId()){
            case R.id.imageLogoSettings:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
            case R.id.buttonSave:
                String email = editEmailSettings.getText().toString().trim();
                String name = editNameSettings.getText().toString().trim();
                String surname = editSurnameSettings.getText().toString().trim();
                String birthday = dateFormatter.format(birthDate.getTime()).trim();
                String gender = "not define";
                int id = radioGroupSettings.getCheckedRadioButtonId();
                if (id == radioMale) gender = "m";
                if (id == radioFemale) gender = "f";
                String address = editAddressSettings.getText().toString().trim();
                String history = "0";
                String recommendations = "0";
                if (checkBoxShowHistorySettings.isChecked()) history = "1";
                if (checkShowRecomSettings.isChecked()) recommendations = "1";

                String code = editCodeSettings.getText().toString().trim();
                if (!uid.isEmpty()) {
                    if (code.equals(codeOutput)){
                        updateUser(uid, name, surname, history, recommendations, birthday, gender, address, email);
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Неверный код", Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Ошибка: пользователь не авторизован!", Toast.LENGTH_LONG)
                            .show();
                }
                break;
        }

    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
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
        void onFragmentInteraction(Uri uri);
    }

    private void updateUser (final String uid, final String name, final String surname, final String history, final String recommendations,
                             final String birthday, final String gender, final String address, final String email) {

        // Tag used to cancel the request
        String tag_string_req = "req_update";

        pDialog.setMessage("Updating ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPDATE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Update Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite

                        JSONObject user = jObj.getJSONObject("user");
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

                        // Updating row in users table
                        db.updateUser(email, uid, name, surname, photo, birthday, gender, address, history,
                                recommendations, created_at, updated_at);

                        Toast.makeText(getActivity().getApplicationContext(), "User successfully updated!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        fragment = new UserData();
                        Defaults.replaceFragment(fragment, getActivity());
                    } else {

                        // Error occurred in registration. Get the error
                        // message
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
                Log.e(TAG, "Update Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
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
                params.put("uid", uid);
                params.put("login", login);
                params.put("name", name);
                params.put("surname", surname);
                params.put("photo", photo);
                params.put("history", history);
                params.put("recommendations", recommendations);
                params.put("birthday", birthday);
                params.put("gender", gender);
                params.put("address", address);
                params.put("email", email);

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
