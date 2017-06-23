package com.example.my.myapplication.IOT.search;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Locale;

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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.my.myapplication.IOT.IOTPlaceListFragment;
import com.example.my.myapplication.IOT.server.models.PlaceInfo;
import com.example.my.myapplication.R;

/**
 * Created by MY on 6/11/2017.
 */
public class ListViewSearchAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    SharedPreferences sharedPreferences;

    LayoutInflater inflater;
    private ArrayList<PlaceInfo> worldpopulationlist = null;
    private ArrayList<PlaceInfo> arraylist;

    public ListViewSearchAdapter(Context context,
                                 ArrayList<PlaceInfo> worldpopulationlist) {
        mContext = context;
        this.worldpopulationlist = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<PlaceInfo>();
        this.arraylist.addAll(worldpopulationlist);
    }

    public class ViewHolder {
        TextView place;
        TextView location;
        TextView description;
        //ImageView flag;
        ImageView flagsetting;
        RelativeLayout flaglayout;
    }

    @Override
    public int getCount() {
        return worldpopulationlist.size();
    }

    @Override
    public PlaceInfo getItem(int position) {
        return worldpopulationlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.place = (TextView) view.findViewById(R.id.Place);
            holder.location = (TextView) view.findViewById(R.id.location);
            holder.description = (TextView) view.findViewById(R.id.description);
            // Locate the ImageView in listview_item.xml
            //holder.flag = (ImageView) view.findViewById(R.id.flag);
            holder.flaglayout = (RelativeLayout) view.findViewById(R.id.flag);
            holder.flagsetting = (ImageView) view.findViewById(R.id.flagsetting);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.place.setText(worldpopulationlist.get(position).getDeviceInfo().getNameDevice());
        holder.location.setText(worldpopulationlist.get(position).getDeviceInfo().getLocation());
        holder.description.setText(worldpopulationlist.get(position)
                .getDeviceInfo().getDescription());
        // Set the results into ImageView
//        holder.flag.setImageResource(worldpopulationlist.get(position)
//                .getFlag());

        holder.flaglayout.setBackgroundResource(worldpopulationlist.get(position)
                .getFlag());

        // Listen for ListView Item Click
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //gui du lieu den activity
               /* // Send single item click data to IOTPlaceListFragment Class
                Intent intent = new Intent(mContext, IOTPlaceListFragment.class);
                // Pass all data rank
                intent.putExtra("rank",
                        (worldpopulationlist.get(position).getRank()));
                // Pass all data country
                intent.putExtra("country",
                        (worldpopulationlist.get(position).getCountry()));
                // Pass all data population
                intent.putExtra("population",
                        (worldpopulationlist.get(position).getPopulation()));
                // Pass all data flag
                intent.putExtra("flag",
                        (worldpopulationlist.get(position).getFlag()));
                // Start IOTPlaceListFragment Class
                mContext.startActivity(intent);*/


                Fragment mFragment;
                mFragment = new IOTPlaceListFragment();
                if (mFragment != null) {

                    Bundle bundle = new Bundle();
      /*              // Pass all data rank
                    bundle.putString("rank",
                            (worldpopulationlist.get(position).getDeviceInfo().getNameDevice()));
                    // Pass all data country
                    bundle.putString("country",
                            (worldpopulationlist.get(position).getDeviceInfo().getLocation()));
                    // Pass all data population
                    bundle.putString("population",
                            (worldpopulationlist.get(position).getDeviceInfo().getDescription()));*/
                    // Pass all data flag
                    bundle.putInt("flag",
                            (worldpopulationlist.get(position).getFlag()));
                    bundle.putSerializable("device", worldpopulationlist.get(position).getDeviceInfo());
                    bundle.putBoolean("active",
                            (worldpopulationlist.get(position).isActive()));
                    mFragment.setArguments(bundle);

                    FragmentManager manager = ((FragmentActivity) mContext).getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.container, mFragment).commit();
                }
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


        holder.flagsetting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, v) {


                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.album_overflow_delete:
                                //deleteAlbum(mAlbum);
                                return true;

                            case R.id.album_overflow_add:
                                // renameAlbum(mAlbum);
                                Toast.makeText(mContext, "album_overflow_rename" + worldpopulationlist.get(position).getDeviceInfo().getNameDevice(), Toast.LENGTH_LONG).show();


                                return true;

                            default:
                                return super.onMenuItemSelected(menu, item);
                        }
                    }
                };

                popupMenu.inflate(R.menu.place_menu);

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



    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldpopulationlist.clear();
        if (charText.length() == 0) {
            worldpopulationlist.addAll(arraylist);
        } else {
            for (PlaceInfo wp : arraylist) {
                if (wp.getDeviceInfo().getNameDevice().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    worldpopulationlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}
