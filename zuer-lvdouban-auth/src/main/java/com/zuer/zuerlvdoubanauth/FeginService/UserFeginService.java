package com.zuer.zuerlvdoubanauth.FeginService;

import com.zuer.zuerlvdoubancommon.entity.User;
import com.zuer.zuerlvdoubancommon.entity.UserInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "zuer-lvdouban-service")
public interface UserFeginService {
    @RequestMapping(value = "/User/queryUserByQueryParam",method = RequestMethod.POST)
    List<User> queryUserByQueryParam( Map<String, Object> queryParam) ;

    @RequestMapping(value = "/User/queryUserInfoByUserName",method = RequestMethod.GET)
    UserInfo queryUserInfoByUserName(@RequestParam("username") String username) ;


    @RequestMapping(value = "/User/insertUser",method = RequestMethod.POST)
    int insertUser(User user);
}
