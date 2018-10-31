package com.cynovo.kivvi.demo.function.outside.action.basic;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by cynovo on 2016/6/2.
 * 获取时间
 */
public class ActionGetTime extends BaseAction {

    public ActionGetTime() {
       super(GetString.getString(KivviApplication.getContext(),R.string.actionBasic)+"|"+GetString.getString(KivviApplication.getContext(),R.string.basic_get_time)+"-3");
    }

    @Override
    public void myAction(String actionName) {
        setMsg(GetString.getString(KivviApplication.getContext(),R.string.start_getting_time));
        KivviDevice kvDev = new KivviDevice();
        try {
            //kvDev.Set(KV.KEY.OPERATE, "GET");  //默认即为GET (the default is "GET")
            int ret = kvDev.Action(KV.CMD.TIME);
            if (ret == KV.RET.OK) {
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.get_time_succeed) + kvDev.GetString(KV.KEY.TIME.RESPONSE.DATETIME));
            } else {
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.fail_in_getting_time) + ret + kvDev.GetString(KV.KEY.BASIC.RESULT));
            }
        }catch (KvException e){
            setMsg(e.getMessage());
        }
    }
}
