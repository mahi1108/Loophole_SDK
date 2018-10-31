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
 * 图片轮播
 */
public class ActionImageCarousel extends BaseAction {
    KivviDevice kvExScreen = null;

    public ActionImageCarousel() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionExscreen)+"|"+GetString.getString(KivviApplication.getContext(),R.string.slide)+"-4");
    }


    @Override
    public void myAction(String actionName) {
        kvExScreen = new KivviDevice();

        try {
            kvExScreen.Set(KV.KEY.DISPLAY_BITMAPS.REQUIRE.CAROUSEL_INTERVAL, 4);    //轮播时间间隔，单位为秒，默认为5秒
            kvExScreen.Set(KV.KEY.DISPLAY_BITMAPS.REQUIRE.CAROUSEL_BITMAP_NUM, 3);  //轮播图片数

            int ret = kvExScreen.Action(KV.CMD.DISPLAY_BITMAPS);
            if(ret == KV.RET.OK){
                setMsg(actionName + GetString.getString(KivviApplication.getContext(),R.string.slide)+GetString.getString(KivviApplication.getContext(),R.string.success));
            }else {
                setMsg(actionName + GetString.getString(KivviApplication.getContext(),R.string.failed_error_code_is)+ ret + GetString.getString(KivviApplication.getContext(),R.string.error_is) + kvExScreen.GetString(KV.KEY.BASIC.RESULT));
            }
        } catch (KvException e) {
            e.printStackTrace();
        }
    }
}
