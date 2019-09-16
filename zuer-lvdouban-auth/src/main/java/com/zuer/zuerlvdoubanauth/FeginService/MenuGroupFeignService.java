package com.zuer.zuerlvdoubanauth.FeginService;

import com.zuer.zuerlvdoubancommon.entity.MenuGroup;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "zuer-lvdouban-service")
public interface MenuGroupFeignService {


    @RequestMapping(value = "/MenuGroup/deleteMenuGroupByGroupIdAndMenuType",method = RequestMethod.GET)
    void deleteMenuGroupByGroupIdAndMenuType(@RequestParam(value="groupId", required = false) String groupId,
                                             @RequestParam(value="menuType", required = false) String menuType);

    @RequestMapping(value = "/MenuGroup/insertMenuGroup",method = RequestMethod.POST)
    void insertMenuGroup(MenuGroup menuGroup);


}
