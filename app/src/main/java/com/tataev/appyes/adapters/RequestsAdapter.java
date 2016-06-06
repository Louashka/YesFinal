package com.tataev.appyes.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tataev.appyes.AppConfig;
import com.tataev.appyes.AppController;
import com.tataev.appyes.R;
import com.tataev.appyes.RoundImage;
import com.tataev.appyes.UsersList;
import com.tataev.appyes.helper.SQLiteHandlerUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lou on 04.01.2016.
 */
public class RequestsAdapter extends BaseAdapter {

    private ArrayList<UsersList> requestsList;
    private Context context;
    private LayoutInflater l_InflaterUA;
    private SQLiteHandlerUser db;
    private Map<String, String> params;
    private ProgressDialog pDialog;
    private String id_receiver;
    private boolean error;

    public RequestsAdapter (Context context, ArrayList<UsersList> requestsList, String id_receiver) {
        this.context = context;
        this.requestsList = requestsList;
        this.id_receiver = id_receiver;
        l_InflaterUA = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return requestsList.size();
    }

    @Override
    public Object getItem(int position) {
        return requestsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        RoundImage roundedImage = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = l_InflaterUA.inflate(R.layout.requests_list_adapter, null);
            holder.imageRequestAva = (ImageView)convertView.findViewById(R.id.imageRequestAva);
            holder.usersNameRequest = (TextView)convertView.findViewById(R.id.usersNameRequest);
            holder.usersStatusRequest = (RadioButton)convertView.findViewById(R.id.usersStatusRequest);
            holder.usersDecline = (ImageView)convertView.findViewById(R.id.usersDecline);
            holder.usersAccept = (ImageView)convertView.findViewById(R.id.usersAccept);
            holder.requestDecline = (TextView)convertView.findViewById(R.id.requestDecline);
            holder.requestAccept = (TextView)convertView.findViewById(R.id.requestAccept);

            db = new SQLiteHandlerUser(context.getApplicationContext());
            pDialog = new ProgressDialog(context);
            pDialog.setCancelable(false);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            byte[] decodedString = Base64.decode(requestsList.get(position).getUserPhoto(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            roundedImage = new RoundImage(decodedByte, 180, 180);
            holder.imageRequestAva.setImageDrawable(roundedImage);
        } catch (Exception e) {
            holder.imageRequestAva.setImageDrawable(new RoundImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.users_logo_default), 180, 180));
            e.printStackTrace();
        }



        holder.usersNameRequest.setText(requestsList.get(position).getUserName());
        holder.usersDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.setMessage("Loading ...");
                showDialog();
                boolean request = declineRequest(position);
                if (!request) {
                    hideDialog();
                    holder.usersDecline.setVisibility(View.GONE);
                    holder.usersAccept.setVisibility(View.GONE);
                    holder.requestDecline.setVisibility(View.VISIBLE);
                } else {
                    hideDialog();
                }
            }
        });
        holder.usersAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.setMessage("Loading ...");
                showDialog();
                boolean request = acceptRequest(position);
                if (!request) {
                    hideDialog();
                    holder.usersDecline.setVisibility(View.GONE);
                    holder.usersAccept.setVisibility(View.GONE);
                    holder.requestAccept.setVisibility(View.VISIBLE);
                } else {
                    hideDialog();
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        ImageView imageRequestAva;
        TextView usersNameRequest;
        RadioButton usersStatusRequest;
        ImageView usersDecline;
        ImageView usersAccept;
        TextView requestDecline;
        TextView requestAccept;
    }

    private Boolean declineRequest(final int position) {
        // Tag used to cancel the request
        String tag_string_req = "req_decline_request";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DECLINE_REQUEST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    error = jObj.getBoolean("error");
                    if (!error) {
                        Toast.makeText(context.getApplicationContext(),
                                "Запрос отклонен", Toast.LENGTH_LONG).show();
                    } else {
                        // Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(context.getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                params = new HashMap<String, String>();
                params.put("id_sender", requestsList.get(position).getUserId());
                params.put("id_receiver", id_receiver);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        return error;
    }

    private Boolean acceptRequest(final int position) {
        // Tag used to cancel the request
        String tag_string_req = "req_accept_request";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ACCEPT_REQUEST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    error = jObj.getBoolean("error");
                    if (!error) {
                        Toast.makeText(context.getApplicationContext(),
                                "Запрос принят", Toast.LENGTH_LONG).show();
                        String unique_id = jObj.getString("id");
                        String login = jObj.getString("login");
                        String name = jObj.getString("name");
                        String surname = jObj.getString("surname");
                        String photo = jObj.getString("photo");
                        Integer historyInt = jObj.getInt("history");
                        Integer recommendationsInt = jObj.getInt("recommendations");

                        // Inserting row in users table
                        db.addFriend(unique_id, login, name, surname, photo, historyInt, recommendationsInt);
                    } else {
                        // Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(context.getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                params = new HashMap<String, String>();
                params.put("id_sender", requestsList.get(position).getUserId());
                params.put("id_receiver", id_receiver);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        return error;
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
