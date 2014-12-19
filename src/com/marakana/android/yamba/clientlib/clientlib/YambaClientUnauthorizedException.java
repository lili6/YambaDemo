
package com.marakana.android.yamba.clientlib.clientlib;

//import com.marakana.android.yamba.clientlib.YambaClientException;

public class YambaClientUnauthorizedException extends YambaClientException {

    private static final long serialVersionUID = -3792023133642909075L;

    public YambaClientUnauthorizedException(String detailMessage) {
        super(detailMessage);
    }

    public YambaClientUnauthorizedException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
