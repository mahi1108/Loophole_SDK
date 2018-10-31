package com.cynovo.kivvi.demo.function.outside.action.pinpad.other;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Message;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KivviDeviceResp;
import com.cynovo.kivvidevicessdk.KivviDeviceRespListener;
import com.cynovo.kivvidevicessdk.KvException;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by Martin on 2017/3/29.
 */

public class ActionHandWrite extends BaseAction {
    KivviDevice kvDev;
    String msg="";
    /**
     * child 卡片/搜卡 （按照类似这种结构组装,名字不能重复）
     *
     * @param //child
     */
    public ActionHandWrite(){
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPinpad)+"|"+GetString.getString(KivviApplication.getContext(),R.string.loadkey_other)+"|"+GetString.getString(KivviApplication.getContext(),R.string.Signature)+"-3");
    }
    private android.os.Handler handler = new android.os.Handler() {
        // 在Handler中获取消息，重写handleMessage()方法
        @Override
        public void handleMessage(Message msg) {
            setMsg((String)msg.obj);
        }
    };

    @Override
    public void myAction(String actionName) {
        setMsg("请签名");
        kvDev = new KivviDevice();
        try {
//            kvDev.Set(KV.KEY.FILE_TYPE, "CERT");
//            kvDev.Set(KV.KEY.FILE_NAME,  "ActionStoreFile");

            int ret = kvDev.Action(KV.CMD.DIGITAL_SIGN, new KivviDeviceRespListener() {
                byte[] fileData;
                @Override
                public void onResponse(KivviDeviceResp kivviDeviceResp) {
                    try {
                        if (kivviDeviceResp.ErrCode == KV.RET.OK) {
                            fileData = kvDev.GetByteArray(KV.KEY.DIGITAL_SIGN.RESPONSE.FILE_DATA);
                            //byte转bitmap
                            Bitmap bmptemp = BitmapFactory.decodeByteArray(fileData, 0, fileData.length);
                            //镜像反转
                            Bitmap newbitmap = convertBmp(bmptemp);
                            //bitmap转byte
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            newbitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            fileData = baos.toByteArray();
                            //byte 转 文件
                            File bmpfile = getFileFromBytes(fileData, "mnt/sdcard/bmpfile.bmp");
                            msg ="签名成功" + "文件长度为" + fileData.length+"-"+bmpfile.getPath();

                        } else {
                            msg ="签名失败"+kvDev.GetString(KV.KEY.BASIC.RESULT);
                        }
                    }catch (KvException e){
                        msg = e.getMessage();
                    }

                    Message message = Message.obtain();
                    message.obj = msg;
                    handler.sendMessage(message);
                }
            });
        }catch (KvException e){
            msg = e.getMessage();
            Message message = Message.obtain();
            message.obj = msg;
            handler.sendMessage(message);
        }

    }

    private Bitmap convertBmp(Bitmap bmp){
        int w = bmp.getWidth();
        int h = bmp.getHeight();

        Bitmap convertBmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(convertBmp);
        Matrix matrix = new Matrix();
        matrix.postScale(1, -1); //镜像垂直翻转
        //  matrix.postScale(-1, 1); //镜像水平翻转
//        matrix.postRotate(180); //旋转-90度

        Bitmap newBmp = Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, true);
        cv.drawBitmap(newBmp, new Rect(0, 0,newBmp.getWidth(), newBmp.getHeight()),new Rect(0, 0, w, h), null);
        return convertBmp;
    }

    /**
     * 把字节数组保存为一个文件
     * @Author HEH
     * @EditTime 2010-07-19 上午11:45:56
     */
    private static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }


}
