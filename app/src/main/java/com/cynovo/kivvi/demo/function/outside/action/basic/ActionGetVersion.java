package com.cynovo.kivvi.demo.function.outside.action.basic;


import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvi.demo.base.BaseAction;
import cynovo.com.sdktool.utils.GetString;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

/**
 * Created by cynovo on 2016/6/2.
 */
public class ActionGetVersion extends BaseAction {

    public ActionGetVersion() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionBasic)+"|"+GetString.getString(KivviApplication.getContext(),R.string.get_version)+"-1");
    }

    public  String IntToHexString(int data){
        String strOut = new String();

        strOut += String.format("%02X", data);

        if(strOut.length()%2 != 0){
            strOut = "0" + strOut;
        }
        return strOut;
    }


    @Override
    public void myAction(String actionName) {
        String msg ="";

        KivviDevice kvDev = new KivviDevice();
        KivviApplication kvapp =new KivviApplication();
        try {
            int ret = kvDev.Action(KV.CMD.GET_VERSION);
            if (ret == KV.RET.OK) {
                String platform = kvDev.GetEnvironment(KV.KEY.GET_VERSION.RESPONSE.KVDEV_PLATFORM);
                msg = GetString.getString(KivviApplication.getContext(),R.string.main_devices_info);
                msg += "\n"+GetString.getString(KivviApplication.getContext(),R.string.serial_number) + kvDev.GetEnvironment(KV.KEY.GET_VERSION.RESPONSE.SERIAL_NO);
                msg += "\n"+GetString.getString(KivviApplication.getContext(),R.string.hardware_version) + kvDev.GetEnvironment(KV.KEY.GET_VERSION.RESPONSE.HW_VERSION);
                msg += "\n"+GetString.getString(KivviApplication.getContext(),R.string.SDK_version) + kvDev.GetEnvironment(KV.KEY.GET_VERSION.RESPONSE.SDK_VERSION);
                msg += "\n"+GetString.getString(KivviApplication.getContext(),R.string.KivviDevice_version) + kvDev.GetEnvironment(KV.KEY.GET_VERSION.RESPONSE.KVDEV_VERSION);
                msg += "\n"+GetString.getString(KivviApplication.getContext(),R.string.KivviDevice_platform) + platform;
                if(platform.equals("PAR6")){
                    msg += "\n"+"\n"+GetString.getString(KivviApplication.getContext(),R.string.financial_module_info);
                    msg +="\n"+GetString.getString(KivviApplication.getContext(),R.string.serial_number)+kvDev.GetString(KV.KEY.GET_VERSION.RESPONSE.KIVVI_SERIAL_NO);
                    msg +="\n"+GetString.getString(KivviApplication.getContext(),R.string.hardware_version)+kvDev.GetString(KV.KEY.GET_VERSION.RESPONSE.KIVVI_HW_VERSION);
                    msg += "\n" + GetString.getString(KivviApplication.getContext(),R.string.chip_version) + kvDev.GetString(KV.KEY.GET_VERSION.RESPONSE.CHIP_VERSION);
                    msg += "\n"+GetString.getString(KivviApplication.getContext(),R.string.firmware_version) + kvDev.GetString(KV.KEY.GET_VERSION.RESPONSE.FW_VERSION);
                    msg += "\n"+GetString.getString(KivviApplication.getContext(),R.string.Safety_trigger_register_values) + IntToHexString(kvDev.GetInt(KV.KEY.GET_VERSION.RESPONSE.KVDEV_SECURITY));
                }
                setMsg(msg);
            } else {
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.fail_to_connect_to_pos) + ret +kvDev.Get(KV.KEY.BASIC.RESULT));
            }
        }catch (KvException e){
            setMsg(e.getMessage());
        }
    }
}
