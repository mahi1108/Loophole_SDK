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
public class ActionDisplayCustomMsg extends BaseAction {


    public ActionDisplayCustomMsg() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionExscreen)+"|"+GetString.getString(KivviApplication.getContext(),R.string.display_UserInfo)+"-3");
    }

    @Override
    public void myAction(String actionName) {
        final KivviDevice kvDevScreen = new KivviDevice();


        String msgInfo = "Processing \n " +
                " \n " +
                "Please Wait...\n " +
                "CYNOVO \n " +
                "Test Demo";           //自定义消息，'\n'表示换行 ，文字默认居中。
        try {

            kvDevScreen.Set(KV.KEY.DISPLAY_CUSTOM_MSG.REQUIRE.FONT_SIZE,  16);   //可设置为8，10，13，16，24 默认24
            kvDevScreen.Set(KV.KEY.DISPLAY_CUSTOM_MSG.REQUIRE.FONT_COLOR, 0xFFFF00);  //字体颜色， 默认黑色
            kvDevScreen.Set(KV.KEY.DISPLAY_CUSTOM_MSG.REQUIRE.BK_COLOR,   0x000000);    //背景颜色， 默认蓝色
            kvDevScreen.Set(KV.KEY.DISPLAY_CUSTOM_MSG.REQUIRE.MESSAGE,    msgInfo);
            kvDevScreen.Set(KV.KEY.DISPLAY_CUSTOM_MSG.REQUIRE.TIMEOUT,    5);

            int ret = kvDevScreen.Action(KV.CMD.DISPLAY_CUSTOM_MSG);
            if(ret == KV.RET.OK){
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.display_ok));
            }else{
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.failed_error_code_is) + ret + GetString.getString(KivviApplication.getContext(),R.string.error_is) + kvDevScreen.GetString(KV.KEY.BASIC.RESULT));
            }
        } catch (KvException e) {
            e.printStackTrace();
        }
    }
}
