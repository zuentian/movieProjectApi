package com.zuer.movieprojectuser.feignConfig;

import com.zuer.movieprojectcommon.entity.UserRole;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("movie-project-common")
public interface UserRoleFeignClient {

    @RequestMapping(value = "/UserRole/insertUserRole",method = RequestMethod.POST)
    void insertUserRole(@RequestBody UserRole userRole);

    @RequestMapping(value = "/UserRole/deleteUserRoleByUserId",method = RequestMethod.GET)
    void deleteUserRoleByUserId(@RequestParam("userId") String userId) throws Exception;
}
