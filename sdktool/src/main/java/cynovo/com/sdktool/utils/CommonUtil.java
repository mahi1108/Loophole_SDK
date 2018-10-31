package cynovo.com.sdktool.utils;

import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by cynovo on 2016/6/1.
 */
public class CommonUtil {
    public static String mapEmvDataType(String dataTypeCode){
        String strDataType = null;
        if(dataTypeCode.equals("F0")){
            strDataType = "交易请求";
        }else if(dataTypeCode.equals("F1")){
            strDataType = "交易确认";
        }else if(dataTypeCode.equals("F2")){
            strDataType = "交易冲正";
        }else if(dataTypeCode.equals("FB")){
            strDataType = "离线交易";
        }else if(dataTypeCode.equals("10")){
            strDataType = "交易批准";
        }else if(dataTypeCode.equals("11")){
            strDataType = "交易拒绝";
        }else if(dataTypeCode.equals("12")){
            strDataType = "交易终止";
        }else if(dataTypeCode.equals("13")){
            strDataType = "交易拒绝，不允许服务";
        }else if(dataTypeCode.equals("20")){
            strDataType = "脱机批准";
        }else if(dataTypeCode.equals("21")){
            strDataType = "脱机拒绝";
        }else if(dataTypeCode.equals("30")){
            strDataType = "上电失败";
        }

        return strDataType;
    }

    public static void scroll2Bottom(final ScrollView scroll, final View inner) {
            scroll.postDelayed(new Runnable() {
            @Override public void run() {
                Log.i("scroll_to_bottom","scrolling");
                scroll.fullScroll(View.FOCUS_DOWN);
            }
           }, 100);
    }

}
