
package com.marakana.android.yamba.clientlib.clientlib;

//import com.marakana.android.yamba.clientlib.YambaClientException;

public class YambaClientIOException extends YambaClientException {

    private static final long serialVersionUID = -3792023133642909075L;

    public YambaClientIOException(String detailMessage) {
        super(detailMessage);
    }

    public YambaClientIOException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
