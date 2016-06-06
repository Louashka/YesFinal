package com.tataev.appyes.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.tataev.appyes.AppConfig;
import com.tataev.appyes.Defaults;
import com.tataev.appyes.MainActivity;
import com.tataev.appyes.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoriesChildList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoriesChildList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesChildList extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView menu_ccl_tab;
    private TextView nearby_ccl_tab;
    private TextView novelty_ccl_tab;
    private TextView favor_ccl_tab;
    private TextView reservation_ccl_tab;
    private TextView categories_ccl_tab;
    private Fragment fragment;
    private SearchView search_view_ccl;
    private ListView listViewCCL;
    private int groupPosition;
    private int childPosition;
    private String[] listItems;
    private int[][] itemsArray = new int[][]{
            {R.array.upForManArray, R.array.bottomForMan, R.array.jacetsAndCoatsArray, R.array.suitAndBlazerArray,
            R.array.sportWearArray, R.array.indoorClothingArray, R.array.tranksAndShortArray, R.array.accessoriesForManArray},
            {R.array.popularForWomanArray, R.array.bottomForWomanArray, R.array.topForWomanArray, R.array.weddingDressArray,
            R.array.womanUnderClothArrray, R.array.indoorClothingArray, R.array.womanSportWearArray, R.array.pregnantWearArray, R.array.womanAccessoryArray},
            {R.array.babiesGoodArray, R.array.girlieGoodsArray, R.array.girlGoodsArray,R.array.boyGoodsArray,
            R.array.juniorGoodsArray,R.array.schoolGoodArray,R.array.childAccessoriesArray},{R.array.mobilesArray,R.array.accessoriesForIphoneArray,
    R.array.mobilesGadgetArray,R.array.wiredPhonesArray,R.array.mobilesAccessoriesArray,R.array.mobileComponentsArray},
            {R.array.photoAndCameraArray,R.array.tvSetArray,R.array.videoAndAudioArray,R.array.walkManAndHeadphoneArray,
            R.array.gamesSoftArray,R.array.cleverElectronicsArraay,R.array.elecBooksArray,R.array.carsElectronicsArray,R.array.detailsAndAccessoriesArray },
            {R.array.electricInstrumenentsArray,R.array.handInstrumentsArray,R.array.remountInstrumentsArray,R.array.securityArray,
            R.array.electricEquipmentArray,R.array.accessoriesForHouseGardenArray},{R.array.gardenAndPlantsArray,R.array.gardenTechnicsArray,
    R.array.irrigationArray,R.array.allforGardenArray,R.array.kempingArray,R.array.goodsForRestPicnicArray,R.array.saunaAndBathroomArray,
    R.array.furnitureAndDecorArray,R.array.childrenPlaceArray,R.array.gardenAccessoriesArray},{R.array.fridgeArray,
    R.array.largeKitchenTechArray,R.array.smallCommonTechArray,R.array.dishesArray,R.array.kitchenAccessoriesArray },{R.array.washAnddryArray,
    R.array.smallCommonMachenesArray,R.array.floorEquipmentArray,R.array.climaticTechArray,R.array.accessoriesForCommonTechArray,
    R.array.boilersArray,R.array.gearsForRemountArray,R.array.illuminateArray,R.array.clockArray} };

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
     * @return A new instance of fragment CategoriesChildList.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesChildList newInstance(String param1, String param2) {
        CategoriesChildList fragment = new CategoriesChildList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CategoriesChildList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            groupPosition = getArguments().getInt("groupPosition");
            childPosition = getArguments().getInt("childPosition");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_categories_child_list, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Категории");

      //  Defaults.setSearchViewStyle(R.id.searchViewCCL, rootView, getActivity());

        //Initialize tab menu icons
        menu_ccl_tab = (TextView)rootView.findViewById(R.id.menu_ccl_tab);
        nearby_ccl_tab = (TextView)rootView.findViewById(R.id.nearby_ccl_tab);
        novelty_ccl_tab = (TextView)rootView.findViewById(R.id.novelty_ccl_tab);
        favor_ccl_tab = (TextView)rootView.findViewById(R.id.favor_ccl_tab);
        reservation_ccl_tab = (TextView)rootView.findViewById(R.id.reservation_ccl_tab);
        categories_ccl_tab = (TextView)rootView.findViewById(R.id.categories_ccl_tab);
     //   search_view_ccl = (SearchView)rootView.findViewById(R.id.searchViewCCL);
        listViewCCL = (ListView)rootView.findViewById(R.id.listViewCCL);

        try {
            listItems = getResources().getStringArray(itemsArray[groupPosition][childPosition]);
            listViewCCL.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItems));
            listViewCCL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (groupPosition ==0 && childPosition ==0){
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.url);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.poloUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.vestUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.shirtUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.pulloverUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.underClothUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }
                    }
                    if (groupPosition ==0 && childPosition ==1){
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.trousersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.jeansUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.pantsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.bottomUnderClothUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.clothFrorBathUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.manSocksUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.hotClothUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;



                        }

                    }

                    if (groupPosition ==0 && childPosition ==2) {
                       switch (position){
                           case 0:
                               fragment = new ProductsList(AppConfig.mansJacketUrl);
                               Defaults.replaceFragment(fragment, getActivity());
                               break;
                           case 1:
                               fragment = new ProductsList(AppConfig.manscoatsUrl);
                               Defaults.replaceFragment(fragment, getActivity());
                               break;
                           case 2:
                               fragment = new ProductsList(AppConfig.mansraincoatsUrl);
                               Defaults.replaceFragment(fragment, getActivity());
                               break;
                           case 3:
                               fragment = new ProductsList(AppConfig.manstolstovkiUrl);
                               Defaults.replaceFragment(fragment, getActivity());
                               break;
                           case 4:
                               fragment = new ProductsList(AppConfig.mansjeansjacketsUrl);
                               Defaults.replaceFragment(fragment, getActivity());
                               break;
                           case 5:
                               fragment = new ProductsList(AppConfig.mansportjacketsUrl);
                               Defaults.replaceFragment(fragment, getActivity());
                               break;
                           case 6:
                               fragment = new ProductsList(AppConfig.manvetrovkiUrl);
                               Defaults.replaceFragment(fragment, getActivity());
                               break;
                           case 7:
                               fragment = new ProductsList(AppConfig.manleatherjacketsUrl);
                               Defaults.replaceFragment(fragment, getActivity());
                               break;
                           case 8:
                               fragment = new ProductsList(AppConfig.manpuhovikiparkiUrl);
                               Defaults.replaceFragment(fragment, getActivity());
                               break;
                           case 9:
                               fragment = new ProductsList(AppConfig.manjiletUrl);
                               Defaults.replaceFragment(fragment, getActivity());
                               break;

                       }

                    }
                    if (groupPosition ==0 && childPosition ==3) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.manpidjakUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.mankostyumiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                        }

                    }
                    if (groupPosition ==0 && childPosition ==4) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.mansportcollarlessjacketsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.mansporttrousersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.mansportcowljacketUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.mansportwearUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.mansportwoollentrouserUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }

                    }
                    if (groupPosition ==0 && childPosition ==5) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.manhomegownUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.manhometrousersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.manhomepijamasUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                        }

                    }
                    if (groupPosition ==0 && childPosition ==6) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.manswimtrunksUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.manswimtrousersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.manswimpijamasUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                        }

                    }
                    if (groupPosition ==0 && childPosition ==7) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.manheadwearUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.manscarfUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.manglovesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.manbeltsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.manbagsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.mantiesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.manjewelriesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.manglassesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.manwatchesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.manbraceletUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;



                        }


                    }


                    if (groupPosition == 1 && childPosition == 0){
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.popularGoogWomUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.bluzkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.kardigansUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.tshirtLadyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.vestForladyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.kombinezoniUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.bleizerForLadiesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                        }

                    }
                    if (groupPosition == 1 && childPosition == 1) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.jubsForLadyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.pantsForLadyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.jeansForLAdyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.trouserForLadyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.leginsforladyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                        }
                    }

//               upWear   for woman
                    if (groupPosition == 1 && childPosition == 2) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.coatsDressUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.vetrovkiladyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.plashitrenchkotiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.warmjacketsladyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.parkiLadyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.trenchiforladyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.sportjacketsladyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.jeansjacketsladyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.jeletsforladyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.tostovkiforladyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;





                        }


                    }

                    if (groupPosition == 1 && childPosition == 3) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.marrigeDressUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.eveningDressUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.formalDressUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.frendygirllDressUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.coctelDressUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;



                        }




                    }
                    if (groupPosition == 1 && childPosition == 4) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.brassiereUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.neglijeUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.ladyswimsuitUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.ladykorsajimaikibodiniUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.ladykomplektibelyaUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.ladykorektirubelyoUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.ladychulkinoskiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.ladyaccsessoriesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }


                    }

                    if (groupPosition == 1 && childPosition == 5) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.ladyhalatiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.ladyhometrousersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.ladyhomepijamasUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }

                    }

                    if (groupPosition == 1 && childPosition == 6) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.ladysportjacketsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.ladysporttrousersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.ladysportswearUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.ladysporttshirtUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.ladysportshoesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 5:
                                fragment = new ProductsList(AppConfig.ladysporthomewearUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;


                        }

                    }

                    if (groupPosition == 1 && childPosition == 7) {}

                    if (groupPosition == 1 && childPosition == 8) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.ladyheadwearUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.ladyscarfglovesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.ladybeltsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.ladysunglassUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.ladybeisbolkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }

                    }

                    if (groupPosition == 2 && childPosition == 0) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.babyunderclothUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.babybodypesochnikiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.babytrousersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.babyupclothUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.babykombinezoniUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.babykonvertiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.babykomplectiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.babykostyumiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.babykoftochkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.babykoftiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 10:
                                fragment = new ProductsList(AppConfig.babynoskikolgotkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 11:
                                fragment = new ProductsList(AppConfig.babyhomeclothUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 12:
                                fragment = new ProductsList(AppConfig.babyodejdakreshenieUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 13:
                                fragment = new ProductsList(AppConfig.babydressjubkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 14:
                                fragment = new ProductsList(AppConfig.babypolzunkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 15:
                                fragment = new ProductsList(AppConfig.babytshirtsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 16:
                                fragment = new ProductsList(AppConfig.babyshortiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 17:
                                fragment = new ProductsList(AppConfig.babyshoesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                        }


                    }


                    if (groupPosition == 2 && childPosition == 1) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.girlieunderclothsocksUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.girlieblizkitunikiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.girlietrouserspantsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.girliecoatsjacketsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.girlievodolazkilongsliviUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.girliejeansUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.girliekombinezoniUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.girliekostyumkomplektiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.girliekoftijaketiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.girliekupalnikiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 10:
                                fragment = new ProductsList(AppConfig.girlieclothforhomeUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 11:
                                fragment = new ProductsList(AppConfig.girliedressUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 12:
                                fragment = new ProductsList(AppConfig.girliepulloverdjemperiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 13:
                                fragment = new ProductsList(AppConfig. girliesvitshotitolstUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 14:
                                fragment = new ProductsList(AppConfig.girlietopiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 15:
                                fragment = new ProductsList(AppConfig.girlietshirtsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 16:
                                fragment = new ProductsList(AppConfig.girliebermudishortiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 17:
                                fragment = new ProductsList(AppConfig.girliejubkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 18:
                                fragment = new ProductsList(AppConfig.girlieshoesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                        }


                    }


                    if (groupPosition == 2 && childPosition == 2) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.girlunderwearclothforhomeUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.girlbluzkitunikiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.girltrouserspantsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.girlcoatsjacketsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.girljeansUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.girlkoftiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.girldressUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.girlpulloversviterUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.girltshirttopiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.girljubkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 10:
                                fragment = new ProductsList(AppConfig.girlboleroUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 11:
                                fragment = new ProductsList(AppConfig.girlshoesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }


                    }




                    if (groupPosition == 2 && childPosition == 3) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.boyunderwearUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.boytrousersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.boycotsjacketsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.boyvodolazkilongsliviUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.boyjeansUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.boyjiletiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.boykarnavalkostyumUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.boykostumjiletkombinezUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.boykoftiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.boynoskikolgotkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 10:
                                fragment = new ProductsList(AppConfig.boydressforhomeUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 11:
                                fragment = new ProductsList(AppConfig.boyplavkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 12:
                                fragment = new ProductsList(AppConfig.boypullovesjempersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 13:
                                fragment = new ProductsList(AppConfig.boyrubashkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 14:
                                fragment = new ProductsList(AppConfig.boysvishotitolstovkUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 15:
                                fragment = new ProductsList(AppConfig.boytshirtsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 16:
                                fragment = new ProductsList(AppConfig.boyshortibermudiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 17:
                                fragment = new ProductsList(AppConfig.boyshoesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }


                    }


                    if (groupPosition == 2 && childPosition == 4) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.juniorclothforhomeUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.juniortrouserspantsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 2:
                                fragment = new ProductsList(AppConfig.juniorupwearcoatsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.juniorjeansUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.juniorkostumjiletsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.juniorkoftiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.juniorpulloverswiterUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.juniorrubashkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.juniortshirtsmaikiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.juniornaborikomplektiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 10:
                                fragment = new ProductsList(AppConfig.juniorheadwearUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 11:
                                fragment = new ProductsList(AppConfig.juniorshoesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;



                        }


                    }

                    if (groupPosition == 2 && childPosition == 5) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.schoolshoesforgirlsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.schoolshoesforboyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 2:
                                fragment = new ProductsList(AppConfig. schoolclothforgirlUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.schoolclothforboyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.schoolsportUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.schoolrucksackUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.schoolaccessoriesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;




                        }


                    }


                    if (groupPosition == 2 && childPosition == 6) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.childaccessoriforglassUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.childaccessorforbagsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 2:
                                fragment = new ProductsList(AppConfig.childaccessorbeltsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.childaccessorpocketsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.childaccessorforshoesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;





                        }


                    }
                    if (groupPosition == 3 && childPosition == 0) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.mobileacerUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.mobilealcatelUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 2:
                                fragment = new ProductsList(AppConfig.mobileasusUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.mobileblackberryUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.mobilebqUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.mobileexplayUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.mobileflyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.mobilehtcUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.mobilehuaweiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.mobileiphoneUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 10:
                                fragment = new ProductsList(AppConfig.mobilelenevoUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 11:
                                fragment = new ProductsList(AppConfig.mobilelgUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 12:
                                fragment = new ProductsList(AppConfig.mobilekeneksiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 13:
                                fragment = new ProductsList(AppConfig.mobilemeizuUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 14:
                                fragment = new ProductsList(AppConfig.mobileicrosoftUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 15:
                                fragment = new ProductsList(AppConfig.mobilemotorolaUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 16:
                                fragment = new ProductsList(AppConfig.mobilenokiaUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 17:
                                fragment = new ProductsList(AppConfig. mobilenomiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 18:
                                fragment = new ProductsList(AppConfig.mobilepanasonicUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 19:
                                fragment = new ProductsList(AppConfig.mobilephilipsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 20:
                                fragment = new ProductsList(AppConfig.mobilequmoUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 21:
                                fragment = new ProductsList(AppConfig.mobilesamsungUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 22:
                                fragment = new ProductsList(AppConfig.mobilesonyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 23:
                                fragment = new ProductsList(AppConfig.mobilethlUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 24:
                                fragment = new ProductsList(AppConfig.mobilevertuUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 25:
                                fragment = new ProductsList(AppConfig.mobilezteUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 26:
                                fragment = new ProductsList(AppConfig.mobilexiaomiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 27:
                                fragment = new ProductsList(AppConfig. mobileyotaphoneUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 28:
                                fragment = new ProductsList(AppConfig.mobileothersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }


                    }

                    if (groupPosition == 3 && childPosition == 1) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.iphoneaccessorsecurityglassUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.iphoneaccessorkorpusaUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 2:
                                fragment = new ProductsList(AppConfig.iphoneaccessorcarchargersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.iphoneaccessorexternalbatteryUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.iphoneaccessochargersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.iphoneaccessorcablesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.iphoneaccessusbmemoryUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.iphoneaccesadaptersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.iphoneaccesgearsforselfiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.iphoneaccesacumulatorsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 10:
                                fragment = new ProductsList(AppConfig.iphoneaccesholdersincarsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 11:
                                fragment = new ProductsList(AppConfig.iphoneaccesstickersandgiftsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 12:
                                fragment = new ProductsList(AppConfig.iphoneaccesstableholdersmobileUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 13:
                                fragment = new ProductsList(AppConfig. iphoneaccesssuviniriUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 15:
                                fragment = new ProductsList(AppConfig.iphoneaccebrelkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 16:
                                fragment = new ProductsList(AppConfig.iphoneaccsredstvauhodaUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 17:
                                fragment = new ProductsList(AppConfig.iphoneaccesschehliUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }

                    }


                    if (groupPosition == 3 && childPosition == 2) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.gadgetscleverwatchUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.gadgetscleverbrasletsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 2:
                                fragment = new ProductsList(AppConfig.gadgetsfitnestrekerUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.gadgetsaccessoriesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }


                    }

                    if (groupPosition == 3 && childPosition == 3) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.stationphoneofficeUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.stationphonehomeUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 2:
                                fragment = new ProductsList(AppConfig.stationphoneradiophoneUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.stationphoneradiostationUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }


                    }

                    if (groupPosition == 3 && childPosition == 4) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.mobilaccessprotectglassUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.mobilaccesskorpusaUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 2:
                                fragment = new ProductsList(AppConfig.mobilaccesscarchargerformobileUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.mobilaccessexternalacumalatorUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.mobilaccesschargersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.mobilaccesscablesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.mobilaccessflashmemoriesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.mobilaccesscardreadersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.mobilaccessusbmemoryUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.mobilaccesdocstationsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 10:
                                fragment = new ProductsList(AppConfig.mobilaccesstilusiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 11:
                                fragment = new ProductsList(AppConfig.mobilaccessadaptersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 12:
                                fragment = new ProductsList(AppConfig.mobilaccessselfholdersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 13:
                                fragment = new ProductsList(AppConfig.mobilaccesssbatteryUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 15:
                                fragment = new ProductsList(AppConfig.mobilaccesssboosterantennaUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 16:
                                fragment = new ProductsList(AppConfig.mobilaccessmobilholderforcarUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 17:
                                fragment = new ProductsList(AppConfig.mobilaccessstickersgiftsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 18:
                                fragment = new ProductsList(AppConfig.mobilaccesstableholdersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 19:
                                fragment = new ProductsList(AppConfig.mobilaccessfmtransmitterUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 20:
                                fragment = new ProductsList(AppConfig.mobilaccesssuviniriUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 21:
                                fragment = new ProductsList(AppConfig.mobilaccessportativacustikaUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 22:
                                fragment = new ProductsList(AppConfig.mobilaccessbrelkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 23:
                                fragment = new ProductsList(AppConfig.mobilaccesshabiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 24:
                                fragment = new ProductsList(AppConfig.mobilaccesssredstvadlyauhodaUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 25:
                                fragment = new ProductsList(AppConfig.mobilaccesssnaushnikiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 26:
                                fragment = new ProductsList(AppConfig.mobilaccessbloetoothgsrnUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 27:
                                fragment = new ProductsList(AppConfig.mobilaccessbagsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 28:
                                fragment = new ProductsList(AppConfig.mobilaccesschehlibamperiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 29:
                                fragment = new ProductsList(AppConfig.mobilaccesswaterproofcoverUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 30:
                                fragment = new ProductsList(AppConfig.mobilaccessleathercoverUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 31:
                                fragment = new ProductsList(AppConfig.mobilaccessalumincoversUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 32:
                                fragment = new ProductsList(AppConfig.mobilaccescoverswithstrazUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }

                    }

                    if (groupPosition == 3 && childPosition == 5) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.mobileditailsdisplayUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.mobilebatteryUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 2:
                                fragment = new ProductsList(AppConfig.mobiledetailcoversUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.mobiledetailsignalboostersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.mobiledetailothersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }

                    }

                    if (groupPosition == 4 && childPosition == 0) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.electronicminicameraUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.electronicdigitalcameraUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 2:
                                fragment = new ProductsList(AppConfig.electroniccameraphotoaccessoryUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.electronicportotivcameraUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.electronicobjectivesaccessorUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }

                    }
                    if (groupPosition == 4 && childPosition == 1) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.electronicsuhdtvUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.electronicultrahdtvUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 2:
                                fragment = new ProductsList(AppConfig.electroni3dtvUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.electronsmarttvUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.electronicledtvUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.electronicoledtvUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.electrichometvUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.electricsputnicdigitalcabletvUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig. electrictumbikranshteinekraniUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.electricaccessorfortvUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                        }

                    }

                    if (groupPosition == 4 && childPosition == 2) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.videoaudioprojectaccessoryUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.videoaudiovideoaudioaccessUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 2:
                                fragment = new ProductsList(AppConfig.videoaudiokolonkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 3:
                                fragment = new ProductsList(AppConfig.videoaudiotvcardsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.videoaudiosoundbariUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.videoaudiodvdbluerayvidUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.videoaudiohifiapparatUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.videoaudiomusiccentersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.videoaudioprtativaudioUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 10:
                                fragment = new ProductsList(AppConfig.videoaudiomusicinstrumentsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 11:
                                fragment = new ProductsList(AppConfig.videoaudioaccessorforaudiovideoUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                        }

                    }


                    if (groupPosition == 4 && childPosition == 3) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.playerheadphmp3playersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.playerheadphmp4playersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 2:
                                fragment = new ProductsList(AppConfig.playerheadradiopriemnikUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }

                    }





                    if (groupPosition == 4 && childPosition == 4) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.gamesoftportativideodevicesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.gamesoftgamedevicesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.gamesoftkolonkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.gamesoftaccforgamedevicesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.gamesofkinoUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.gamesoftbooksUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.gamesoftmusicalinstrumentsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.gamesoftradiotoiesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;


                        }

                    }



                    if (groupPosition == 4 && childPosition == 5) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.cleverwatchUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.cleverbrsletsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.cleverremoteconrollerUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.clevercarryelectronicsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.cleverforhomeUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.cleverforsportUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;


                        }

                    }

                    if (groupPosition == 4 && childPosition == 6) {

                    }


                    if (groupPosition == 4 && childPosition == 7) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.carelectronicautomagnitolUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.carelectronicvideoregistratorUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.carelectronradardetectorUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.carelectrongpsnavigatorUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.carelectronminimoikiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.carelectrovacumecleanerUrl );
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.carelectronaccessorforauototechUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                        }

                    }

                    if (groupPosition == 4 && childPosition == 8) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.accessoriedetailskolonkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.accessoriedetailsmeorycardsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.accessoriedetailaccumulatorUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.accessoriedetailbagsforelectronUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.accessoriedetaildigitalcablesUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.accessoriedetailcoverformobileUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }

                    }

                    if (groupPosition == 5 && childPosition == 0) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.electroinstrumdreliperifershurUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.electroinstrumlobzilpilirubUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.electroinstrumentaccumulotvertkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.electroinstrumentmultifunctionistrumUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.electroinstrumekraskopeltiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.electroinstrumnaborielektrinstrumentUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }

                    }

                    if (groupPosition == 5 && childPosition == 1) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.handinstrumotvertkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.handinstrumsharnirgubcinstrumUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.handinstrumkeysUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.handsinstrumnaboriruchinstrumUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;



                        }

                    }


                    if (groupPosition == 5 && childPosition == 2) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.materialforremountotdelmaterUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.materialforfloorcoverUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.materialforbuildmaterialsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.materialforbuildlakikraskiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.materialforelementkrepejfurnitUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                        }

                    }

                    if (groupPosition == 5 && childPosition == 3) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.radionyaniUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.securityprotectcameravideoUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.securityprotectmagnitafvideonablUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.sistemivideonablyudUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.spaccessorfovideoUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.spdatchikisignalizUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.spsecurityworkplaceUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.spavarinienaboriUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.spsefiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.spfiresecurityUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                        }

                    }
                    if (groupPosition == 5 && childPosition == 4) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.ghelinstsvarkaoborUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.ghelinstelectrogeneratorUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.ghelinststabiliznaryajUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.ghelinststelectroudlenitperehodnUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.ghelinstavtotransformatorUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.ghelinstaccumulabattaeyUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.ghelinstwikluchatelpereklyuchaUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.ghelinstrozetkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.ghelinsttransformatoriUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.ghelinsttokizmeriteliUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 10:
                                fragment = new ProductsList(AppConfig.ghelinstelektromontajUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 11:
                                fragment = new ProductsList(AppConfig.ghelinstradiodetalielkomponUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 12:
                                fragment = new ProductsList(AppConfig.ghelinstupravlenosveshenUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 13:
                                fragment = new ProductsList(AppConfig.ghelinstmetaloiskdetectprovodkUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;


                        }

                    }

                    if (groupPosition == 5 && childPosition == 5) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.afhgaccforminiwashersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.afhgaccfoeinflatifurnitureUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.afhgaccforbiotualetcepticUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.afhgaccforremonttechUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.afhgaccforpecheikaminovUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.afhgaccforgardentechUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.afhgaccforbabekyuUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.afhgaccforpicnicUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.afhgaccforswimmingUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.afhgaccforbasseinovUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 10:
                                fragment = new ProductsList(AppConfig.afhgaccforminimoekUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 11:
                                fragment = new ProductsList(AppConfig.afhgaccforinflatefurnitureUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }

                    }
                    if (groupPosition == 6 && childPosition == 6) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.ggtepliciparnikiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.gggardeninstrumentsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.ggallforwateringUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.gggardeninventarUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.ggseedUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.gggazoniUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.ggposadochnimaterUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.ggudobreniyaUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.ggotpugivateliUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;


                        }

                    }


                    if (groupPosition == 6 && childPosition == 1) {
                        switch (position) {
                            case 0:
                                fragment = new ProductsList(AppConfig.gtechtrmmersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.gtechgazonokosilkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.gtechcepniepiliUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.gtechkustoreziUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.gtechgardencleanerUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.gtechkeltivatoriUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.gtechismelchiteliUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.gtechsnowcleanerUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.gtechwasherhighpressUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.gtechgazonokosilkitrimmerUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 10:
                                fragment = new ProductsList(AppConfig.gtechmotoblokikultivatoriUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 11:
                                fragment = new ProductsList(AppConfig.gtechminitraktorsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 12:
                                fragment = new ProductsList(AppConfig.gtechwodanasosUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 13:
                                fragment = new ProductsList(AppConfig.gtechvertikutteriUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 14:
                                fragment = new ProductsList(AppConfig.gtechwozduhoduvkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 15:
                                fragment = new ProductsList(AppConfig.gtechizmelchitmusoraUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 16:
                                fragment = new ProductsList(AppConfig.gtechosnastkaksadovtehmiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 17:
                                fragment = new ProductsList(AppConfig.gtechbenzopiliUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;


                        }


                    }
                    if (groupPosition == 6 && childPosition == 2) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.spirrigatpumpersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.spirrigatmotopompiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.spirrigaterainersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.spirrigatesprinklersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.spirrigatpistoletipilivUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.spirrigatsistemapolivaUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.spirrigatsadovieshlangiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.spirrigatconnectorUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.spirrigatekatushkitelejkishlangiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.spirrigatebakivodaleikiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;


                        }

                    }

                    if (groupPosition == 6 && childPosition == 3) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.allgardensadoviinventaUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.allgardenprotectgardenUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.allgardenparnikitepliciUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.allgardenrassadaUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.allgardenuhodzarasteniUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.allgardensadovidecorUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;



                        }

                    }

                    if (groupPosition == 6 && childPosition == 4) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.kempingbiotualetiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.kempingnaduvkrovatiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.kempingbasseiniUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.kempingmangalibarbekUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;



                        }

                    }
                    if (groupPosition == 6 && childPosition == 5) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.goodfrestpicnicgardenfurnitureUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.goodfrestpicnicgrilimangalshahslUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.goodfrestpicnicaccessorforpicUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.goodfrestpicnictentiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.goodfrestpicbadmintonUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.goodfrestpicnastolnitennisUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.goodfrestpicmonocikliUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;



                        }

                    }

                    if (groupPosition == 6 && childPosition == 6) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.saunabathroomUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.saunabathroominfrakrassaunUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.saunabathroompechibanUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.saunabathroomdimohodiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.saunabathroomdooarsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.saunabathroombochkikupeliUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.saunabathroomparogeneratoriUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.saunabathroomkamnipecheiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;


                        }

                    }
                    if (groupPosition == 6 && childPosition == 7) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.furnituredecorgardenfurnitureUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.furnituredecorpletenamebelUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.furnituredecorulichniesvetilUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.furnituredecorsadovidecorUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;


                        }

                    }
                    if (groupPosition == 6 && childPosition == 8) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.chplplayhtentUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.chplplaycomlekgorkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.chplkacheliUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.chplpesochniciaccessuaUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.chplbatutiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.chplnaduwniebasseinUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.chplallgoodsUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;


                        }

                    }
                    if (groupPosition == 6 && childPosition == 9) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.accessorforgardenaccminimoikiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.accessorforgardenaccnaduwmebelUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.accessorforgardenaccbiotualeticeptikUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.accessorforgardaccsadovtechUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.accessorforgardaccbarbequUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.accessorforgardaccpicnicUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;



                        }

                    }
                    if (groupPosition == 7 && childPosition == 0) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.kitchenfridgeUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.kitchenfridgehighiceboxUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.kitchenfridgelowiceboxUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.kitchenfridgeonedoorUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.kitchenfridgmultidoorUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.kitchenfridgesidebysideUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.kitchenfridgicecameraUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.kitchenfridgiselariUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.kitchenfridgvinnieUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 9:
                                fragment = new ProductsList(AppConfig.kitchenfridgportativnieUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;



                        }

                    }


                    if (groupPosition == 7 && childPosition == 1) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.kitchlargtechelectrplitUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.kitchlargtechgazplitUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.kitchlargtchmicrowolnpechUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.kitchlargtchplitnastolnUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.kitchlargtchvstrposudmashUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.kitchlargtchvstrelecplitUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.kitchlargtchvstrgazplitUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.kitchlargtchposudmashUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.kitchlargtchwityajkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.kitchlargtchkitchenmashUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 10:
                                fragment = new ProductsList(AppConfig.kitchlargtchmyasorubkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 11:
                                fragment = new ProductsList(AppConfig.kitchlargtchwafelniciUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 12:
                                fragment = new ProductsList(AppConfig.kitchlargtchhlebopechiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;


                        }

                    }
                    if (groupPosition == 7 && childPosition == 2) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.kitchsmallcofemashUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.kitchsmallcofewarkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.kitchsmallcofemolkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.kitchsmallmixerUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.kitchsmalkitchencombineUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.kitchsmalblenderUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.kitchsmalmyasorubkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.kitchsmalsokowijimUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.kitchsmalelekchainUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.kitchsmalmultiwarkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 10:
                                fragment = new ProductsList(AppConfig.kitchsmalminipechiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 11:
                                fragment = new ProductsList(AppConfig.kitchsmalparovarkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 12:
                                fragment = new ProductsList(AppConfig.kitchsmallelecaerogrilUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 13:
                                fragment = new ProductsList(AppConfig.kitchsmalltermopotiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 14:
                                fragment = new ProductsList(AppConfig.kitchsmallizmelchitelUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 15:
                                fragment = new ProductsList(AppConfig.kitchsmalltosteriUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 16:
                                fragment = new ProductsList(AppConfig.kitchsmallsifoniUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;


                        }

                    }
                    if (groupPosition == 7 && childPosition == 3) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.kitchdeshesnaborposudUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.kitchdeshesskovorodaUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.kitchdeshekastryulUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.kitchdeshekowshiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.kitchdeshekonteinproduktUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }

                    }

                    if (groupPosition == 7 && childPosition == 4) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.kitchaccesskuhprinadlUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.kitchaccessmicrowavUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.kitchaccesskuhonplitUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.kitchaccessizotermichtowariUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.kitchaccessnasadkinaboriUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.kitchaccesscleanersUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.kitchaccessknivescomplecUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.kitchaccesswesikuhonUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.kitchaccessforfridgeUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.kitchaccesswityajkiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 10:
                                fragment = new ProductsList(AppConfig.kitchaccessposudmoikmashinUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 11:
                                fragment = new ProductsList(AppConfig.kitchaccessfltrivodaUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;




                        }

                    }



                    if (groupPosition == 8 && childPosition == 0) {
                        switch (position) {
                            case 0:
                                fragment = new ProductsList(AppConfig.htwmuskistirmashUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.htwmstarndstirmashUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.htwmstiralmashsushkUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.htwmkimpaktstrimashUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.htwmministirmashUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.htwmkompaksushmashUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                        }
                    }

                    if (groupPosition == 8 && childPosition == 1) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.htshtechutugiparogenUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.htshtechotparivateliUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.htshtecutugiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.htshtechshveinmashUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.htshtechoverlokiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.htshtechaccessshweinmashUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.htshtechcarfridgeUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.htshtechparovieochistUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;


                        }

                    }


                    if (groupPosition == 8 && childPosition == 2) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.htflclpilesospilesborUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.htflclpilesoskonteinpiliUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.htflclpilesosmoyushUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.htflclpilesosrobotUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.htflclpilesosvodyanoifiltorUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.htflclaccumulpilesosUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.htflclcarpilesosUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.htflclstecloochistUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;


                        }

                    }
                    if (groupPosition == 8 && childPosition == 3) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.climtechconditionUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.climtechsplitsystemUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.climtechfloorcondithUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.climtechventilaytorUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.climtechwaterboilerUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.climtechochistwozduhUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.climtechobogpriborUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.climtechmeteostanciiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;


                        }

                    }
                    if (groupPosition == 8 && childPosition == 4) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.accforhomtechpilesosUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.accforhomtechstiralmashUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.accforhomtechutugiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.accforhomtechbattaryaccumUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 4:
                                fragment = new ProductsList(AppConfig.accforhomtechsetevfiltrudlinUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 5:
                                fragment = new ProductsList(AppConfig.accforhomtechprohoztovarUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 6:
                                fragment = new ProductsList(AppConfig.accforhomtechuhodzaveshUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 7:
                                fragment = new ProductsList(AppConfig.accforhomtechgladildoskUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 8:
                                fragment = new ProductsList(AppConfig.accforhomtechglajeniyaUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 9:
                                fragment = new ProductsList(AppConfig.accforhomtechochisuvlajUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 10:
                                fragment = new ProductsList(AppConfig.accforhomtechshveinmashUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 11:
                                fragment = new ProductsList(AppConfig.accforhomtechothergoodshomeUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;



                        }

                    }
                    if (groupPosition == 8 && childPosition == 5) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.waterboilgazovieUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.waterboilelektronUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.waterboilnakopitelUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                            case 3:
                                fragment = new ProductsList(AppConfig.waterboilprotochnieUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                        }

                    }
                    if (groupPosition == 8 && childPosition == 6) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.forremountelektroinstrUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.forremountruchninstrUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.forremountaccessforinstUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }

                    }
                    if (groupPosition == 8 && childPosition == 7) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.forremountlampiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.forremountsvetilnikiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 2:
                                fragment = new ProductsList(AppConfig.forremountsvetilnikielektronsvechiUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;

                        }

                    }
                    if (groupPosition == 8 && childPosition == 8) {
                        switch (position){
                            case 0:
                                fragment = new ProductsList(AppConfig.watchonwallUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                            case 1:
                                fragment = new ProductsList(AppConfig.watchalarmUrl);
                                Defaults.replaceFragment(fragment, getActivity());
                                break;
                        }

                    }
                }
            });
            
        } catch (Exception  e) {
            e.printStackTrace();
            return null;
        }

        // Set OnClickListener to menu icons
        menu_ccl_tab.setOnClickListener(this);
        nearby_ccl_tab.setOnClickListener(this);
        novelty_ccl_tab.setOnClickListener(this);
        favor_ccl_tab.setOnClickListener(this);
        reservation_ccl_tab.setOnClickListener(this);
        categories_ccl_tab.setOnClickListener(this);
//        search_view_ccl.setOnClickListener(this);

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
