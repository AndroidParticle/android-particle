package com.example.my.myapplication.IOT;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my.myapplication.IOT.listPlace.ListViewPlaceAdapter;
import com.example.my.myapplication.IOT.server.ConfigApp;
import com.example.my.myapplication.IOT.server.models.Device;
import com.example.my.myapplication.IOT.server.models.ListPlace;
import com.example.my.myapplication.IOT.server.models.PlaceInfo;
import com.example.my.myapplication.R;
import com.example.my.myapplication.ui.activity.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by MY on 6/11/2017.
 */
public class IOTPlaceListFragment extends Fragment {
    private boolean mSearchCheck;

    // Declare Variables
    TextView txtrank;
    TextView txtcountry;
    TextView txtpopulation;
    ImageView imgflag;
    Device device;
    boolean active;
    int flag;

    ArrayList<PlaceInfo> listPlaceInfos = null;
    SharedPreferences sharedPreferences;
    ListPlace listPlace;

    private View rootView;
    ListView listAdd;
    ListViewPlaceAdapter adapter;

    //
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            rootView = inflater.inflate(R.layout.singleitemview, container, false);

        } catch (InflateException e) {
            Log.e("TAG", "Inflate exception");
        }
        listPlaceInfos = checkSessionListPlace(getContext(), sharedPreferences);
        if (listPlaceInfos == null) {
            ArrayList<PlaceInfo> listPlace = new ArrayList<PlaceInfo>();
            listPlaceInfos = createSeesionListPlace(getContext(), sharedPreferences, listPlace);
            Log.d("listcreatenull", "null");
        }
        Log.d("listPlacename", "null");
        listPlace = new ListPlace(listPlaceInfos);

        try {
            flag = getArguments().getInt("flag", flag);
            device = (Device) getArguments().getSerializable("device");
            active = getArguments().getBoolean("active", active);
            PlaceInfo worldPopulation = new PlaceInfo(device, flag, active);
            // PlaceInfo worldPopulation = new PlaceInfo(rank, country, population, flag);
            listPlace.addPlace(worldPopulation);
            Log.d("addPlace", "null");

        } catch (Exception e) {
            Log.e("TAG", "Inflate exception");
        }

        if (listPlace == null) {
            Log.d("listPlacenull", "null");
            listPlace = new ListPlace();
        }
        ArrayList<PlaceInfo> arraylistAdd = listPlace.getArraylist();
        Log.d("getArraylist", arraylistAdd.size() + "");
        createSeesionListPlace(getContext(), sharedPreferences, arraylistAdd);
        Log.d("showPlace", arraylistAdd.size() + "");

        listAdd = (ListView) rootView.findViewById(R.id.listviewadd);
        // Pass results to ListViewAdapter Class
        adapter = new ListViewPlaceAdapter(getContext(), arraylistAdd);
        // Binds the Adapter to the ListView
        listAdd.setAdapter(adapter);

        return rootView;
    }

    public static ArrayList<PlaceInfo> checkSessionListPlace(Context context, SharedPreferences sharedPreferences) {
        ArrayList<PlaceInfo> arrayList2 = null;
        sharedPreferences = context.getSharedPreferences(ConfigApp.getACCOUNT(), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<PlaceInfo>>() {
        }.getType();
        //c2
        String json2 = sharedPreferences.getString(ConfigApp.getLISTPLACE(), null);
        arrayList2 = gson.fromJson(json2, type);
        try {
            Log.d("listplacecheck", arrayList2.get(0).getDeviceInfo().getNameDevice());
        } catch (Exception e) {

        }
        return arrayList2;
    }

    public static ArrayList<PlaceInfo> createSeesionListPlace(Context applicationContext, SharedPreferences preferences, ArrayList<PlaceInfo> listDeviceAdd) {
        ArrayList<PlaceInfo> listDevice = null;
        preferences = applicationContext.getSharedPreferences(ConfigApp.getACCOUNT(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        try {
            //listDevice = new ArrayList<PlaceInfo>();
            listDevice = listDeviceAdd;
            Gson gson = new Gson();
            String json = gson.toJson(listDevice);
            Log.d("listPlacecreate", json);
            //chuyen cach 2
            editor.putString(ConfigApp.getLISTPLACE(), json);
            editor.commit();

        } catch (Exception e) {
        }
        return listDevice;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu, menu);

        //Select search item
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        menuItem.setVisible(false);//khong hien thi

        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(this.getString(R.string.search));

        ((EditText) searchView.findViewById(R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.nliveo_white));

        searchView.setOnQueryTextListener(onQuerySearchView);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment mFragment = new IOTSearchListFragment();
                FragmentManager manager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.container, mFragment).commit();

            }
        });

        menu.findItem(R.id.menu_add).setVisible(true);
        //tat nut add
        //menu.findItem(R.id.menu_add).setVisible(false);
        mSearchCheck = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {

            case R.id.menu_add:
                Toast.makeText(getActivity(), R.string.add, Toast.LENGTH_SHORT).show();
                Fragment mFragment = new IOTSearchListFragment();
                FragmentManager manager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.container, mFragment).commit();
                break;

            case R.id.menu_search:
                mSearchCheck = true;
                Toast.makeText(getActivity(), R.string.searchgridview, Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            Log.i("submit", s);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            // if (mSearchCheck) {
            // implement your search here
            Log.i("Change", s);

            // }
            return false;
        }
    };

}
