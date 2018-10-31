package com.cynovo.kivvi.demo.function.outside.action.basic;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import cynovo.com.sdktool.utils.GetString;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

/**
 * Created by Martin on 2017/3/31.
 */
public class ActionCloseScreen extends BaseAction {
    /**
     * child 卡片/搜卡 （按照类似这种结构组装,名字不能重复）
     */
    public ActionCloseScreen() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionBasic)+"|"+GetString.getString(KivviApplication.getContext(),R.string.basic_close_screen)+"-4");
    }
    @Override
    public void myAction(String actionName) {
        setMsg(GetString.getString(KivviApplication.getContext(),R.string.basic_close_screen));
        KivviDevice kvDev = new KivviDevice();
        try {
            //kvDev.Set(KV.KEY.OPERATE, "GET");  //默认即为GET (the default is "GET")
            int ret = kvDev.Action(KV.CMD.CLOSE_SCREEN);
            if (ret == KV.RET.OK) {
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.close_screen_succeed));
            } else {
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.fail_in_close_screen) + ret + kvDev.GetString(KV.KEY.BASIC.RESULT));
            }
        }catch (KvException e){
            setMsg(e.getMessage());
        }
    }
}
