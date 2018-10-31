package com.cynovo.kivvi.demo.function.outside.action.pinpad;

import android.os.Message;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by cynovo on 2016/6/2.
 * 显示余额
 */
public class ActionDispBalance extends BaseAction {

    String msg ="";

    public ActionDispBalance() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPinpad)+"|"+GetString.getString(KivviApplication.getContext(),R.string.show_balance)+"-7");
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
        setMsg(GetString.getString(KivviApplication.getContext(),R.string.showing_balance_please_click_ok));
        new Thread(){
            public void run() {
                try {
                    kvDev.Set(KV.KEY.PINPAD_MSG_CONFIRM.REQUIRE.DATA_TYPE, "BALANCE");
                    kvDev.Set(KV.KEY.PINPAD_MSG_CONFIRM.REQUIRE.MSG_LINE1, "-6500.23");
                    kvDev.Set(KV.KEY.PINPAD_MSG_CONFIRM.REQUIRE.TIMEOUT, 5);

                    int ret = kvDev.Action(KV.CMD.PINPAD_MSG_CONFIRM);
                    if(ret == KV.RET.OK){
                        msg =GetString.getString(KivviApplication.getContext(),R.string.show_balance_ok);
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
