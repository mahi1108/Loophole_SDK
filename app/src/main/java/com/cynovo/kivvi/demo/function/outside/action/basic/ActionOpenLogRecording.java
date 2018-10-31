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
public class ActionOpenLogRecording extends BaseAction {
    /**
     * child 卡片/搜卡 （按照类似这种结构组装,名字不能重复）
     */
    public ActionOpenLogRecording() {
        super(GetString.getString(KivviApplication.getContext(), R.string.actionBasic)+"|"+GetString.getString(KivviApplication.getContext(),R.string.basic_openLogRecord)+"-6");
    }

    @Override
    public void myAction(String actionName) {
        KivviDevice kvDev = new KivviDevice();

        try {
            kvDev.Set(KV.KEY.LOG_STATUE.REQUIRE.LOGRECORD, "open");
            int ret = kvDev.Action(KV.CMD.LOG_STATUE);
            if(ret == KV.RET.OK){
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.open_logrecord_success));
            }else{
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.open_logrecord_failed));
            }
        } catch (KvException e) {
            e.printStackTrace();
        }
    }
}
