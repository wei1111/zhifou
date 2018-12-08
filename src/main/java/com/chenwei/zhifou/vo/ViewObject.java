package com.chenwei.zhifou.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wei1
 * @Date: Create in 2018/12/7 20:18
 * @Description:
 */
public class ViewObject {
    private Map<String, Object> objs = new HashMap<String, Object>();

    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public Object get(String key) {
        return objs.get(key);
    }
}
