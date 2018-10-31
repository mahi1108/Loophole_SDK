package cynovo.com.sdktool.utils;

import java.util.Locale;

/**
 * Created by George Chen on 2015/12/15.
 */
public class DataConvert {
    public static int MIN(int a, int b){
        return (a < b) ? a : b;
    }

    public static int MAX(int a, int b){
        return (a > b) ? a : b;
    }

    public static String Amount12bToDisp(String amount12b){
        String strAmount = "" + Long.valueOf(amount12b.substring(0, 10)) + "." + amount12b.substring(10, 12);
        return strAmount;
    }

    public static String AmountDispTo12b(String strAmount){
        String result;
        if(strAmount.contains(".")){
            String[] tmp = strAmount.split("\\.");
            result = String.format(Locale.getDefault(), "%010d", Long.valueOf(tmp[0]));
            switch(tmp[1].length()){
                case 0: result += "00";  break;
                case 1: result += (tmp[1] + "0"); break;
                case 2: result += tmp[1]; break;
                default: result += tmp[1].substring(0, 2); break;
            }
        }else{
            result = String.format(Locale.getDefault(), "%010d00", Long.valueOf(strAmount));
        }

        return result;
    }

    public static String ByteArrayToHexString(byte[] byteArray){
        return ByteArrayToHexString(byteArray, 0, byteArray.length);
    }

    public static String ByteArrayToHexString(byte[] byteArray, int length){
        return ByteArrayToHexString(byteArray, 0, length);
    }

    public static String ByteArrayToHexString(byte[] byteArray, int beginIdx, int length){
        if(byteArray == null){
            return null;
        }

        String strOut = new String();

        length = MIN(length, byteArray.length - beginIdx);
        int endIdx = beginIdx + length;

        if(endIdx > 1024)
            endIdx = 1024;

        for(int i = beginIdx; i < endIdx; i++) {
            strOut += String.format("%02X", byteArray[i]);
        }

        return strOut;
    }

    public static String IntToHexString(int data){
        String strOut = new String();

        strOut += String.format("%02X", data);

        if(strOut.length()%2 != 0){
            strOut = "0" + strOut;
        }

        return strOut;
    }

    public static byte[] SubByteArray(byte[] data, int beginIdx, int length){
        if(data == null || beginIdx < 0 || beginIdx > data.length){
            return null;
        }

        length = MIN(length, data.length - beginIdx);

        byte[] subData = new byte[length];

        for(int i = 0; i < length; i++){
            subData[i] = data[beginIdx+i];
        }

        return subData;
    }

    private static byte _HexCharToByte(char c) {
        if(c >= 'a' && c <='f'){
            c -= 32;
        }
        byte b = (byte)"0123456789ABCDEF".indexOf(c);
        return b;
    }

    public static byte[] HexStringToByteArray(String hex) {
        int len = (hex.length() / 2);
        byte[] res = new byte[len];
        char[] c = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            res[i] = (byte)(_HexCharToByte(c[pos])<<4 | _HexCharToByte(c[pos+1]));
        }

        return res;
    }
}

