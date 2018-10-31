package com.cynovo.kivvi.demo.function.outside.action.pinpad.other;

import android.os.Message;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by eddard on 17-11-21.
 */

public class ActionConfirmUI extends BaseAction {

    String msg ="";

    public ActionConfirmUI() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPinpad)+"|"+GetString.getString(KivviApplication.getContext(),R.string.loadkey_other)+"|"+"confirmui"+"-6");
    }

    private android.os.Handler handler = new android.os.Handler() {
        // 在Handler中获取消息，重写handleMessage()方法
        @Override
        public void handleMessage(Message msg) {
            cancelDialog();
            setMsg(msg.obj.toString());
        }
    };

    @Override
    public void myAction(final String actionName) {
        final KivviDevice kvDev = new KivviDevice();
        showDialog(true,kvDev);
        setMsg("Please choose");
        new Thread(){
            public void run() {
                try {

                    kvDev.Set("funtype",  "confirm");
                    kvDev.Set("NameBT0", "Yes");
                    kvDev.Set("NameBT1",  "NO");
                    kvDev.Set("Textmsg", "Do you confirm?");
                    kvDev.Set("BackColor", 0xdcdcdc);
                    kvDev.Set("ButtonColor", 0xe0e0e0);

                    int ret = kvDev.Action("pinpad.cmd.confirmui");
                    if(ret == KV.RET.OK){
                        int ButtonId = kvDev.GetInt("ButtonId");
                        switch(ButtonId)
                        {
                            case 0:
                                    msg ="you choose yes";
                                break;
                            case 1:
                                    msg ="you choose no";
                                break;
                            default:
                                     msg ="you did not choose!";
                                break;
                        }

                    }else if(ret == KV.RET.CANCELED){
                        msg = "User cancel!";
                    }else{
                        msg = GetString.getString(KivviApplication.getContext(),R.string.show_balance_fail) + ret + GetString.getString(KivviApplication.getContext(),R.string.error_is) + kvDev.GetString(KV.KEY.BASIC.RESULT);
                    }
                } catch (KvException e) {
                    e.printStackTrace();
                }
                Message message = Message.obtain();
                message.obj =msg;
                handler.sendMessage(message);
            }
        }.start();
    }

}
