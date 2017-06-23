package com.example.my.myapplication.IOT.materialprofile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my.myapplication.IOT.IOTDeviceListFragment;
import com.example.my.myapplication.IOT.LoginActivity;
import com.example.my.myapplication.IOT.fab.SpeedDialMenuAdapter;
import com.example.my.myapplication.IOT.fab.constants.C;
import com.example.my.myapplication.IOT.map.mapClickDetail.TestMapFragment;
import com.example.my.myapplication.IOT.server.ConfigApp;
import com.example.my.myapplication.IOT.server.models.AddressGeo;
import com.example.my.myapplication.IOT.server.models.Device;
import com.example.my.myapplication.IOT.server.models.TypeParticle;
import com.example.my.myapplication.IOT.server.service.ServiceDevice;
import com.example.my.myapplication.IOT.server.service.ServiceType;
import com.example.my.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class InfoDeviceFragment extends Fragment {
    View rootView;

    //fab cach 1
    private com.example.my.myapplication.IOT.fab.FloatingActionButton fab;
    private LinearLayout closeFAB;

    // button values
    private static int[] icons = new int[]{
            R.mipmap.ic_add,
            R.mipmap.ic_done,
            R.mipmap.ic_cloud,
            R.mipmap.ic_swap_horiz,
            R.mipmap.ic_swap_vert
    };
    //end fab cach 1

    //fab cach 2
    //scroll fab 3
    FloatingActionButton fabscroll3;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;
    CoordinatorLayout rootLayout;

    //Save the FAB's active status
    //false -> fab = close
    //true -> fab = open
    private boolean FAB_Status = false;

    //Animations
    Animation show_fab_1;
    Animation hide_fab_1;
    Animation show_fab_2;
    Animation hide_fab_2;
    Animation show_fab_3;
    Animation hide_fab_3;
    //end fab cach 2

    //edit
    private View alertView;
    private AlertDialog myDialog;

    //create device
    private View alertViewCreate;
    private AlertDialog myDialogCreate;

    //alert
    CharSequence myList[];
    CharSequence myListDefault[] = {"Photons", "Electrons"};
    private AlertDialog myDialogType;

    SharedPreferences sharedPreferences;
    ServiceType serviceType;
    ServiceDevice serviceDevice;
    ArrayList<TypeParticle> listTypeParticle = null;

    Device deviceInfo;
    TextView nameDevice, typeParticle, deviceID, location, keyThingspeak, listType, edittypeParticle;
    EditText editnameDevice, editdeviceID, editlocation, editkeyThingspeak;
    static String accountId;
    private int indexType = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        try {
            rootView = inflater.inflate(R.layout.activity_info_device, container, false);

        } catch (InflateException e) {
            Log.e("TAG", "Inflate exception");
        }
        if (serviceType == null) {
            serviceType = new ServiceType();
        }
        if (serviceDevice == null) {
            serviceDevice = new ServiceDevice();
        }


//        List<String> listItems = new ArrayList<String>();
//        listItems.add("Photon");
//        listItems.add("Electron");
//        CharSequence myListDemo[]  = listItems.toArray(new CharSequence[listItems.size()]);
//
//        String[] mEntriesString = new String[myListDemo.length];
//        int i=0;
//        for(CharSequence ch: myListDemo){
//            mEntriesString[i++] = ch.toString();
//        }
        listTypeParticle = checkSessionListType(getContext(), sharedPreferences);
        accountId = checkSessionAccountId(getContext(), sharedPreferences);
        if (listTypeParticle == null) {
            listTypeParticle = createSessionListTypeParticle(getContext(), sharedPreferences, serviceType);
        }

        if (listTypeParticle != null) {
            myList = null;
            int len = listTypeParticle.size();
            List<String> listItems = new ArrayList<String>();
            for (int i = 0; i < len; i++) {
                listItems.add(listTypeParticle.get(i).getNameType());
            }
            myList = listItems.toArray(new CharSequence[listItems.size()]);
        } else {
            myList = myListDefault;
        }

        nameDevice = (TextView) rootView.findViewById(R.id.NameDevice);
        typeParticle = (TextView) rootView.findViewById(R.id.typeParticle);
        deviceID = (TextView) rootView.findViewById(R.id.DeviceID);
        location = (TextView) rootView.findViewById(R.id.Location);
        keyThingspeak = (TextView) rootView.findViewById(R.id.keyThingspeak);
        deviceInfo = checkSessionDeviceInfo(getContext(), sharedPreferences);
        //cach 2
        // deviceInfo = (Device) getArguments().getSerializable("device");

        if (deviceInfo == null) {
            reloadIOTDeviceListFragmentt(getContext());
        } else {
            setDeviceInfo(deviceInfo, listTypeParticle, nameDevice, typeParticle, deviceID, location, keyThingspeak);

        }
        //demo click
//        FloatingActionButton fabs = (FloatingActionButton) rootView.findViewById(R.id.fab3scroll);
//        fabs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        //end demo click

        //cach 2
        //Floating Action Buttons
        fabscroll3 = (FloatingActionButton) rootView.findViewById(R.id.fab3scroll);
        fab1 = (FloatingActionButton) rootView.findViewById(R.id.fab_1);
        fab2 = (FloatingActionButton) rootView.findViewById(R.id.fab_2);
        fab3 = (FloatingActionButton) rootView.findViewById(R.id.fab_3);

        //Animations
        show_fab_1 = AnimationUtils.loadAnimation(getContext(), R.anim.fab1_show);
        hide_fab_1 = AnimationUtils.loadAnimation(getContext(), R.anim.fab1_hide);
        show_fab_2 = AnimationUtils.loadAnimation(getContext(), R.anim.fab2_show);
        hide_fab_2 = AnimationUtils.loadAnimation(getContext(), R.anim.fab2_hide);
        show_fab_3 = AnimationUtils.loadAnimation(getContext(), R.anim.fab3_show);
        hide_fab_3 = AnimationUtils.loadAnimation(getContext(), R.anim.fab3_hide);


        fabscroll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FAB_Status == false) {
                    //Display FAB menu
                    expandFAB();
                    FAB_Status = true;
                } else {
                    //Close FAB menu
                    hideFAB();
                    FAB_Status = false;
                }
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Create", Toast.LENGTH_SHORT).show();
                AlertCreate();
                myDialogCreate.show();

            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Edit", Toast.LENGTH_SHORT).show();
                AlertEdit();
                myDialog.show();

            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Delete", Toast.LENGTH_SHORT).show();
                deleteDeviceAndReloadIOTDeviceListFragment(deviceInfo.getDeviceId(), serviceDevice, getContext(), sharedPreferences, accountId);

            }
        });






        /*//Initialize an empty list of 50 elements
        List list = new ArrayList();
        for (int i = 0; i < 50; i++) {
            list.add(new Object());
        }*/

//        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
//        Recycler_View_Adapter adapter = new Recycler_View_Adapter(list, getContext());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        recyclerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (FAB_Status) {
//                    hideFAB();
//                    FAB_Status = false;
//                }
//                return false;
//            }
//        });


        //end cach 2

        //cach 1
        closeFAB = (LinearLayout) rootView.findViewById(R.id.closeFABclick);
        closeFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.closeSpeedDialMenu();

            }
        });
        // get reference to FAB
        fab = (com.example.my.myapplication.IOT.fab.FloatingActionButton) rootView.findViewById(R.id.fabmenuclick);
        fab.setIcon(icons[0]);

        fab.setMenuAdapter(new SpeedDialAdapter());


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "khong co tat dung", Toast.LENGTH_SHORT).show();
            }
        });
        fab.setOnSpeedDialOpenListener(new com.example.my.myapplication.IOT.fab.FloatingActionButton.OnSpeedDialOpenListener() {
            @Override
            public void onOpen(com.example.my.myapplication.IOT.fab.FloatingActionButton v) {

                Toast.makeText(getContext(), R.string.speed_dial_opened, Toast.LENGTH_SHORT).show();
            }
        });
        fab.setOnSpeedDialCloseListener(new com.example.my.myapplication.IOT.fab.FloatingActionButton.OnSpeedDialCloseListener() {
            @Override
            public void onClose(com.example.my.myapplication.IOT.fab.FloatingActionButton v) {

                Toast.makeText(getContext(), R.string.speed_dial_closed, Toast.LENGTH_SHORT).show();
            }
        });
        //end cach 1
        return rootView;
    }

    private void AlertCreate() {
        //alert create
        AlertDialog.Builder builderCreate = new AlertDialog.Builder(getContext());

        LayoutInflater inflatersCreate = LayoutInflater.from(getContext());
        alertViewCreate = inflatersCreate.inflate(R.layout.dialog_edit_device, null);
        builderCreate.setView(alertViewCreate);

        builderCreate.setTitle("Create Device");

        builderCreate.setIcon(R.drawable.rsz_particle);

        editnameDevice = (EditText) alertViewCreate.findViewById(R.id.editNameDevice);
        edittypeParticle = (TextView) alertViewCreate.findViewById(R.id.edittypeParticle);
        editdeviceID = (EditText) alertViewCreate.findViewById(R.id.editDeviceId);
        editlocation = (EditText) alertViewCreate.findViewById(R.id.editLocation);
        listType = (TextView) alertViewCreate.findViewById(R.id.listTypeParticle);
        editkeyThingspeak = (EditText) alertViewCreate.findViewById(R.id.editkeyThingspeak);

        //setDeviceInfo(deviceInfo, listTypeParticle, editnameDevice, edittypeParticle, editdeviceID, editlocation);

        AlertType();
        listType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDialogType.show();
            }
        });

        builderCreate.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
              /*  EditText t_user = (EditText) alertView.findViewById(R.id.usernamedialog);
                EditText t_pass = (EditText) alertView.findViewById(R.id.passworddialog);
                String username = t_user.getText().toString();
                String password = t_pass.getText().toString();
                Toast toast = Toast.makeText(getContext(), "User: " + username + ", Pass: " + password, Toast.LENGTH_SHORT);
                toast.show();*/
                String nameDevice = editnameDevice.getText().toString();
                String typeName = edittypeParticle.getText().toString();
                String deviceID = editdeviceID.getText().toString();
                String location = editlocation.getText().toString();
                String keyThingspeak = editkeyThingspeak.getText().toString();

                // => kinh do vi do
                double la = 0;
                double lo = 0;

                AddressGeo addressGeo = TestMapFragment.getInfoAddress(location);
                la = addressGeo.getLa();
                lo = addressGeo.getLn();

                String typeID = getIdTypeParticle(listTypeParticle, typeName);

                Device createDevice = new Device(deviceID, nameDevice, true, location, la, lo, "",
                        "", typeID, accountId, keyThingspeak);
                Log.i("deviceCreate", createDevice.getNameDevice());

                if (!createDevice(createDevice, serviceDevice)) {
                    errorCreate();
                } else {
                    LoginActivity.createSeesionListDevice(getContext(), sharedPreferences, serviceDevice, accountId);
                    IOTDeviceListFragment.createSeesionDeviceInfo(getContext(), sharedPreferences, createDevice);
                    reloadInfoDeviceFragment(getContext());

                }
                closeFAB();
            }
        });


        builderCreate.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
              /*  Toast.makeText(getContext(),
                        "You Have Cancel the Dialog box", Toast.LENGTH_LONG)
                        .show();*/
                closeFAB();
            }
        });

        builderCreate.setCancelable(false);
        myDialogCreate = builderCreate.create();
        //end alert create

    }

    private void AlertType() {
        //alert
        AlertDialog.Builder adType = new AlertDialog.Builder(getContext());
        adType.setTitle("TypeParticle");

        indexType = getIndexTypeParticle(listTypeParticle, typeParticle.getText().toString());
        //so 1 la so se click trong list
        adType.setSingleChoiceItems(myList, indexType, new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                Toast.makeText(getContext(),
                        "You Choose : " + myList[arg1],
                        Toast.LENGTH_LONG).show();
                edittypeParticle.setText(myList[arg1].toString());
                indexType = getIndexTypeParticle(listTypeParticle, typeParticle.getText().toString());

                myDialogType.dismiss();
            }
        });
        adType.setCancelable(false);
        myDialogType = adType.create();
    }

    private void AlertEdit() {

        //alert edit
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflaters = LayoutInflater.from(getContext());
        alertView = inflaters.inflate(R.layout.dialog_edit_device, null);
        builder.setView(alertView);

        builder.setTitle("Edit Device");

        builder.setIcon(R.drawable.rsz_particle);

        editnameDevice = (EditText) alertView.findViewById(R.id.editNameDevice);
        edittypeParticle = (TextView) alertView.findViewById(R.id.edittypeParticle);
        editdeviceID = (EditText) alertView.findViewById(R.id.editDeviceId);
        editlocation = (EditText) alertView.findViewById(R.id.editLocation);
        listType = (TextView) alertView.findViewById(R.id.listTypeParticle);
        editkeyThingspeak = (EditText) alertView.findViewById(R.id.editkeyThingspeak);
        if (deviceInfo != null)
            setDeviceInfoEdit(deviceInfo, listTypeParticle, editnameDevice, edittypeParticle, editdeviceID, editlocation, editkeyThingspeak);
        AlertType();
        listType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialogType.show();
            }
        });


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
              /*  EditText t_user = (EditText) alertView.findViewById(R.id.usernamedialog);
                EditText t_pass = (EditText) alertView.findViewById(R.id.passworddialog);
                String username = t_user.getText().toString();
                String password = t_pass.getText().toString();
                Toast toast = Toast.makeText(getContext(), "User: " + username + ", Pass: " + password, Toast.LENGTH_SHORT);
                toast.show();*/
                String nameDevice = editnameDevice.getText().toString();
                String typeName = edittypeParticle.getText().toString();
                String deviceID = editdeviceID.getText().toString();
                String location = editlocation.getText().toString();
                String keyThingspeak = editkeyThingspeak.getText().toString();

                // => kinh do vi do
                double la;
                double lo;
                if (deviceInfo.getLocation().equals(location) || location.equals("")) {
                    la = deviceInfo.getLatitude();
                    lo = deviceInfo.getLongitude();
                } else {
                    AddressGeo addressGeo = TestMapFragment.getInfoAddress(location);
                    la = addressGeo.getLa();
                    lo = addressGeo.getLn();
                }
                String typeID = getIdTypeParticle(listTypeParticle, typeName);

                Device editDevice = new Device(deviceInfo.getDeviceId(), deviceInfo.getNameDevice(), deviceInfo.isActive(), deviceInfo.getLocation(), deviceInfo.getLatitude(), deviceInfo.getLongitude(), deviceInfo.getDescription(), deviceInfo.getId(), deviceInfo.getTypeId(), deviceInfo.getAccountId(), deviceInfo.getKeyThingspeak());
                editDevice.setNameDevice(nameDevice);
                editDevice.setTypeId(typeID);
                editDevice.setDeviceId(deviceID);
                editDevice.setLocation(location);
                editDevice.setKeyThingspeak(keyThingspeak);
                editDevice.setLatitude(la);
                editDevice.setLongitude(lo);

                Log.i("deviceInfo", deviceInfo.getNameDevice());
                Log.i("deviceEdit", editDevice.getNameDevice());

                if (!editDevice(editDevice, serviceDevice)) {
                    error(getContext());
                } else {
                    LoginActivity.createSeesionListDevice(getContext(), sharedPreferences, serviceDevice, accountId);
                    IOTDeviceListFragment.createSeesionDeviceInfo(getContext(), sharedPreferences, editDevice);
                    reloadInfoDeviceFragment(getContext());
                }
                closeFAB();
            }
        });


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
              /*  Toast.makeText(getContext(),
                        "You Have Cancel the Dialog box", Toast.LENGTH_LONG)
                        .show();*/
                closeFAB();
            }
        });

        builder.setCancelable(false);
        myDialog = builder.create();
        //end alert edit

    }


    public static boolean deleteDeviceAndReloadIOTDeviceListFragment(String deviceId, ServiceDevice serviceDevice, Context context, SharedPreferences sharedPreferences, String accountId) {
        if (!deleteDevice(deviceId, serviceDevice)) {
            error(context);
            return false;
        } else {
            LoginActivity.createSeesionListDevice(context, sharedPreferences, serviceDevice, accountId);
            reloadIOTDeviceListFragmentt(context);
            return true;
        }
    }

    public static void reloadIOTDeviceListFragmentt(Context context) {
        Fragment mFragment;
        mFragment = new IOTDeviceListFragment();
        FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, mFragment).commit();
    }

    public static void reloadInfoDeviceFragment(Context context) {
        Fragment mFragment;
        mFragment = new InfoDeviceFragment();
        FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, mFragment).commit();
    }


    private void errorCreate() {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Error Create");
        alertDialog.setMessage("Vui lòng thử lại");
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private boolean createDevice(Device createDevice, ServiceDevice serviceDevice) {
        boolean check = false;
        try {
            check = serviceDevice.createDevice(createDevice);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return check;
    }

    private static boolean deleteDevice(String deviceId, ServiceDevice serviceDevice) {
        boolean check = false;
        try {
            check = serviceDevice.deleteDevice(deviceId);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return check;
    }

    public static String checkSessionAccountId(Context context, SharedPreferences sharedPreferences) {
        sharedPreferences = context.getSharedPreferences(ConfigApp.getACCOUNT(), Context.MODE_PRIVATE);
        return accountId = sharedPreferences.getString(ConfigApp.getIdAccount(), null);
    }

    public static void error(Context context) {
      /*
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lỗi đăng ký");
        builder.setMessage(message);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();*/

        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Vui lòng thử lại");
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static boolean editDevice(Device editDevice, ServiceDevice serviceDevice) {
        boolean check = false;
        try {
            check = serviceDevice.editDevice(editDevice);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return check;
    }

    private void setDeviceInfo(Device deviceInfo, ArrayList<TypeParticle> listTypeParticle, TextView nameDevice, TextView typeParticle, TextView deviceID, TextView location, TextView keyThingspeak) {
        nameDevice.setText(deviceInfo.getNameDevice());
        deviceID.setText(deviceInfo.getDeviceId());
        location.setText(deviceInfo.getLocation());
        String typeName = getNameTypeParticle(listTypeParticle, deviceInfo.getTypeId());
        typeParticle.setText(typeName);
        keyThingspeak.setText(deviceInfo.getKeyThingspeak());
    }

    private void setDeviceInfoEdit(Device deviceInfo, ArrayList<TypeParticle> listTypeParticle, EditText nameDevice, TextView typeParticle, EditText deviceID, EditText location, EditText keyThingspeak) {
        nameDevice.setText(deviceInfo.getNameDevice());
        deviceID.setText(deviceInfo.getDeviceId());
        location.setText(deviceInfo.getLocation());
        String typeName = getNameTypeParticle(listTypeParticle, deviceInfo.getTypeId());
        typeParticle.setText(typeName);
        keyThingspeak.setText(deviceInfo.getKeyThingspeak());
    }


    private String getNameTypeParticle(ArrayList<TypeParticle> listTypeParticle, String typeId) {
        String nameType = "";
        int len = listTypeParticle.size();
        for (int i = 0; i < len; i++) {
            if (listTypeParticle.get(i).getId().equals(typeId)) {
                nameType = listTypeParticle.get(i).getNameType();
            }
        }
        return nameType;
    }


    private String getIdTypeParticle(ArrayList<TypeParticle> listTypeParticle, String name) {
        String idType = "";
        int len = listTypeParticle.size();
        for (int i = 0; i < len; i++) {
            if (listTypeParticle.get(i).getNameType().equals(name)) {
                idType = listTypeParticle.get(i).getId();
            }
        }
        return idType;
    }

    private int getIndexTypeParticle(ArrayList<TypeParticle> listTypeParticle, String name) {
        int idType = 0;
        int len = listTypeParticle.size();
        for (int i = 0; i < len; i++) {
            if (listTypeParticle.get(i).getNameType().equals(name)) {
                idType = i;
            }
        }
        return idType;
    }

    public static ArrayList<TypeParticle> createSessionListTypeParticle(Context context, SharedPreferences sharedPreferences, ServiceType serviceType) {
        ArrayList<TypeParticle> listDevice = null;
        sharedPreferences = context.getSharedPreferences(ConfigApp.getACCOUNT(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            listDevice = serviceType.getListType();
            Gson gson = new Gson();
            String json = gson.toJson(listDevice);
            Log.d("listType", json);
            /*   //danh sach thiet bi
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editors = sharedPrefs.edit();
            //c1
            editors.putString(ConfigApp.getListDevice(), json);
            editors.commit();
            */

            //chuyen cach 2
            editor.putString(ConfigApp.getLISTTYPE(), json);
            editor.commit();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listDevice;
    }


    public static ArrayList<TypeParticle> checkSessionListType(Context context, SharedPreferences sharedPreferences) {
        sharedPreferences = context.getSharedPreferences(ConfigApp.getACCOUNT(), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<TypeParticle>>() {
        }.getType();
       /* //c1
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String json = sharedPrefs.getString(ConfigApp.getListDevice(), null);
        ArrayList<Device> arrayList = gson.fromJson(json, type);
        Log.d("list", arrayList.get(0).getNameDevice());*/
        //c2
        String json2 = sharedPreferences.getString(ConfigApp.getLISTTYPE(), null);
        ArrayList<TypeParticle> arrayList2 = gson.fromJson(json2, type);
//        Log.d("list2", arrayList2.get(0).getNameType());
        return arrayList2;
    }

    public static Device checkSessionDeviceInfo(Context context, SharedPreferences sharedPreferences) {
        sharedPreferences = context.getSharedPreferences(ConfigApp.getACCOUNT(), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<Device>() {
        }.getType();
       /* //c1
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String json = sharedPrefs.getString(ConfigApp.getListDevice(), null);
        ArrayList<Device> arrayList = gson.fromJson(json, type);
        Log.d("list", arrayList.get(0).getNameDevice());*/
        //c2
        String json2 = sharedPreferences.getString(ConfigApp.getDEVICEINFO(), null);
        Device device = gson.fromJson(json2, type);
//        Log.d("list2", arrayList2.get(0).getNameType());
        return device;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settingsnewnew) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //cach 1
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
            Toast.makeText(getContext(), "-" + position + "-", Toast.LENGTH_SHORT).show();

            if (position == 3) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                Toast.makeText(getContext(), getString(R.string.click_with_item, position), Toast.LENGTH_SHORT).show();
                return true;
            }
        }
    }
    //end cach 1

    //cach 2
    private void expandFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(show_fab_1);
        fab1.setClickable(true);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin += (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin += (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(show_fab_2);
        fab2.setClickable(true);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams3.rightMargin += (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin += (int) (fab3.getHeight() * 1.7);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(show_fab_3);
        fab3.setClickable(true);
    }


    private void hideFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(hide_fab_2);
        fab2.setClickable(false);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams3.rightMargin -= (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin -= (int) (fab3.getHeight() * 1.7);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(hide_fab_3);
        fab3.setClickable(false);
    }

    //end cach 2
    public void closeFAB() {
        //Close FAB menu
        hideFAB();
        FAB_Status = false;
    }
}
