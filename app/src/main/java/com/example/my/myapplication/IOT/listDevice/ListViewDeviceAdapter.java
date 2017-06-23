package com.example.my.myapplication.IOT.listDevice;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.my.myapplication.IOT.IOTDeviceListFragment;
import com.example.my.myapplication.IOT.LoginActivity;
import com.example.my.myapplication.IOT.materialprofile.InfoDeviceFragment;
import com.example.my.myapplication.IOT.server.models.Device;
import com.example.my.myapplication.IOT.server.service.ServiceDevice;
import com.example.my.myapplication.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by MY on 6/11/2017.
 */
public class ListViewDeviceAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    SharedPreferences sharedPreferences;
    ServiceDevice serviceDevice;
    String accountId;

    LayoutInflater inflater;
    private ArrayList<Device> worldpopulationlist = null;
    private ArrayList<Device> arraylist;

    public ListViewDeviceAdapter(Context context,
                                 ArrayList<Device> worldpopulationlist) {
        mContext = context;
        this.worldpopulationlist = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Device>();
        this.arraylist.addAll(worldpopulationlist);
    }

    public class ViewHolder {
        TextView nameDevice;
        TextView deviceId;
        ToggleButton chkState;
        ImageView flagsetting;
        RelativeLayout flaglayout;
    }

    @Override
    public int getCount() {
        return worldpopulationlist.size();
    }

    @Override
    public Device getItem(int position) {
        return worldpopulationlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        if (serviceDevice == null) {
            serviceDevice = new ServiceDevice();
        }
        accountId = InfoDeviceFragment.checkSessionAccountId(mContext, sharedPreferences);

        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            //view = inflater.inflate(R.layout.listviewdevice_item, null);
            view = inflater.inflate(R.layout.lv_layout, null);
            // Locate the TextViews in listview_item.xml
            holder.nameDevice = (TextView) view.findViewById(R.id.NameDevice);
            holder.deviceId = (TextView) view.findViewById(R.id.DeviceIdItem);
            holder.chkState = (ToggleButton) view.findViewById(R.id.chkState);

            // Locate the ImageView in listview_item.xml
            //holder.flag = (ImageView) view.findViewById(R.id.flag);
            holder.flagsetting = (ImageView) view.findViewById(R.id.flagsetting);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.nameDevice.setText(worldpopulationlist.get(position).getNameDevice());
        holder.deviceId.setText(worldpopulationlist.get(position).getDeviceId());
        holder.chkState.setChecked(worldpopulationlist.get(position).isActive());
        Log.i("checkvalue", worldpopulationlist.get(position).isActive() + "" + worldpopulationlist.get(position).getDeviceId());

        // Set the results into ImageView
//        holder.flag.setImageResource(worldpopulationlist.get(position)
//                .getFlag());

        // Listen for ListView Item Click
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showInfoDevice(mContext, sharedPreferences, worldpopulationlist.get(position));
            }
        });

        /*holder.flag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, v);
                popupMenu.inflate(R.menu.menu);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_search:
                                //do something here...
                                Toast.makeText(mContext,"a"+worldpopulationlist.get(position).getCountry(),Toast.LENGTH_LONG).show();
                                break;
                            case R.id.menu_add:

                                //do something
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });*/
        holder.chkState.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("checkvalueclick", worldpopulationlist.get(position).isActive() + "");
                boolean active = false;
                if (worldpopulationlist.get(position).isActive()) {
                    Log.i("check", "false");
                    active = false;
                } else {
                    Log.i("check", "true");
                    active = true;
                }
                if (!editDeviceActice(worldpopulationlist.get(position), active, serviceDevice)) {
                    InfoDeviceFragment.error(mContext);
                    holder.chkState.setChecked(!active);

                } else {
                    holder.chkState.setChecked(active);
                    LoginActivity.createSeesionListDevice(mContext, sharedPreferences, serviceDevice, accountId);
                    IOTDeviceListFragment.createSeesionDeviceInfo(mContext, sharedPreferences, worldpopulationlist.get(position));

                    InfoDeviceFragment.reloadInfoDeviceFragment(mContext);
                }

//                Fragment mFragment;
//                mFragment = new IOTDeviceListFragment();
//                FragmentManager manager = ((FragmentActivity) mContext).getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.container, mFragment).commit();
            }
        });


        holder.flagsetting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, v) {


                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                //deleteAlbum(mAlbum);
                                InfoDeviceFragment.deleteDeviceAndReloadIOTDeviceListFragment(worldpopulationlist.get(position).getDeviceId(), serviceDevice, mContext, sharedPreferences, accountId);
                                return true;

                            case R.id.showInfo:
                                // renameAlbum(mAlbum);
                                Toast.makeText(mContext, "show info" + worldpopulationlist.get(position).getNameDevice(), Toast.LENGTH_LONG).show();
                                showInfoDevice(mContext, sharedPreferences, worldpopulationlist.get(position));


                                return true;


                            default:
                                return super.onMenuItemSelected(menu, item);
                        }
                    }
                };

                popupMenu.inflate(R.menu.device_menu);

                // Force icons to show
                Object menuHelper;
                Class[] argTypes;
                try {
                    Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
                    fMenuHelper.setAccessible(true);
                    menuHelper = fMenuHelper.get(popupMenu);
                    argTypes = new Class[]{boolean.class};
                    menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
                } catch (Exception e) {
                    // Possible exceptions are NoSuchMethodError and NoSuchFieldError
                    //
                    // In either case, an exception indicates something is wrong with the reflection code, or the
                    // structure of the PopupMenu class or its dependencies has changed.
                    //
                    // These exceptions should never happen since we're shipping the AppCompat library in our own apk,
                    // but in the case that they do, we simply can't force icons to display, so log the error and
                    // show the menu normally.

                    Log.w("TAG", "error forcing menu icons to show", e);
                    popupMenu.show();
                    return;
                }

                popupMenu.show();

                //set vi tri cua popmenu
               /* // Try to force some horizontal offset
                try {
                    Field fListPopup = menuHelper.getClass().getDeclaredField("mPopup");
                    fListPopup.setAccessible(true);
                    Object listPopup = fListPopup.get(menuHelper);
                    argTypes = new Class[]{int.class};
                    Class listPopupClass = listPopup.getClass();

                    // Get the width of the popup window
                    int width = (Integer) listPopupClass.getDeclaredMethod("getWidth").invoke(listPopup);

                    // Invoke setHorizontalOffset() with the negative width to move left by that distance
                    listPopupClass.getDeclaredMethod("setHorizontalOffset", argTypes).invoke(listPopup, -width);

                    // Invoke show() to update the window's position
                    listPopupClass.getDeclaredMethod("show").invoke(listPopup);
                } catch (Exception e) {
                    // Again, an exception here indicates a programming error rather than an exceptional condition
                    // at runtime
                    Log.w("TAG", "Unable to force offset", e);
                }*/
            }
        });
        return view;
    }

    public static void showInfoDevice(Context mContext, SharedPreferences sharedPreferences, Device device) {
        IOTDeviceListFragment.createSeesionDeviceInfo(mContext, sharedPreferences, device);

        Fragment mFragment;
        mFragment = new InfoDeviceFragment();
        if (mFragment != null) {

            Bundle bundle = new Bundle();

            bundle.putSerializable("device", device);
            bundle.putBoolean("active",
                    (device.isActive()));

            mFragment.setArguments(bundle);

            FragmentManager manager = ((FragmentActivity) mContext).getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container, mFragment).commit();
        }

    }

    private boolean editDeviceActice(Device deviceInfo, boolean active, ServiceDevice serviceDevice) {
        Device editDevice = new Device(deviceInfo.getDeviceId(), deviceInfo.getNameDevice(), deviceInfo.isActive(), deviceInfo.getLocation(), deviceInfo.getLatitude(), deviceInfo.getLongitude(), deviceInfo.getDescription(), deviceInfo.getId(), deviceInfo.getTypeId(), deviceInfo.getAccountId(), deviceInfo.getKeyThingspeak());
        editDevice.setActive(active);
        return InfoDeviceFragment.editDevice(editDevice, serviceDevice);
    }


}
