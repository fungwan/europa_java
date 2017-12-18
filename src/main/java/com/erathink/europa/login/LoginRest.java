package com.erathink.europa.login;

import com.alibaba.fastjson.JSONObject;
import com.erathink.europa.commons.CoreConstants;
import com.erathink.europa.commons.ErrorEnum;
import com.erathink.europa.commons.Response;
import com.erathink.europa.user.entity.UserEntity;
import com.erathink.europa.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by fengyun on 2017/12/17.
 */

@RestController
@CrossOrigin
@RequestMapping(value = "/europa")
public class LoginRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRest.class);

    @Autowired
    UserService userService;

    /**
     * 用户名密码登陆
     *
     * @param session
     * @param body
     * @return
     */
    @RequestMapping(value = "/user/session", method = RequestMethod.POST)
    public Response loginByAcct(HttpSession session, @RequestBody JSONObject body) {

        String pw = body.getString("password");
        String userName = body.getString("userName");

        UserEntity entity = userService.getByUserNameAndPW(userName,pw);
        if(entity == null){
            return Response.wrap(ErrorEnum.COMM_BIZ_ERROR,"登录密码错误");
        }else{
            session.setAttribute(CoreConstants.SESSION_KEY_USER,
                    entity);
            return Response.wrap(entity);
        }

    }
}
