package com.cj.model;

import java.util.HashMap;

/**
 * 操作消息提醒
 */
public class AjaxResult extends HashMap<String,Object> {

    public static  final String CODE_TAG="code";

    public static final String MSG_TAG="msg";

    public static final String DATA_TAG="data";

    public AjaxResult() {
    }

    public AjaxResult(int code, String msg) {
        super.put(CODE_TAG,code);
        super.put(MSG_TAG,msg);
    }


}
