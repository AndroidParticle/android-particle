package com.example.my.myapplication.IOT.fab;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my.myapplication.IOT.fab.FloatingActionButton;
import com.example.my.myapplication.IOT.fab.SpeedDialMenuAdapter;
import com.example.my.myapplication.IOT.fab.constants.C;

import android.support.v7.widget.CardView;

import com.example.my.myapplication.R;

public class DemoActivity extends AppCompatActivity {
    private LinearLayout closeFAB;
    // state
    private boolean fabHidden;
    private boolean inClickMode;
    private int iconSelected;
    private int buttonColourSelected;
    private int coverColourSelected;
    private boolean speedDialColoursEnabled;
    private boolean speedDialOptionalCloseEnabled;

    // views
    private FloatingActionButton fab;
    private Button hideShowButton;
    private Button switchModeButton;
    private Button changeCoverColourButton;
    private Button toggleSpeedDialColoursSwitch;
    private Button toggleSpeedDialOptionalCloseButton;
    private Button openSpeedDialButton;

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

        // set/restore state
        if (savedInstanceState == null) {
            // initial state
            fabHidden = false;
            inClickMode = true;
            iconSelected = 0;
            buttonColourSelected = 0;
            coverColourSelected = 0;
            speedDialColoursEnabled = false;
            speedDialOptionalCloseEnabled = false;
        } else {
            // restore state
            fabHidden = savedInstanceState.getBoolean("fabHidden");
            inClickMode = savedInstanceState.getBoolean("inClickMode");
            iconSelected = savedInstanceState.getInt("iconSelected");
            buttonColourSelected = savedInstanceState.getInt("buttonColourSelected");
            coverColourSelected = savedInstanceState.getInt("coverColourSelected");
            speedDialColoursEnabled = savedInstanceState.getBoolean("speedDialColoursEnabled");
            speedDialOptionalCloseEnabled = savedInstanceState.getBoolean("speedDialOptionalCloseEnabled");
        }


        closeFAB = (LinearLayout) findViewById(R.id.closeFAB);
        closeFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DemoActivity.this, "close--------------------------", Toast.LENGTH_SHORT).show();

                fab.closeSpeedDialMenu();
            }
        });
        // get reference to FAB
        fab = (FloatingActionButton) findViewById(R.id.fabmenu);

        // initialise FAB
        if (savedInstanceState == null) {
            fab.setIcon(icons[iconSelected]);
            fab.setBackgroundColour(buttonColours[buttonColourSelected]);
            fab.setContentCoverColour(coverColours[coverColourSelected]);
        }
        fab.setMenuAdapter(new SpeedDialAdapter());

        //fab.setOnClickListener(iv -> Toast.makeText(DemoActivity.this, R.string.click_simple, Toast.LENGTH_SHORT).show());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DemoActivity.this, "clickfab1--------------------------", Toast.LENGTH_SHORT).show();
            }
        });
        fab.setOnSpeedDialOpenListener(new FloatingActionButton.OnSpeedDialOpenListener() {
            @Override
            public void onOpen(FloatingActionButton v) {

                Toast.makeText(DemoActivity.this, R.string.speed_dial_opened, Toast.LENGTH_SHORT).show();
            }
        });
        fab.setOnSpeedDialCloseListener(new FloatingActionButton.OnSpeedDialCloseListener() {
            @Override
            public void onClose(FloatingActionButton v) {

                Toast.makeText(DemoActivity.this, R.string.speed_dial_closed, Toast.LENGTH_SHORT).show();
            }
        });
        //	fab.setOnClickListener(iv -> Toast.makeText(DemoActivity.this, R.string.click_simple, Toast.LENGTH_SHORT).show());
//		fab.setOnSpeedDialOpenListener(f -> Toast.makeText(DemoActivity.this, R.string.speed_dial_opened, Toast.LENGTH_SHORT).show());
//		fab.setOnSpeedDialCloseListener(f -> Toast.makeText(DemoActivity.this, R.string.speed_dial_closed, Toast.LENGTH_SHORT).show());

        // get references to buttons
        hideShowButton = (Button) findViewById(R.id.hide_show);
        switchModeButton = (Button) findViewById(R.id.switch_mode);
        changeCoverColourButton = (Button) findViewById(R.id.change_cover_colour);
        toggleSpeedDialColoursSwitch = (Button) findViewById(R.id.toggle_colours);
        toggleSpeedDialOptionalCloseButton = (Button) findViewById(R.id.toggle_optional_close);
        openSpeedDialButton = (Button) findViewById(R.id.open_speed_dial);

        // update view state
        updateButtonStates();

        // set click listeners
        hideShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabHidden) {
                    fab.show();
                } else {
                    fab.hide();
                }
                fabHidden = !fabHidden;
                updateButtonStates();
            }
        });

        switchModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inClickMode = !inClickMode;
                updateButtonStates();
            }
        });

        findViewById(R.id.change_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconSelected = ++iconSelected % icons.length;
                fab.setIcon(icons[iconSelected]);
            }
        });

        findViewById(R.id.change_button_colour).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonColourSelected = ++buttonColourSelected % buttonColours.length;
                fab.setBackgroundColour(buttonColours[buttonColourSelected]);
            }
        });

        findViewById(R.id.change_cover_colour).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coverColourSelected = ++coverColourSelected % coverColours.length;
                fab.setContentCoverColour(coverColours[coverColourSelected]);
            }
        });

        toggleSpeedDialColoursSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speedDialColoursEnabled = !speedDialColoursEnabled;
                updateButtonStates();
                fab.rebuildSpeedDialMenu();
            }
        });

        toggleSpeedDialOptionalCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                speedDialOptionalCloseEnabled = !speedDialOptionalCloseEnabled;
                updateButtonStates();
                fab.rebuildSpeedDialMenu();
            }
        });

        openSpeedDialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fab.openSpeedDialMenu();
                if (!fab.isShown())
                    Toast.makeText(DemoActivity.this, R.string.disabled_when_hidden, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fabHidden", fabHidden);
        outState.putBoolean("inClickMode", inClickMode);
        outState.putInt("iconSelected", iconSelected);
        outState.putInt("buttonColourSelected", buttonColourSelected);
        outState.putInt("coverColourSelected", coverColourSelected);
        outState.putBoolean("speedDialColoursEnabled", speedDialColoursEnabled);
        outState.putBoolean("speedDialOptionalCloseEnabled", speedDialOptionalCloseEnabled);
    }

    private void updateButtonStates() {
        hideShowButton.setText(fabHidden ? R.string.show_fab : R.string.hide_fab);
        switchModeButton.setText(inClickMode ? R.string.switch_mode_to_speed_dial : R.string.switch_mode_to_click);
        changeCoverColourButton.setEnabled(!inClickMode);
        toggleSpeedDialColoursSwitch.setEnabled(!inClickMode);
        toggleSpeedDialColoursSwitch.setText(speedDialColoursEnabled ? R.string.toggle_colours_off : R.string.toggle_colours_on);
        toggleSpeedDialOptionalCloseButton.setEnabled(!inClickMode);
        toggleSpeedDialOptionalCloseButton.setText(speedDialOptionalCloseEnabled ? R.string.toggle_optional_close_off : R.string.toggle_optional_close_on);
        // openSpeedDialButton.setEnabled(!inClickMode);
        openSpeedDialButton.setEnabled(true);
    }

    private class SpeedDialAdapter extends SpeedDialMenuAdapter {

        @Override
        protected int getCount() {
            return speedDialOptionalCloseEnabled ? 6 : 5;
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
            if (speedDialColoursEnabled) {
                int[] colours = new int[]{
                        0xffff9900,
                        0xff0099ff,
                        0xffff0099,
                        0xff9900ff
                };
                return colours[position];
            }

            return super.getBackgroundColour(position);
        }

        @Override
        protected boolean onMenuItemClick(int position) {
            Toast.makeText(DemoActivity.this, "-" + position + "-", Toast.LENGTH_SHORT).show();

            if (position == 3) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DemoActivity.this);
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
                Toast.makeText(DemoActivity.this, getString(R.string.click_with_item, position), Toast.LENGTH_SHORT).show();
                return true;
            }
        }

        @Override
        protected boolean rotateFab() {
            return iconSelected == 0;
        }

        @Override
        protected boolean isEnabled() {
            return !inClickMode;
        }
    }
}
