package cynovo.com.sdktool.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Craftsman on 2016/11/15.
 */
public class BaseTips {
    private static Map<String, String> allTips = new HashMap();

    public BaseTips(String name, String tip){
        allTips.put(name, tip);
    }

    public static String Get(String name){
        return allTips.get(name);
    }
}
