package com.example.my.myapplication.IOT;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.ListView;
import android.widget.Toast;

import com.example.my.myapplication.IOT.search.ListViewSearchAdapter;
import com.example.my.myapplication.IOT.server.models.PlaceInfo;
import com.example.my.myapplication.IOT.server.ConfigApp;
import com.example.my.myapplication.IOT.server.models.Device;
import com.example.my.myapplication.IOT.server.service.ServiceDevice;
import com.example.my.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class IOTSearchListFragment extends Fragment {
    private boolean mSearchCheck;
    private View rootView;
    SearchView searchView;
    SharedPreferences sharedPreferences;
    private ServiceDevice serviceDevice;
    ArrayList<Device> listDevice = null;
    // Declare Variables
    ListView list;
    ListViewSearchAdapter adapter;
    Device[] deviceArr = new Device[]{};
    int[] flag = new int[]{};

    Device[] deviceArrT = new Device[]{};
    int[] flagT = new int[]{R.drawable.backgroundweather};

    ArrayList<PlaceInfo> arraylist = new ArrayList<PlaceInfo>();

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
            rootView = inflater.inflate(R.layout.fragment_iotsearch_list, container, false);

        } catch (InflateException e) {
            Log.e("TAG", "Inflate exception");
        }
        if (serviceDevice == null) {
            serviceDevice = new ServiceDevice();
        }
        listDevice = checkSessionListDevice(getContext(), sharedPreferences);
        if (listDevice == null) {
            listDevice = LoginActivity.createSeesionListDevice(getContext(), sharedPreferences, serviceDevice, "");
            Log.d("listcreate", listDevice.get(0).getNameDevice());

        }
        Log.d("listdevice", listDevice.get(0).getNameDevice());

        if (listDevice != null) {


            int len = listDevice.size();
            deviceArr = new Device[len];  flag = new int[len];
            for (int i = 0; i < len; i++) {
                Log.d("namedevice", listDevice.get(0).getNameDevice());

                deviceArr[i] = listDevice.get(i);
                flag[i] = R.drawable.backgroundweather;
            }
        } else {
            deviceArr = deviceArrT;
            flag = flagT;
        }
        list = (ListView) rootView.findViewById(R.id.listviewsearch);

        for (int i = 0; i < deviceArr.length; i++) {
            PlaceInfo wp = new PlaceInfo(deviceArr[i], flag[i], true);
            // Binds all strings into an array
            arraylist.add(wp);
        }

        // Pass results to ListViewAdapter Class
        adapter = new ListViewSearchAdapter(getContext(), arraylist);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        return rootView;
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_search).expandActionView();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu, menu);

        //Select search item
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        menuItem.setVisible(true);

        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(this.getString(R.string.search));

        ((EditText) searchView.findViewById(R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.nliveo_white));

        searchView.setOnQueryTextListener(onQuerySearchView);

        //tat nut add
        menu.findItem(R.id.menu_add).setVisible(false);
        mSearchCheck = false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {

            case R.id.menu_add:
                Toast.makeText(getActivity(), R.string.add, Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_search:
                mSearchCheck = true;
                Toast.makeText(getActivity(), R.string.search_hint, Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            Log.i("submit", s);
            // your search methods
            // adapter.filter(s);
            searchView.clearFocus();

            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            //  if (mSearchCheck) {
            // implement your search here
            Log.i("Change", s);
            adapter.filter(s);
            //}
            return false;
        }
    };

    public static ArrayList<Device> checkSessionListDevice(Context context, SharedPreferences sharedPreferences) {
        ArrayList<Device> arrayList2 = null;
        sharedPreferences = context.getSharedPreferences(ConfigApp.getACCOUNT(), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Device>>() {
        }.getType();

        //c2
        String json2 = sharedPreferences.getString(ConfigApp.getListDevice(), null);
        arrayList2 = gson.fromJson(json2, type);
        try {
            Log.d("list2", arrayList2.get(0).getNameDevice());
        } catch (Exception e) {

        }
        return arrayList2;
    }

}
