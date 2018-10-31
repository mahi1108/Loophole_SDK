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
 * 显示用户信息
 */
public class ActionDisplayTransResult extends BaseAction {


    public ActionDisplayTransResult() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionExscreen)+"|"+GetString.getString(KivviApplication.getContext(),R.string.show_transact_result)+"-5");
    }

    @Override
    public void myAction(String actionName) {
        final KivviDevice kvDevScreen = new KivviDevice();

        try {

            kvDevScreen.Set(KV.KEY.DISPLAY_CUSTOM_MSG.REQUIRE.DISPLAYTYPE,    "picture");   //it can be "message"(default) "picture"
            kvDevScreen.Set(KV.KEY.DISPLAY_CUSTOM_MSG.REQUIRE.TRANS_RESPONSE, "auth");  //it can be "auth" "decl"


            int ret = kvDevScreen.Action(KV.CMD.DISPLAY_CUSTOM_MSG);
            if(ret == KV.RET.OK){
                setMsg("Show success, please see the front screen!");
            }else{
                setMsg("Show failed!"+ ret + "result" + kvDevScreen.GetString(KV.KEY.BASIC.RESULT));
            }
        } catch (KvException e) {
            e.printStackTrace();
        }
    }
}
