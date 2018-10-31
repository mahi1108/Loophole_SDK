package com.cynovo.kivvi.demo.function.outside.action.printer;

import android.util.Log;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by cynovo on 2016/6/1.
 * 测试打印类
 */
public class ActionTestPrint extends BaseAction {

    public ActionTestPrint(){
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPrint)+"|"+GetString.getString(KivviApplication.getContext(),R.string.test_print));
    }

    @Override
    public void myAction(String actionName) {
            int iRet = -1;
            final KivviDevice kvDev = new KivviDevice();
//            kvDev.Set(KV.Printer.KEY_STYLE_DATATYPE, KV.Printer.STYLE_DATATYPE_CN);//设置打印数据类型，默认为中文
//            kvDev.Set(KV.Printer.KEY_STYLE_SPEED, 5); //设置打印速度，int，范围大小[1,8]
//            kvDev.Set(KV.Printer.KEY_ADD_LINE_CONTENT, "1、----__________\n");
//            iRet = kvDev.Action(KV.Printer.CMD_PRINT);
//            //kvDev.Set(KV.Printer.KEY_STYLE_POSITION, KV.Printer.STYLE_POSITION_CENTER);
//            kvDev.Set(KV.Printer.KEY_STYLE_SIZE, KV.Printer.STYLE_SIZE_DOUBLE_H);
//            kvDev.Set(KV.Printer.KEY_ADD_LINE_CONTENT, "2、字体双倍高\n再来双倍高\n");
//            iRet =kvDev.Action(KV.Printer.CMD_PRINT);
//
//            kvDev.Set(KV.Printer.KEY_STYLE_SIZE, KV.Printer.STYLE_SIZE_DOUBLE_WH);
//            kvDev.Set(KV.Printer.KEY_ADD_LINE_CONTENT, "3、字体双倍宽高\n");
//            iRet =kvDev.Action(KV.Printer.CMD_PRINT);
//
//            kvDev.Set(KV.Printer.KEY_STYLE_SIZE, KV.Printer.STYLE_SIZE_DOUBLE_W);
//            kvDev.Set(KV.Printer.KEY_ADD_LINE_CONTENT, "4、字体双倍宽\n");
//            kvDev.Action(KV.Printer.CMD_PRINT);
//
////        kvDev.Set(KV.Printer.KEY_STYLE_POSITION, KV.Printer.STYLE_POSOTION_RIGHT);
////        kvDev.Set(KV.Printer.KEY_ADD_LINE_CONTENT, "4、右对齐");
////        kvDev.Set(KV.Printer.KEY_ADD_LINE_CONTENT, "5、再来右对齐");
//
//            //居中打个二维码
//            kvDev.Set(KV.Printer.KEY_STYLE_DATATYPE, KV.Printer.STYLE_DATATYPE_BM);//设置打印数据格式为图片
//            kvDev.Set(KV.Printer.KEY_STYLE_POSITION, KV.Printer.STYLE_POSITION_CENTER);
//            Bitmap qrCode = null;
//            try {
//                qrCode = CmdOperationUtil.Create2DCode("123456789");
//                kvDev.Set(KV.Printer.KEY_ADD_LINE_BITMAP, qrCode);
//            } catch (WriterException e) {
//                e.printStackTrace();
//            }
//            kvDev.Action(KV.Printer.CMD_PRINT);
//            if(iRet == KV.RET.OK){
////                textView.setText("打印成功");
//                setMsg("打印成功");
//            }else{
////                textView.setText("打印失敗");
//                setMsg("打印失败");
//            }
            Log.i(GetString.getString(KivviApplication.getContext(),R.string.start_printing), GetString.getString(KivviApplication.getContext(),R.string.print));
        }
}
