package com.ifast.maker.service.impl;

import com.ifast.maker.dao.MakerDao;
import com.ifast.maker.pojo.Maker;
import com.ifast.maker.service.MakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李官宇 on 2017/7/27.
 * It comes to a very simple choose,really,
 * get busy living,or get busy dying.
 */
@Service
public class MakerServiceImpl implements MakerService {
    @Autowired
    private MakerDao makerDao;

    public List<Maker> getMakers() {
        return makerDao.getMakers();
    }

    public void setKeyValue(String key, String value) {
        Jedis jedis = new Jedis("192.168.127.100");
        jedis.set(key, value);
    }
}
