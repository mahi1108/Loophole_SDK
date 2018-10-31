package com.cynovo.kivvi.demo.function.outside.action.pinpad.other;

import android.os.Message;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;
import com.cynovo.kivvidevicessdk.Utils.F;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by eddard on 17-11-21.
 */

public class ActionTip extends BaseAction {

    String msg ="";

    public ActionTip() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPinpad)+"|"+GetString.getString(KivviApplication.getContext(),R.string.loadkey_other)+"|"+GetString.getString(KivviApplication.getContext(),R.string.input_tip)+"-5");
    }

    private android.os.Handler handler = new android.os.Handler() {
        // 在Handler中获取消息，重写handleMessage()方法
        @Override
        public void handleMessage(Message msg) {
            cancelDialog();
            setMsg(msg.obj.toString());
        }
    };

    private static final String NORMAL= "normal";
    @Override
    public void myAction(final String actionName) {
        final KivviDevice kvDev = new KivviDevice();
        showDialog(true,kvDev);
        setMsg("Entering Tip");
        new Thread(){
            public void run() {
                try {

                    kvDev.Set(KV.KEY.PINPAD_MSG_CONFIRM.REQUIRE.DATA_TYPE,  "AMOUNT");
                    kvDev.Set(KV.KEY.PINPAD_MSG_CONFIRM.REQUIRE.MSG_LINE1, "amount: $1.00 ");
                    kvDev.Set(KV.KEY.PINPAD_MSG_CONFIRM.REQUIRE.MSG_LINE2,  " ");
                    kvDev.Set(KV.KEY.PINPAD_MSG_CONFIRM.REQUIRE.MAXLEN, 12);
                    kvDev.Set(KV.KEY.PINPAD_MSG_CONFIRM.REQUIRE.PINPAD_MODE, NORMAL);
                    kvDev.Set(KV.KEY.PINPAD_MSG_CONFIRM.REQUIRE.TIMEOUT, 60);

                    int ret = kvDev.Action(KV.CMD.PINPAD_MSG_CONFIRM);
                    if(ret == KV.RET.OK){
                        byte[] byteTip = kvDev.GetByteArray(KV.KEY.PINPAD_MSG_CONFIRM.RESPONSE.NUMBER);
                        msg ="tip: " +  F.ByteArrayToHexString(byteTip);
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
