package com.cynovo.kivvi.demo.function.outside.action.basic;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by Martin on 2017/3/31.
 */

public class ActionStop extends BaseAction {
    /**
     * child 卡片/搜卡 （按照类似这种结构组装,名字不能重复）
     */
    public ActionStop() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionBasic)+"|"+GetString.getString(KivviApplication.getContext(),R.string.stop)+"-5");
    }

    private String GetString(int id)
    {
        return GetString.getString(KivviApplication.getContext(),id);
    }

    @Override
    public void myAction(String actionName) {
        setMsg(GetString(R.string.stop_action));
        KivviDevice kvDev = new KivviDevice();
        try {
            int ret = kvDev.Action(KV.CMD.STOP_ACTION);
            if (ret == KV.RET.OK) {
                setMsg(GetString(R.string.stop_success));
            } else {
                setMsg(GetString(R.string.no_task_run));
            }
        }catch (KvException e){
            setMsg(e.getMessage());
        }
    }
}
