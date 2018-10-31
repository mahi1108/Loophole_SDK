package com.cynovo.kivvi.demo.function.outside.action.exscreen;


import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by cynovo on 2016/6/2.
 * 小屏待机
 */
public class ActionDisplayIdle extends BaseAction {
    KivviDevice kvExScreen = new KivviDevice();

    public ActionDisplayIdle() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionExscreen)+"|"+GetString.getString(KivviApplication.getContext(),R.string.standby)+"-1");
    }

    @Override
    public void myAction(String actionName) {
        KivviDevice kvDev = new KivviDevice();
        try {
            kvDev.Action(KV.CMD.DISPLAY_IDLE);
            setMsg(GetString.getString(KivviApplication.getContext(),R.string.standby_ok));
        } catch (KvException e) {
            e.printStackTrace();
        }
    }
}
