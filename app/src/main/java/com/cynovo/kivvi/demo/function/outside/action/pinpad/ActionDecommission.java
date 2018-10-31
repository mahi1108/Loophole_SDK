package com.cynovo.kivvi.demo.function.outside.action.pinpad;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvi.demo.function.outside.action.pinpad.loadKey.DUKPT.initkeyPlain.ActionLoadKEY;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by cynovo on 2016/6/2.
 * authorize
 */
public class ActionDecommission extends BaseAction {

    public ActionDecommission() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPinpad)+"|"+GetString.getString(KivviApplication.getContext(),R.string.destroy_key)+"-6");
    }

    @Override
    public void myAction(String actionName) {

        final KivviDevice kvDevPinpad = new KivviDevice();
        ActionLoadKEY.DUKPTloaded = false;

        try {
            int ret = kvDevPinpad.Action(KV.CMD.KEY_DECOMMISSION);
            if(ret == KV.RET.OK){
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.decommission_ok));
            }else{
                String errMsg = GetString.getString(KivviApplication.getContext(),R.string.decommission_fail) + ret + "\n";
                try {
                    errMsg += GetString.getString(KivviApplication.getContext(),R.string.error_is) + kvDevPinpad.GetString(KV.KEY.BASIC.RESULT);
                } catch (KvException e) {
                    e.printStackTrace();
                } finally {
                    setMsg(errMsg);
                }
            }
        } catch (KvException e) {
            e.printStackTrace();
        }
    }
}
