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
public class ActionLedTest extends BaseAction {

    /**
     * child 卡片/搜卡 （按照类似这种结构组装,名字不能重复）
     */
    public ActionLedTest() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionBasic)+"|"+GetString.getString(KivviApplication.getContext(),R.string.basic_led_test)+"-10");
    }

    private int LedSwitch(String leds, String operate){
        int ret = -1;
        KivviDevice kvDev = new KivviDevice();

        try {
            kvDev.Set(KV.KEY.LED_SWITCH.REQUIRE.LED, leds);
            kvDev.Set(KV.KEY.LED_SWITCH.REQUIRE.OPERATE, operate);

            ret = kvDev.Action(KV.CMD.LED_SWITCH);
        } catch (KvException e) {
            e.printStackTrace();
        }

        return ret;
    }

    private int LedControl(String leds, int keepMS){
        int ret = -1;

        ret = LedSwitch(leds, "ON");

        try {
            Thread.sleep(keepMS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ret = LedSwitch(leds, "OFF");

        return ret;
    }

    @Override
    public void myAction(String actionName) {
        setMsg("闪灯开始");

        LedControl("RED", 50);
        LedControl("GREEN", 50);
        LedControl("YELLOW", 50);
        LedControl("BLUE", 50);
        LedControl("DRED", 50);
        LedControl("DGREEN", 50);

        LedControl("RED|GREEN|YELLOW|BLUE|DRED|DGREEN", 200);

        setMsg("闪灯结束");
    }
}
