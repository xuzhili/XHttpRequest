package com.xuzhili.xhttprequest;

import com.xuzhili.xhttprequest.carry.XHttpRequester;

/**
 * Created by $xuzhili on 2018/6/5.
 * hnzkxuzhili@gmail.com
 */

public class XHttpFactory {

    public static XHttpRequester createRequester(String url) {
        return new XHttpRequester(url);
    }

}
