package com.moxie.cloud.services.server.util;

import java.util.HashMap;

/**
 *
 */
public class MatchOutMap<K, V> extends HashMap<K, V> {
    private int matchFieldType = 0;

    public int getMatchFieldType() {
        return matchFieldType;
    }

    public void setMatchFieldType(int matchFieldType) {
        this.matchFieldType = matchFieldType;
    }


}
