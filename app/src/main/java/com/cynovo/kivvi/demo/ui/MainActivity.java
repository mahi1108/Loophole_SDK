package com.cynovo.kivvi.demo.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvi.demo.ui.adapter.MsgAdapter;
import com.cynovo.kivvi.demo.ui.adapter.MyAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cynovo.com.sdktool.utils.BaseTips;

public class MainActivity extends Activity{

    private ListView lvCmd;
    private Context mContext;
    private ListView lvMsg;
    private MsgAdapter mAdapter;
    private MyAdapter myAdapter;
    private Button mBtnClearLog;
    private ArrayList<String> allData = new ArrayList<String>();
    private ArrayList<String> dirs = new ArrayList<String>();
    private ImageView imageView;
    private String actionName = "";
    private TextView tvShowPosition;
    private boolean initializedone=false;
    private int item_position = 0;
    private String selected_child_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
      //  initView();

        loadSplashScreen();
    }

    public void loadSplashScreen( )
    {

        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        ImageView logo = (ImageView) findViewById(R.id.logo);
        logo.startAnimation(shake);

        loadMenuOptionsScreens( );
    }

    public void loadMenuOptionsScreens( )
    {

        Handler h = new Handler( );
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                Animation shake = AnimationUtils.loadAnimation(MainActivity.this,
                        R.anim.shake);
                ImageView logo = (ImageView) findViewById(R.id.logo);
                logo.startAnimation(shake);


                Handler h1 = new Handler( );
                h1.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        setContentView(R.layout.activity_menu_selection);

                        Button qrcodescan = (Button) findViewById(R.id.qrcodescan);
                        Button chipreading = (Button) findViewById(R.id.chipreading);
                        Button swipecard = (Button) findViewById(R.id.swipecard);
                        Button scancard = (Button) findViewById(R.id.scancard);
                        Button print = (Button) findViewById(R.id.print);
                        Button applepay = (Button) findViewById(R.id.applepay);


                        View.OnClickListener listener = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                setContentView(R.layout.activity_main);

                                initializedone = true;

                                initView();

                                reloadOnResume();

                        // [Basic Test-1, Pinpad-2, Storage-3, Card-4, Exscreen-5, Scan-6, Print-7, scangun-9, Mifare-10, pinpad]

                                System.out.println("**************************************************");

                                System.out.println(allData);

                                System.out.println("**************************************************");


                                switch (v.getId())
                                {

                                    case R.id.swipecard:

                                        item_position = allData.indexOf("Card-4");
                                        selected_child_name = "Search Card-1";
                                        break;
                                    case R.id.chipreading:
                                        item_position = allData.indexOf("Card-4");
                                        selected_child_name = "EMV Process-2";
                                        break;
                                    case R.id.scancard:
                                        item_position = allData.indexOf("Card-4");
                                        selected_child_name = "Search Card-1";
                                        break;
                                    case R.id.qrcodescan:
                                        item_position = allData.indexOf("Scan-6");
                                        selected_child_name = "Scan QRcode";

                                        break;
                                    case R.id.print:
                                        item_position = allData.indexOf("Print-7");
                                        selected_child_name = "Text-2";
                                        break;
                                    case R.id.applepay:
                                        item_position = allData.indexOf("Card-4");
                                        selected_child_name = "Search Card-1";
                                        break;
                                }


                                actionName = allData.get(item_position);
                                if(BaseAction.actionName.containsKey(actionName)){
                                    BaseAction object =BaseAction.actionName.get(actionName);
                                    try {
                                        object.action(actionName,mContext);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    if(!dirs.contains(actionName)){
                                        dirs.add(actionName);
                                    }
                                    getChildByDirs();
                                }
                                showCurrentPosition();


                            }
                        };
                        qrcodescan.setOnClickListener(listener);
                        chipreading.setOnClickListener(listener);
                        swipecard.setOnClickListener(listener);
                        scancard.setOnClickListener(listener);
                        print.setOnClickListener(listener);
                        applepay.setOnClickListener(listener);

                    }
                }, 2000);

            }
        },3000);

    }

    @Override
    protected void onResume(){
        super.onResume();
        if(initializedone) {
            lvMsg = findViewById(R.id.lv_msg);
            mAdapter = new MsgAdapter(MainActivity.this, KivviApplication.msgData);
            lvMsg.setAdapter(mAdapter);
            refreshListView();
        }
    }

    public void reloadOnResume( )
    {
        lvMsg = findViewById(R.id.lv_msg);
        mAdapter = new MsgAdapter(MainActivity.this, KivviApplication.msgData);
        lvMsg.setAdapter(mAdapter);
        refreshListView();
    }

    private void initView(){
        mContext =this ;
        lvCmd = (ListView) findViewById(R.id.my_list_view);
        mBtnClearLog=(Button)findViewById(R.id.btn_clear_lv);
        tvShowPosition=(TextView)findViewById(R.id.showCurrentPosition);
        imageView = (ImageView) findViewById(R.id.showHandImage);
        tvShowPosition.setText(getString(R.string.position)+getString(R.string.root));
        mBtnClearLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KivviApplication.msgData.clear();
                refreshListView();
            }
        });
        ArrayList<String> parent = BaseAction.getParent();
        for (int i = 0; i < parent.size(); i++) {
            allData.add(parent.get(i));
        }
        refreshGridView();
        BaseAction.setMsgPrint(new BaseAction.MsgPrint() {
            @Override
            public void onMsgPrint(String msg) {
                KivviApplication.msgData.add(msg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshListView();
                    }
                });
                if(msg.contains("-")){
                    String s = msg.split("-")[1];
                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(s);
                        Bitmap bitmap  = BitmapFactory.decodeStream(fis);
                        imageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        lvCmd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                actionName = allData.get(i);
                //先判断是目录文件还是操作事件
                if(BaseAction.actionName.containsKey(actionName)){
                    BaseAction object =BaseAction.actionName.get(actionName);
                    try {
                        object.action(actionName,mContext);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    //如果是目录文件，获取目录文件的子文件
                    if(!dirs.contains(actionName)){
                        dirs.add(actionName);
                    }
                    getChildByDirs();
                }
                 showCurrentPosition();
            }
        });

//        //Tips:
        String tip = BaseTips.Get(actionName);
        if(tip != null) {
            KivviApplication.msgData.add(tip);
        }
    }

    private void refreshListView(){
        mAdapter.notifyDataSetChanged();
        //第二个参数表示Y轴的偏移量
        lvMsg.setSelectionFromTop(ListView.FOCUS_DOWN,-10000);//刷新到底部
    }

    private void OrderActionItem(List<String> items){
        Collections.sort(items, new Comparator<String>() {
            public int compare(String arg0, String arg1) {
                int arg0_Idx = 9999;
                int arg1_Idx = 9999;
                if(arg0.contains("-")){
                    arg0_Idx = Integer.valueOf(arg0.split("-")[1]);
                }
                if(arg1.contains("-")){
                    arg1_Idx = Integer.valueOf(arg1.split("-")[1]);
                }
                return (arg0_Idx-arg1_Idx);
            }
        });
    }

    private void refreshGridView(){
        OrderActionItem(allData);
        MyAdapter myAdapter = new MyAdapter(mContext, allData);
        lvCmd.setAdapter(myAdapter);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ArrayList<String> arrayList =new ArrayList<String>();
            //后退就是目录的上一层
         /*   Toast.makeText(MainActivity.this,
                    dirs.size()+"",
                    Toast.LENGTH_LONG).show(); */
                if(dirs.size()>1){
                    dirs.remove(dirs.size()-1);
                    showCurrentPosition();
                    getChildByDirs();
                    return false;
                }else{
                    dirs.clear();
                    allData = BaseAction.getParent();
                    showCurrentPosition();
                    refreshGridView();
                    reloadMenuView();
                    return false;
                }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getChildByDirs(){
        ArrayList<String> childListByParent= BaseAction.getChildListByParent(dirs);
        allData.clear();
        for (int j = 0; j < childListByParent.size(); j++) {
            allData.add(childListByParent.get(j));
        }

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

        System.out.println(allData); //for print  [QRcode-5, Text-2, Busy-10, OBcode-4, Print Test, Paper Detect-1, Feed-6, Picture-3, receipt-7, Busy-12]

           // for Card4 ->  [Search Card-1, EMV Issuing Bank Data-9, EMV Process-2, Direct Trade-8, ICCard Detect-4, CardSlot Detect-3, LoadRelateKey-11]

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");


        refreshGridView();

         item_position = allData.indexOf(selected_child_name);


        actionName = allData.get(item_position);
        //先判断是目录文件还是操作事件
        if(BaseAction.actionName.containsKey(actionName)){
            BaseAction object =BaseAction.actionName.get(actionName);
            try {
                object.action(actionName,mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //如果是目录文件，获取目录文件的子文件
            if(!dirs.contains(actionName)){
                dirs.add(actionName);
            }
            getChildByDirs();
        }
        showCurrentPosition();

    }

    private void showCurrentPosition(){
        String position= "";
        if(dirs.size()>0){
            for (int j = 0; j < dirs.size(); j++) {
                position = position +dirs.get(j)+" -> ";
            }
            if(BaseAction.actionName.containsKey(actionName)){
                position = position+actionName;
            }
        }else{
            position = getString(R.string.root);
        }
        tvShowPosition.setText(getString(R.string.position)+position);

    }

    public void reloadMenuView( )
    {

        setContentView(R.layout.activity_menu_selection);

        Button qrcodescan = (Button) findViewById(R.id.qrcodescan);
        Button chipreading = (Button) findViewById(R.id.chipreading);
        Button swipecard = (Button) findViewById(R.id.swipecard);
        Button scancard = (Button) findViewById(R.id.scancard);
        Button print = (Button) findViewById(R.id.print);
        Button applepay = (Button) findViewById(R.id.applepay);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setContentView(R.layout.activity_main);

                initializedone = true;

                initView();

                reloadOnResume();

                // [Basic Test-1, Pinpad-2, Storage-3, Card-4, Exscreen-5, Scan-6, Print-7, scangun-9, Mifare-10, pinpad]

                System.out.println("**************************************************");

                System.out.println(allData);

                System.out.println("**************************************************");


                switch (v.getId())
                {

                    case R.id.swipecard:

                        item_position = allData.indexOf("Card-4");
                        selected_child_name = "Search Card-1";
                        break;
                    case R.id.chipreading:
                        item_position = allData.indexOf("Card-4");
                        selected_child_name = "EMV Process-2";
                        break;
                    case R.id.scancard:
                        item_position = allData.indexOf("Card-4");
                        selected_child_name = "Search Card-1";
                        break;
                    case R.id.qrcodescan:
                        item_position = allData.indexOf("Scan-6");
                        selected_child_name = "Scan QRcode";

                        break;
                    case R.id.print:
                        item_position = allData.indexOf("Print-7");
                        selected_child_name = "Text-2";
                        break;
                    case R.id.applepay:
                        item_position = allData.indexOf("Card-4");
                        selected_child_name = "Search Card-1";
                        break;
                }


                actionName = allData.get(item_position);
                if(BaseAction.actionName.containsKey(actionName)){
                    BaseAction object =BaseAction.actionName.get(actionName);
                    try {
                        object.action(actionName,mContext);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    if(!dirs.contains(actionName)){
                        dirs.add(actionName);
                    }
                    getChildByDirs();
                }
                showCurrentPosition();


            }
        };
        qrcodescan.setOnClickListener(listener);
        chipreading.setOnClickListener(listener);
        swipecard.setOnClickListener(listener);
        scancard.setOnClickListener(listener);
        print.setOnClickListener(listener);
        applepay.setOnClickListener(listener);

    }
}
