package com.example.my.myapplication.IOT.gridview;

/**
 * Created by MY on 6/11/2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my.myapplication.IOT.IOTWebviewStatictisFragment;
import com.example.my.myapplication.R;

public class CustomAdapters extends BaseAdapter {

    String[] result;
    Context context;
    int[] imageId;
    int[] envCurrent;
    String[] envStr;

    private static LayoutInflater inflater = null;


    public CustomAdapters(Context mainActivity, String[] prgmNameList, int[] prgmImages, int[] envCurrent, String[] envStr) {
        result = prgmNameList;
        context = mainActivity;
        imageId = prgmImages;
        this.envCurrent = envCurrent;
        this.envStr = envStr;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView tv;
        ImageView img;
        TextView valueCurrentEnv;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.programlist, null);
        holder.tv = (TextView) rowView.findViewById(R.id.textView12);
        holder.img = (ImageView) rowView.findViewById(R.id.imageView12);
        holder.valueCurrentEnv = (TextView) rowView.findViewById(R.id.valueCurrentEnv);

        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);
       //holder.valueCurrentEnv.setText(envCurrent[position]);
        holder.valueCurrentEnv.setText(envStr[position]);
        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();

                Fragment mFragment;
                // mFragment = new TestMapFragment();
                mFragment = new IOTWebviewStatictisFragment();
                if (mFragment != null) {

                    Bundle bundle = new Bundle();
                    bundle.putString("message", result[position]);
                    mFragment.setArguments(bundle);

                    Log.i("not null", "MyClass.getView() — get item number " + mFragment.toString());
                    FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.container, mFragment).commit();
                }

            }
        });

        return rowView;
    }

}