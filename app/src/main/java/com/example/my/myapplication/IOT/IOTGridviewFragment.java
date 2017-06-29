package com.example.my.myapplication.IOT;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.my.myapplication.IOT.gridview.CustomAdapters;
import com.example.my.myapplication.IOT.materialprofile.InfoDeviceFragment;
import com.example.my.myapplication.IOT.server.models.Device;
import com.example.my.myapplication.IOT.server.models.EnviromentCurrent;
import com.example.my.myapplication.IOT.server.service.ServiceEnv;
import com.example.my.myapplication.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class IOTGridviewFragment extends Fragment {//android.support.v4.app.Fragment {
    private View rootView;
    CollapsingToolbarLayout collapsingToolbarLayoutAndroid;
    CoordinatorLayout rootLayoutAndroid;
    private boolean mSearchCheck;

    //v2
    GridView gv;
    public static String[] prgmNameList = {"Nhiệt độ", "Độ ẩm", "Ánh sáng", "Tầm nhìn", "Điểm sương", "Áp suất"};
    public static int[] prgmImages = {
            R.drawable.thermometerlines,
            R.drawable.waterpercent256,
            R.drawable.whitebalancesunny,
            R.drawable.eye,
            R.drawable.water,
            R.drawable.apressure
    };

    private String[] env = {"", "", "", "", "", ""};
    private int[] envCurrent = new int[6];
    ServiceEnv serviceEnv;
    Device deviceInfo;
    private SharedPreferences sharedPreferences;
    private EnviromentCurrent enviromentCurrent;

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
        if (serviceEnv == null) {
            serviceEnv = new ServiceEnv();
        }
        rootView = inflater.inflate(R.layout.gridviewcustom, container, false);

        deviceInfo = InfoDeviceFragment.checkSessionDeviceInfo(getContext(), sharedPreferences);
        //cach 2
        // deviceInfo = (Device) getArguments().getSerializable("device");

        if (deviceInfo == null) {

            InfoDeviceFragment.reloadIOTDeviceListFragmentt(getContext());
            Log.i("deviceInfonull", String.valueOf(deviceInfo));

        } else {
            Log.i("deviceInfodeviceid", String.valueOf(deviceInfo));

            enviromentCurrent = getEnvCurrent(serviceEnv, deviceInfo.getDeviceId());
            if (enviromentCurrent != null) {
                envCurrent = new int[]{
                        enviromentCurrent.getT(), enviromentCurrent.getHi(), enviromentCurrent.getH(), enviromentCurrent.getHeight(), enviromentCurrent.getDp(), enviromentCurrent.getPa()
                };
                env = new String[]{
                        enviromentCurrent.getT() + " 'C", enviromentCurrent.getHi() + "%", enviromentCurrent.getH() + "Lux"
                        , enviromentCurrent.getHeight() + "m", enviromentCurrent.getDp() + "'C", enviromentCurrent.getPa() + "mb"
                };
            }
            gv = (GridView) rootView.findViewById(R.id.gridView12);
            gv.setAdapter(new CustomAdapters(getActivity(), prgmNameList, prgmImages, envCurrent, env));
        }
        return rootView;
    }

    private EnviromentCurrent getEnvCurrent(ServiceEnv serviceEnv, String deviceId) {
        EnviromentCurrent enviromentCurrent = null;


        try {
            enviromentCurrent = serviceEnv.getEnviromentCurrent(deviceId);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return enviromentCurrent;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

    }

    private void initInstances() {
        collapsingToolbarLayoutAndroid = (CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsing_toolbar_android_layoutf);
        collapsingToolbarLayoutAndroid.setTitle("Material Grid");
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu, menu);

        //Select search item
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        menuItem.setVisible(true);

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

        //menu.findItem(R.id.menu_add).setVisible(true);
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
            if (mSearchCheck) {
                // implement your search here
                Log.i("Change", s);

            }
            return false;
        }
    };
}
