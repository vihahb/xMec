package com.xtelsolution.xmec.listener;

/**
 * Created by phimau on 2/22/2017.
 */

public interface UploadFileListener {
    void onSuccess(String url);

    void onError(String e);
}
