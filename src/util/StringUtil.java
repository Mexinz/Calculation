package util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Mexinz
 * @Date: 2019/10/8
 */
public class StringUtil {

    public static Map<String,Integer> getParamMap1(String[] param) {
        Map<String,Integer> paramMap = new HashMap<>(2);
        paramMap.put(param[0],Integer.valueOf(param[1]));
        paramMap.put(param[2],Integer.valueOf(param[3]));
        return paramMap;
    }

    public static Map<String,String> getParamMap2(String[] param) {
        Map<String,String> paramMap = new HashMap<>(2);
        paramMap.put(param[0],param[1]);
        paramMap.put(param[2],param[3]);
        return paramMap;
    }
}
