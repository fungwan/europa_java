package com.erathink.europa.user.rest;

import com.alibaba.fastjson.JSONObject;
import com.erathink.europa.commons.ErrorEnum;
import com.erathink.europa.commons.Response;
import com.erathink.europa.user.entity.UserEntity;
import com.erathink.europa.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;

/**
 * Created by fengyun on 2017/12/17.
 */

@RestController
@CrossOrigin
@RequestMapping(value = "/europa")
public class UserRest {

    @Autowired
    UserService userService;

    @Autowired
    private ResourceLoader resourceLoader;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRest.class);

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Response getDetail(@PathVariable String id) {

        //test
        String path = this.getClass().getResource("/").getPath() + "zs.p12";
        LOGGER.info(path);

        UserEntity entity = userService.getById(id);
        if(entity == null) return Response.wrap(ErrorEnum.COMM_BIZ_NOT_EXISTS, "id");
        return Response.wrap(path);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Response createDetail(@RequestBody JSONObject body) {//用实体接收更为合适，语义理解通畅

        LOGGER.info("新的用户信息为：{}",body);
        return Response.wrap(1);
    }
}
