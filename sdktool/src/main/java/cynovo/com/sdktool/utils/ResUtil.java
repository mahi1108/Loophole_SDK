package cynovo.com.sdktool.utils;

/**
 *
 *  Created by cynovo on 2016/6/1.
 *  对结果的处理
 */
public class ResUtil {

    //错误码
    public static final int RET_OK              =  0;
    public static final int ERR_NOT_OPEN        = -1;
    public static final int ERR_FAILED          = -2;
    public static final int ERR_BUSY            = -3;
    public static final int ERR_TIMEOUT         = -4;
    public static final int ERR_OUT_PAPER       = -8;
    public static final int ERR_OTHERS          = -9;

    public static String errReason(int errorCode){
        String ret = "";

        switch(errorCode){
            case ERR_NOT_OPEN:
                ret = "设备打开失败";
                break;
            case ERR_FAILED:
                ret = "指令执行失败";
                break;
            case ERR_TIMEOUT:
                ret = "等待超时";
                break;
            case ERR_OUT_PAPER:
                ret = "打印机缺纸";
                break;
            default:
                ret = "未知错误";
                break;
        }
        return ret;
    }

}
