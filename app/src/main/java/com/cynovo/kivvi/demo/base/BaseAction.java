package com.cynovo.kivvi.demo.base;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.ui.view.progressdialog.SpotsDialog;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cynovo.com.sdktool.utils.GetString;
import cynovo.com.sdktool.utils.models.TreeModel;

/**
 * Created by cynovo on 2016/6/1.
 */
public class BaseAction extends Object{
    public static Map<String,BaseAction> menu= new HashMap<String,BaseAction>(); //加入一级二级菜单
    private  String child;
    private static MsgPrint mMsgPrint;
    private static boolean flag = false ;//标志是否阻塞当前操作,false表示没有阻塞
    public static Map<String,ArrayList<String>> menuRelation= new HashMap<String,ArrayList<String>>(); //加入一级二级菜单的对应关系
    //前面是二級菜單，後面是對應的操作類
    public static Map<String,BaseAction> actionName = new HashMap<String,BaseAction>();  //对应子菜单和事件之间的关系
    private static TreeModel treeModel =new TreeModel();
    private Context context;
    private SpotsDialog dialog;
    public int PinAppId = 2;
    public int PinAppId_1 = 1;


    /**
     * child 卡片/搜卡 （按照类似这种结构组装,名字不能重复）
     * @param child
     */
    public BaseAction(String child){
        this.child =child;
        menu.put(child, this);
    }

    public String getChild(){
        return child;
    }

    public Context getContext(){
        return context;
    }

    public void action(String action,Context context) throws Exception {
        if(flag){
            //如果事件被阻塞，不往下执行
            return;
        }
        this.context=context;
        setMsg(GetString.getString(context,R.string.is_running) + actionName.get(action).getClass().getName());
        myAction(action);
    }

    public void myAction(String actionName) throws Exception {
    }

    public void lock(){
        //开始执行操作的时候锁定
        flag = true;
    }

    public void unlock(){
        //操作结束后解锁
        flag = false;
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    public void setMsg(String msg){
        mMsgPrint.onMsgPrint(msg);
    }

    public static void setMsgPrint(MsgPrint msgPrint){
        mMsgPrint = msgPrint;
    }

    public static interface MsgPrint{
        void onMsgPrint(String msg);
    }

    /**
     *
     * @param showDialog 是否需要stop按钮
     * @param device
     */
    public void showDialog(boolean showDialog, final KivviDevice device){
        dialog =new SpotsDialog(context, showDialog, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(device!=null){
                            device.Stop();
                        }
                        //取消操作之后，将操作解锁
                        unlock();
                        dialog.dismiss();
                    } catch (KvException e) {
                        e.printStackTrace();
                    }
                }
            });
            dialog.show();
    }
    public void cancelDialog(){
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    public static void executeMenuRelation(){
        int i;
        ArrayList<String> list = new ArrayList<String>();
        Iterator iterator = menu.keySet().iterator();
        while (iterator.hasNext()) {
            String key =(String) iterator.next();
            Log.i("key===",key);
            String s[] =key.split("\\|");
            for(i =0; i<s.length; i++){
                if(i == s.length-1) {
                    treeModel.addItem(s[i], menu.get(key));
                    actionName.put(s[i], menu.get(key));
                    break;
                }
                if(treeModel.entry(s[i])){
                }else{
                    treeModel.addTree(s[i]);
                    treeModel.entry(s[i]);
                }
            }
            treeModel.returnRoot();
        }
        //treeModel.print(treeModel, "root");
    }

    public static ArrayList<String> getChildListByParent(ArrayList<String> parent){
        ArrayList<String> item =new ArrayList<String>();
        for (int i = 0; i < parent.size() ; i++) {
            if(!parent.get(i).equals("root")){
                if(treeModel.entry(parent.get(i))){
                    List<String> treeList = treeModel.getTree();
                    List<String> itemList = treeModel.getItem();
                    if(i==parent.size()-1){
                        item =(ArrayList<String>)itemList;
                        if(treeList.size()>0){
                            item.addAll(treeList);
                        }
                    }
                }
            }
        }
        treeModel.returnRoot();
        return  item;
    }

    public static ArrayList<String> getParent() {
        ArrayList<String> parent = new ArrayList<String>();
        List<String> treeList = treeModel.getTree();
        Log.i("========", treeList.toString());
        parent = (ArrayList<String>) treeList;
        return parent;
    }

}