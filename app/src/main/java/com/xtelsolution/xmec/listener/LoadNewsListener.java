package com.xtelsolution.xmec.listener;

import org.jsoup.nodes.Document;

/**
 * Created by HUNGNT on 3/6/2017.
 */

public interface LoadNewsListener {
    void onPrepare();

    void onSucess(Document result);

    void onError();
}
