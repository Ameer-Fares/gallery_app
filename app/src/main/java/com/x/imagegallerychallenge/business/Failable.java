package com.x.imagegallerychallenge.business;

import java.util.HashMap;

public interface Failable {
    void failed(HashMap<String, String> extraInfo);
}
