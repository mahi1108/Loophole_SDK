package com.cynovo.kivvi.demo.function.outside.action.storage;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvi.demo.base.BaseAction;
import cynovo.com.sdktool.utils.GetString;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by cynovo on 2016/6/2.
 * 加载轮播图片
 */
public class ActionStoreBitmap extends BaseAction {
    String errMsg = "";

    private android.os.Handler handler = new android.os.Handler() {
        // 在Handler中获取消息，重写handleMessage()方法
        @Override
        public void handleMessage(Message msg) {
            int number =msg.getData().getInt("number", -1);
            String msgContent ="";
            if(msg.what == KV.RET.OK){
                msgContent += GetString.getString(KivviApplication.getContext(),R.string.load_the) + number + GetString.getString(KivviApplication.getContext(),R.string.success);
            }else{
                cancelDialog();
                msgContent += GetString.getString(KivviApplication.getContext(),R.string.load_the) + number + GetString.getString(KivviApplication.getContext(),R.string.failure);
            }
            if(number !=3 ){
                msgContent+= GetString.getString(KivviApplication.getContext(),R.string.start_load_the_number) + (number+1) + GetString.getString(KivviApplication.getContext(),R.string.picture);
            }else{
                cancelDialog();
                msgContent+= GetString.getString(KivviApplication.getContext(),R.string.picture_load_ok);
            }
            setMsg(msgContent);
        }
    };

    public ActionStoreBitmap() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionStorage)+"|"+GetString.getString(KivviApplication.getContext(),R.string.load_slide)+"-2");
    }

    private int StorageImage(int idx, byte[] filedata){
        KivviDevice kvDev = new KivviDevice();
        int ret = 0;

        try {
            kvDev.Set(KV.KEY.STORE_BITMAP.REQUIRE.INDEX, idx);
            kvDev.Set(KV.KEY.STORE_BITMAP.REQUIRE.FILE_LEN, filedata.length);
            kvDev.Set(KV.KEY.STORE_BITMAP.REQUIRE.FILE_DATA, filedata);

            ret = kvDev.Action(KV.CMD.STORE_BITMAP);
            if(ret != KV.RET.OK){
                errMsg = kvDev.GetString(KV.KEY.BASIC.RESULT);
            }
        } catch (KvException e) {
            e.printStackTrace();
        }
        return ret;
    }


    @Override
    public void myAction(String actionName){
        setMsg(GetString.getString(KivviApplication.getContext(),R.string.loading_the_3rd_picture)+GetString.getString(KivviApplication.getContext(),R.string.Expend_150_second));
        //Bitmap准备

        InputStream input1 = null;
        InputStream input2 = null;
        InputStream input3 = null;
        try {
            input1= KivviApplication.getContext().getResources().getAssets().open("image1.bmp");
            input2= KivviApplication.getContext().getResources().getAssets().open("image2.bmp");
            input3= KivviApplication.getContext().getResources().getAssets().open("image3.bmp");
        } catch (IOException e) {
            e.printStackTrace();
        }

        final byte[] fileData[] = new byte[3][];
        try {
            fileData[0] = readStream(input1);
            fileData[1] = readStream(input2);
            fileData[2] = readStream(input3);
        } catch (Exception e) {
            e.printStackTrace();
        }


        new Thread(){
            public void run(){
                int ret = -1;
                //加载图片比较耗时，每张图耗时约50秒，请耐心等待
                for(int i = 0; i < 3; i++){

                    ret = StorageImage(i, fileData[i]);
                    Log.d("KvDevDEMO", "Store Bitmap 001 " + ret);
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putInt("number",i+1);
                    msg.what = ret;
                    msg.setData(bundle);//mes利用Bundle传递数据
                    handler.sendMessage(msg);
                }
            }
        }.start();

    }

    /**
     * @param //将图片内容解析成字节数组
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;

    }
}
