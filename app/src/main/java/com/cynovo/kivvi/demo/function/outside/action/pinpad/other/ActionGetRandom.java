package com.cynovo.kivvi.demo.function.outside.action.pinpad.other;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;
import com.cynovo.kivvidevicessdk.Utils.F;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by cynovo on 2016/6/2.
 * 取随机数
 */
public class ActionGetRandom extends BaseAction {
    KivviDevice kvDev = null;

    Handler handler = new Handler() {
        // 在Handler中获取消息，重写handleMessage()方法
        @Override
        public void handleMessage(Message message) {
            Log.i("message====",message.obj.toString());
            setMsg(message.obj.toString());
        }
    };

    public ActionGetRandom(){
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPinpad)+"|"+GetString.getString(KivviApplication.getContext(),R.string.loadkey_other)+"|"+GetString.getString(KivviApplication.getContext(),R.string.RandomNumber)+"-1");
    }

    @Override
    public void myAction(final String actionName) {
        kvDev = new KivviDevice();

        int ret = 0;

        try {
            kvDev.Set(KV.KEY.GET_RANDOM.REQUIRE.REQ_LENGTH, 20);  //20字节的随机数， 最大一次可取3K字节的随机数

            ret = kvDev.Action(KV.CMD.GET_RANDOM);
            if(ret == KV.RET.OK){
                byte[] byteRandom = kvDev.GetByteArray(KV.KEY.GET_RANDOM.RESPONSE.DATA);

                setMsg(GetString.getString(KivviApplication.getContext(),R.string.RandomNumber) + F.ByteArrayToHexString(byteRandom));
            }else{
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.get_random_number_fail) + ret + GetString.getString(KivviApplication.getContext(),R.string.error_is) + kvDev.GetString(KV.KEY.BASIC.RESULT));
            }
        } catch (KvException e) {
            e.printStackTrace();
        }
    }
}
