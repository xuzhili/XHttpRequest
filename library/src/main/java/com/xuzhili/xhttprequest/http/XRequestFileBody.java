package com.xuzhili.xhttprequest.http;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by $xuzhili on 2018/6/5.
 * hnzkxuzhili@gmail.com
 */

public class XRequestFileBody {

    private File file;

    public XRequestFileBody(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public String getFileName() {
        if (file != null) {
            return file.getName();
        }
        return null;
    }

    public RequestBody getRequestBody() {
        return RequestBody.create(MediaType.parse("image/*"), file);
    }
}
