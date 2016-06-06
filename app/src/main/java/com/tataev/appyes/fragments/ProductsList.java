package com.tataev.appyes.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tataev.appyes.Defaults;
import com.tataev.appyes.Goods;
import com.tataev.appyes.R;
import com.tataev.appyes.adapters.SubCategoriesAdapter;
import com.tataev.appyes.adapters.SubCategoriesAdapterCard;
import com.tataev.appyes.adapters.SubCategoriesAdapterGrid;
import com.tataev.appyes.dbConnection.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductsList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductsList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsList extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView listView,listViewCard;
    ArrayList<Goods> listData;
    SubCategoriesAdapter adapter;
    SubCategoriesAdapterCard adapterCard;
    SubCategoriesAdapterGrid adapterGrid;
    GridView gridView;
     String  url = "";
    String image;
    Bitmap bmp;

    Spinner spinner;
    private Fragment fragment;
    private TextView menu_ccl_tab;
    private TextView nearby_ccl_tab;
    private TextView novelty_ccl_tab;
    private TextView favor_ccl_tab;
    private TextView reservation_ccl_tab;
    private TextView categories_ccl_tab;

@SuppressLint("ValidFragment")
public ProductsList(String url){
    this.url=url;

}
    ArrayList<Goods> List = new ArrayList<>();


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductsList.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsList newInstance(String param1, String param2) {
        ProductsList fragment = new ProductsList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ProductsList() {
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
        View rootView = inflater.inflate(R.layout.fragment_products_list, container, false);

        menu_ccl_tab = (TextView)rootView.findViewById(R.id.menu_ccl_tab);
        nearby_ccl_tab = (TextView)rootView.findViewById(R.id.nearby_ccl_tab);
        novelty_ccl_tab = (TextView)rootView.findViewById(R.id.novelty_ccl_tab);
        favor_ccl_tab = (TextView)rootView.findViewById(R.id.favor_ccl_tab);
        reservation_ccl_tab = (TextView)rootView.findViewById(R.id.reservation_ccl_tab);
        categories_ccl_tab = (TextView)rootView.findViewById(R.id.categories_ccl_tab);
        menu_ccl_tab.setOnClickListener(this);
        nearby_ccl_tab.setOnClickListener(this);
        novelty_ccl_tab.setOnClickListener(this);
        favor_ccl_tab.setOnClickListener(this);
        reservation_ccl_tab.setOnClickListener(this);
        categories_ccl_tab.setOnClickListener(this);
//        search_view_ccl.setOnClickListener(this);

        listView = (ListView) rootView.findViewById(R.id.listView);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setVisibility(View.GONE);
        listViewCard = (ListView)rootView.findViewById(R.id.listViewCard);
        listViewCard.setVisibility(View.GONE);

        ImageView imageCard = (ImageView)rootView.findViewById(R.id.imageCard);
        ImageView imageGrid = (ImageView)rootView.findViewById(R.id.imageGrid);
        ImageView imageList = (ImageView)rootView.findViewById(R.id.imageList);
        imageCard.setOnClickListener(this);
        imageGrid.setOnClickListener(this);
        imageList.setOnClickListener(this);

         spinner = (Spinner) rootView.findViewById(R.id.spinnerFolter);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.category_spinner_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);



        new GetCategories().execute();

        this.gridView = gridView;
        this.listView = listView;

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }




    public class GetCategories extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Загрузка товаров.......");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(url, ServiceHandler.GET);

            Log.e("Response: ", "> " + json);
            if (json != null) {
                JSONObject jsonResponse = null;
                try {


                    jsonResponse = new JSONObject(json);
                    JSONArray jsonMainNode = jsonResponse.optJSONArray("tshirt");
                    List = new ArrayList<>();
                    for (int i = 0; i < jsonMainNode.length(); i++) {

                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                        Goods goods = new Goods();

                        goods.setTitle(jsonChildNode.getString("title"));


                        goods.setPrice(jsonChildNode.getString("price"));

                        goods.setLastPrice(jsonChildNode.getString("lastPrice"));

                        goods.setDiscount(jsonChildNode.getString("discount"));

                        goods.setAction(jsonChildNode.getString("action"));

                        goods.setFreeDelivering(jsonChildNode.getString("delivery"));

                        goods.setTime(jsonChildNode.getString("time"));

                        goods.setOrdering(jsonChildNode.getString("ordering"));

                        goods.setNumOfSelled(jsonChildNode.getString("selled"));

                        image = jsonChildNode.getString("image");

                        byte[] rawImage = Base64.decode(image, Base64.DEFAULT);
                        bmp = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length);
                        //  bmp.compress(Bitmap.CompressFormat.PNG,100);
                        //   im.add(bmp);
                        goods.setImageIcon(bmp);
                        List.add(goods);


                      //  System.out.println("tttttttttttttttttttttttttttttttttttttt" + jsonMainNode.getJSONObject(i).toString());
                       // System.out.println("Value of length" + jsonMainNode.getJSONObject(i).length());
                    }
                } catch (JSONException e) {
                    Log.e("log_tag", "Error parsing data " + e.toString());
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            //populateSpinner();
            getAdapters();

        }

    }





    public void getAdapters(){

        listData = List;
        if (listData !=null){
            adapter = new SubCategoriesAdapter(getActivity(),listData);
            listView.setAdapter(adapter);
           adapterGrid = new SubCategoriesAdapterGrid(getActivity(),listData);
            gridView.setAdapter(adapterGrid);
            adapterCard = new SubCategoriesAdapterCard(getActivity(),listData);
           listViewCard.setAdapter(adapterCard);
        }

    }




















    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageCard:
                gridView.setVisibility(v.GONE);
                listView.setVisibility(v.GONE);
                listViewCard.setVisibility(View.VISIBLE);
                break;
            case R.id.imageGrid:
                gridView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                listViewCard.setVisibility(View.GONE);
                gridView.setNumColumns(GridView.AUTO_FIT);
                break;
            case R.id.imageList:
                gridView.setVisibility(View.GONE);
                listViewCard.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                break;


            case R.id.menu_ccl_tab:
                fragment = new MenuItems();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.nearby_ccl_tab:
                //  fragment = new Nearby();
                //  Defaults.replaceFragment(fragment, getActivity());
                Uri gmmIntentUri =  Uri.parse("geo:0,0?q=a+Grozniy");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                break;
            case R.id.novelty_ccl_tab:
                fragment = new Novelty();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.favor_ccl_tab:
                fragment = new Favourites();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.reservation_ccl_tab:
                fragment = new Reservation();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            case R.id.categories_ccl_tab:
                fragment = new Categories();
                Defaults.replaceFragment(fragment, getActivity());
                break;
            // case R.id.searchViewCCL:
            // search_view_ccl.onActionViewExpanded();
            //  break;
            default:
                break;

        }
    }
/*
   public ArrayList<Goods> getData() {
        ArrayList<Goods> List = new ArrayList<>();

        Goods good1 = new Goods();
        good1.setTitle("Футболки");
        good1.setPrice("33$");
        good1.setLastPrice("40$");
        good1.setDiscount("-30%");
        good1.setAction("Акция!");
        good1.setFreeDelivering("Бесплатная доставка");
        good1.setTime("Время");
        good1.setOrdering("30 Бронь");
        good1.setNumOfSelled("115 продано");
        good1.setImageIcon(R.drawable.ic_tshirt);
        List.add(good1);

        Goods good2 = new Goods();
        good2.setTitle("Футболки");
        good2.setPrice("3000$");
        good2.setLastPrice("500000$");
        good2.setDiscount("-30%");
        good2.setAction("Акция!");
        good2.setFreeDelivering("Бесплатная доставка");
        good2.setTime("Время");
        good2.setOrdering("30 Бронь");
        good2.setNumOfSelled("50 продано");
        good2.setImageIcon(R.drawable.ic_tshirt);
        List.add(good2);

        Goods good3 = new Goods();
        good3.setTitle("Футболки");
        good3.setPrice("33$");
        good3.setLastPrice("40$");
        good3.setDiscount("-30%");
        good3.setAction("Акция!");
        good3.setFreeDelivering("Бесплатная доставка");
        good3.setTime("Время");
        good3.setOrdering("30 Бронь");
        good3.setNumOfSelled("115 продано");
        good3.setImageIcon(R.drawable.ic_tshirt);
        List.add(good3);


        Goods good4 = new Goods();
        good4.setTitle("Футболки");
        good4.setPrice("33$");
        good4.setLastPrice("40$");
        good4.setDiscount("-30%");
        good4.setAction("Акция!");
        good4.setFreeDelivering("Бесплатная доставка");
        good4.setTime("Время");
        good4.setOrdering("30 Бронь");
        good4.setNumOfSelled("115 продано");
        good4.setImageIcon(R.drawable.ic_tshirt);
        List.add(good4);


        return List;
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



    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {


        parent.getItemAtPosition(pos);
        switch (pos) {
            case 1:
                adapter.sortByPrice();
                adapterGrid.sortByPrice();
//adapter.sortList(-1);

                adapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
                adapterGrid.notifyDataSetChanged();
                break;

            case 2:
                //  adapter.sortList(1);
                adapter.reverceSsortByPrice();
                adapterGrid.reverceSsortByPrice();
                adapter.notifyDataSetChanged();
                adapterGrid.notifyDataSetChanged();
                break;
            case 3:
                adapter.sortOrdering();
                adapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
