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
 * Created by cynovo on 2016/6/2.
 * ic卡检测
 */
public class ActionIcslot extends BaseAction {

    public ActionIcslot() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionCard)+"|"+GetString.getString(KivviApplication.getContext(),R.string.ICCard_detection));
    }

    @Override
    public void myAction(String actionName) {
        final KivviDevice kvDev = new KivviDevice();
        String msg = "";
        int ret = -1;
        lock();
        try {
            kvDev.Set(KV.KEY.DETECT_IC_SLOT.REQUIRE.IS_ICCARD, 1);
            ret = kvDev.Action(KV.CMD.DETECT_IC_SLOT);

            Log.d("KvDevDEMO", "IcSlot " + ret);
            unlock();
            if (ret == KV.RET.OK) {
                int withCardInSlot = kvDev.GetInt(KV.KEY.DETECT_IC_SLOT.RESPONSE.ICCARD_INSLOT);
                Log.d("ICtest","IC"+withCardInSlot);
                if (withCardInSlot == 1) {
                    int isICCard = kvDev.GetInt(KV.KEY.DETECT_IC_SLOT.RESPONSE.IS_ICCARD);
                    if (isICCard == 1) {
                        Log.d("isICCard","IC"+isICCard);

                        msg = GetString.getString(KivviApplication.getContext(),R.string.succeed_to_detect_IC_card)+"\n"+GetString.getString(KivviApplication.getContext(),R.string.is_IC_card)+"\n";
                    } else {
                        msg = GetString.getString(KivviApplication.getContext(),R.string.succeed_to_detect_IC_card)+"\n"+GetString.getString(KivviApplication.getContext(),R.string.not_IC_card_or_wrong_side)+"\n";
                    }
                } else {
                    //textView.setText("IC卡检测成功，未检测到卡片!\n");
                    msg = GetString.getString(KivviApplication.getContext(),R.string.succeed_to_detect_IC_card)+"\n"+GetString.getString(KivviApplication.getContext(),R.string.not_found_card)+"\n";
                }
            } else {
                msg = GetString.getString(KivviApplication.getContext(),R.string.fail_to_detect_IC_card)+GetString.getString(KivviApplication.getContext(),R.string.error_is) + ret;
            }
        }catch (KvException e){
            unlock();
            setMsg(e.getMessage());
        }
        setMsg(msg);
    }

}
