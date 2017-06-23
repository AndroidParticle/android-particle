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
import android.widget.ListView;
import android.widget.Toast;

import com.example.my.myapplication.IOT.listDevice.ListViewDeviceAdapter;
import com.example.my.myapplication.IOT.server.ConfigApp;
import com.example.my.myapplication.IOT.server.models.Device;
import com.example.my.myapplication.IOT.server.service.ServiceDevice;
import com.example.my.myapplication.R;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by MY on 6/11/2017.
 */
public class IOTDeviceListFragment extends Fragment {
    private boolean mSearchCheck;

    private ServiceDevice serviceDevice;
    ArrayList<Device> listDevice = null;
    SharedPreferences sharedPreferences;

    private View rootView;
    ListView listAdd;
    ListViewDeviceAdapter adapter;

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
        if (serviceDevice == null) {
            serviceDevice = new ServiceDevice();
        }
        listDevice = IOTSearchListFragment.checkSessionListDevice(getContext(), sharedPreferences);
        if (listDevice == null) {
            listDevice = LoginActivity.createSeesionListDevice(getContext(), sharedPreferences, serviceDevice, "");
            Log.d("listcreate", listDevice.get(0).getNameDevice());

        }
        Log.d("listdevice", listDevice.get(0).getNameDevice());

        listAdd = (ListView) rootView.findViewById(R.id.listviewadd);
        // Pass results to ListViewAdapter Class
        adapter = new ListViewDeviceAdapter(getContext(), listDevice);
        // Binds the Adapter to the ListView
        listAdd.setAdapter(adapter);

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

    // tao session list device
    public static Device createSeesionDeviceInfo(Context applicationContext, SharedPreferences preferences, Device deviceInfo) {
        Device listDevice = null;
        preferences = applicationContext.getSharedPreferences(ConfigApp.getACCOUNT(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(deviceInfo);
        Log.d("deviceInfo", json);
        editor.putString(ConfigApp.getDEVICEINFO(), json);
        editor.commit();

        return listDevice;

    }
}
