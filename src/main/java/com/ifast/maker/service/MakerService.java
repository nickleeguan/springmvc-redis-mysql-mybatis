package com.ifast.maker.service;

import com.ifast.maker.pojo.Maker;

import java.util.List;

/**
 * Created by 李官宇 on 2017/7/27.
 * It comes to a very simple choose,really,
 * get busy living,or get busy dying.
 */
public interface MakerService {
    public List<Maker> getMakers();

    void setKeyValue(String key, String value);
}
