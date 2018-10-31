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

                        Button readCard = (Button) findViewById(R.id.readCard);
                        Button swipeCard = (Button) findViewById(R.id.swipeCard);
                        Button enterPin = (Button) findViewById(R.id.enterPin);
                        Button print = (Button) findViewById(R.id.print);
                        Button qrCode = (Button) findViewById(R.id.qrCode);

                        View.OnClickListener listener = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                setContentView(R.layout.activity_main);

                                initializedone = true;

                                initView();

                                reloadOnResume();

                            }
                        };
                        readCard.setOnClickListener(listener);
                        swipeCard.setOnClickListener(listener);
                        enterPin.setOnClickListener(listener);
                        print.setOnClickListener(listener);
                        qrCode.setOnClickListener(listener);

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
       refreshGridView();
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

        Button readCard = (Button) findViewById(R.id.readCard);
        Button swipeCard = (Button) findViewById(R.id.swipeCard);
        Button enterPin = (Button) findViewById(R.id.enterPin);
        Button print = (Button) findViewById(R.id.print);
        Button qrCode = (Button) findViewById(R.id.qrCode);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setContentView(R.layout.activity_main);

                initializedone = true;

                initView();

                reloadOnResume();

            }
        };
        readCard.setOnClickListener(listener);
        swipeCard.setOnClickListener(listener);
        enterPin.setOnClickListener(listener);
        print.setOnClickListener(listener);
        qrCode.setOnClickListener(listener);
    }
}
