package com.cynovo.kivvi.demo.function.outside.action.card;

import android.util.Log;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by lucy on 2016/7/8.
 */
public class ActionSlot extends BaseAction {

    public ActionSlot() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionCard)+"|"+GetString.getString(KivviApplication.getContext(),R.string.CardSlot_detect));
    }

    @Override
    public void myAction(String actionName) {
        final KivviDevice kvDev = new KivviDevice();
        String msg = "";
        int ret = -1;
        lock();
        try {
            ret = kvDev.Action(KV.CMD.DETECT_IC_SLOT);
            Log.d("KvDevDEMO", "IcSlot " + ret);
            unlock();
            if (ret == KV.RET.OK) {
                int withCardInSlot = kvDev.GetInt(KV.KEY.DETECT_IC_SLOT.RESPONSE.ICCARD_INSLOT);

                if (withCardInSlot == 1) {
                    msg = GetString.getString(KivviApplication.getContext(),R.string.slot_detected_successfully)+"\n";
                } else {
                    //textView.setText("IC卡检测成功，未检测到卡片!\n");
                    msg = GetString.getString(KivviApplication.getContext(),R.string.card_not_found)+"\n";
                }
            } else {
                msg = GetString.getString(KivviApplication.getContext(),R.string.failed_error_code_is) + ret;
            }
        }catch (KvException e){
            unlock();
            setMsg(e.getMessage());
        }
        setMsg(msg);
    }

}
