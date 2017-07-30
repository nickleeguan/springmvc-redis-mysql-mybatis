package com.ifast.maker.controller;

import com.ifast.maker.pojo.Maker;
import com.ifast.maker.service.MakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 李官宇 on 2017/7/27.
 * It comes to a very simple choose,really,
 * get busy living,or get busy dying.
 */
@RestController
public class MakerController {

    @Autowired
    private MakerService makerService;

    @RequestMapping("/makers")
    @ResponseBody
    public List<Maker> getMakers(){
        return makerService.getMakers();
    }

    /**
     * 检测redis连接
     * @param key
     * @param value
     */
    @RequestMapping("/set/{key}/{value}")
    public void setKeyValue(@PathVariable("key") String key, @PathVariable("value") String value){
        makerService.setKeyValue(key, value);
    }
}
