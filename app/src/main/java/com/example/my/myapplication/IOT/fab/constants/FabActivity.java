package com.example.my.myapplication.IOT.fab.constants;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my.myapplication.IOT.fab.FloatingActionButton;
import com.example.my.myapplication.IOT.fab.SpeedDialMenuAdapter;

import com.example.my.myapplication.R;

public class FabActivity extends AppCompatActivity {
    private LinearLayout closeFAB;

    // views
    private FloatingActionButton fab;

    // button values
    private static int[] icons = new int[]{
            R.mipmap.ic_add,
            R.mipmap.ic_done,
            R.mipmap.ic_cloud,
            R.mipmap.ic_swap_horiz,
            R.mipmap.ic_swap_vert
    };
    private static int[] buttonColours = new int[]{
            0xff0099ff,
            0xffff9900,
            0xffff0099,
            0xff9900ff
    };
    private static int[] coverColours = new int[]{
            0x99ffffff,
            0x990099ff,
            0x99ff9900,
            0x99ff0099,
            0x999900ff
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fab_activity);
        setTitle(R.string.app_name);


        closeFAB = (LinearLayout) findViewById(R.id.closeFAB);
        closeFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FabActivity.this, "close--------------------------", Toast.LENGTH_SHORT).show();

                fab.closeSpeedDialMenu();
            }
        });
        // get reference to FAB
        fab = (FloatingActionButton) findViewById(R.id.fabmenu);
        fab.setIcon(icons[0]);

        fab.setMenuAdapter(new SpeedDialAdapter());


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FabActivity.this, "clickfab1--------------------------", Toast.LENGTH_SHORT).show();
            }
        });
        fab.setOnSpeedDialOpenListener(new FloatingActionButton.OnSpeedDialOpenListener() {
            @Override
            public void onOpen(FloatingActionButton v) {

                Toast.makeText(FabActivity.this, R.string.speed_dial_opened, Toast.LENGTH_SHORT).show();
            }
        });
        fab.setOnSpeedDialCloseListener(new FloatingActionButton.OnSpeedDialCloseListener() {
            @Override
            public void onClose(FloatingActionButton v) {

                Toast.makeText(FabActivity.this, R.string.speed_dial_closed, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private class SpeedDialAdapter extends SpeedDialMenuAdapter {

        @Override
        protected int getCount() {
            return 5;
            //  return speedDialOptionalCloseEnabled ? 6 : 5;
        }

        @Override
        protected MenuItem getViews(Context context, int position) {
            MenuItem mi = new MenuItem();

            switch (position) {
                case 0:
                    // example: View and View
                    ImageView icon0 = new ImageView(context);
                    icon0.setImageResource(R.mipmap.ic_done);
                    TextView label0 = new TextView(context);
                    label0.setText(getString(R.string.speed_dial_label, position));
                    mi.iconView = icon0;
                    mi.labelView = label0;
                    break;

                case 1:
                    // example: Drawable and String
                    mi.iconDrawable = ContextCompat.getDrawable(context, R.mipmap.ic_swap_horiz);
                    mi.labelString = getString(R.string.speed_dial_label, position);
                    break;

                case 2:
                    // example: Drawable ID and String
                    mi.iconDrawableId = R.mipmap.ic_swap_vert;
                    mi.labelString = getString(R.string.speed_dial_label, position);
                    break;

                case 3:
                    // example: Drawable ID and String ID
                    mi.iconDrawableId = R.mipmap.ic_cloud;
                    mi.labelStringId = R.string.label_optional_close;
                    break;
                case 4:
                    // example: View and View
                    ImageView icon01 = new ImageView(context);
                    icon01.setImageResource(R.mipmap.ic_done);
                    TextView label01 = new TextView(context);
                    label01.setText(getString(R.string.speed_dial_label, position));
                    mi.iconView = icon01;
                    mi.labelView = label01;
                    break;
                default:
                    Log.wtf(C.LOG_TAG, "Okay, something went *really* wrong.");
                    return mi;
            }

            return mi;
        }

        @Override
        protected int getBackgroundColour(int position) {
            return super.getBackgroundColour(position);
        }

        @Override
        protected boolean onMenuItemClick(int position) {
            Toast.makeText(FabActivity.this, "-" + position + "-", Toast.LENGTH_SHORT).show();

            if (position == 3) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FabActivity.this);
                builder
                        .setTitle(R.string.menu_close_dialog_title)
                        .setMessage(R.string.menu_close_dialog_text)
                                //	.setPositiveButton(R.string.yes, (dialog, which) -> fab.closeSpeedDialMenu())
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fab.closeSpeedDialMenu();
                            }
                        })
                        .setNegativeButton(R.string.no, null)
                        .setCancelable(false);
                builder.create().show();
                return false;
            } else {
                Toast.makeText(FabActivity.this, getString(R.string.click_with_item, position), Toast.LENGTH_SHORT).show();
                return true;
            }
        }

    }
}
