package com.cynovo.kivvi.demo.function.outside.action.basic;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by Craftsman on 2016/11/14.
 */
public class ActionIsBusy extends BaseAction {
    /**
     * child 卡片/搜卡 （按照类似这种结构组装,名字不能重复）
     */
    public ActionIsBusy() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionBasic)+"|"+GetString.getString(KivviApplication.getContext(),R.string.basic_isbusy)+"-11");
    }

    @Override
    public void myAction(String actionName) {
        KivviDevice kvDev = new KivviDevice();

        try {
            int ret = kvDev.Action(KV.CMD.IS_BUSY);
            if(ret == KV.RET.OK){
                if(kvDev.GetBool(KV.KEY.IS_BUSY.RESPONSE.IS_BUSY)){
                    setMsg(GetString.getString(KivviApplication.getContext(),R.string.device_isbusy));
                }else{
                    setMsg(GetString.getString(KivviApplication.getContext(),R.string.device_isnotbusy));
                }
            }else{
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.busy_state_get_fail));
            }
        } catch (KvException e) {
            e.printStackTrace();
        }
    }
}
