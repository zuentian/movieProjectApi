package com.zuer.zuerlvdoubanauth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuer.zuerlvdoubanauth.FeginService.DictFeignService;
import com.zuer.zuerlvdoubanauth.FeginService.GroupFeignService;
import com.zuer.zuerlvdoubanauth.FeginService.UserFeginService;
import com.zuer.zuerlvdoubanauth.jwt.JWTUtil;
import com.zuer.zuerlvdoubancommon.entity.Group;
import com.zuer.zuerlvdoubancommon.entity.Menu;
import com.zuer.zuerlvdoubancommon.entity.UserInfo;
import com.zuer.zuerlvdoubancommon.utils.ClientUtil;
import com.zuer.zuerlvdoubancommon.utils.ReflectionUtils;
import com.zuer.zuerlvdoubancommon.utils.TreeUtil;
import com.zuer.zuerlvdoubancommon.vo.DictValue;
import com.zuer.zuerlvdoubancommon.vo.GroupTree;
import com.zuer.zuerlvdoubancommon.vo.MenuTree;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

@EnableAutoConfiguration
@RequestMapping(value = "/GroupController")
@RestController
public class GroupController {

    @Autowired
    private UserFeginService userFeginService;
    @Autowired
    private GroupFeignService groupFeignService;
    @Autowired
    private DictFeignService dictFeignService;

    @RequestMapping(value = "/queryTree", method = RequestMethod.POST)
    @ResponseBody
    public List<GroupTree> queryTree(String groupTypeId) {
        if(StringUtils.isBlank(groupTypeId)) {
            return new ArrayList<GroupTree>();
        }
        List<Group> groupList=groupFeignService.queryGroupByGroupTypeId(groupTypeId);
        String root="";
        List<DictValue> dictValueList=dictFeignService.queryDictByDictType("GROUPROOT");
        if(dictValueList!=null&&dictValueList.size()>0){
            root=dictValueList.get(0).getLabel();
        }
        return createrGroupTree(groupList, root);
    }
    @RequestMapping(value = "/insertGroup",method = RequestMethod.POST)
    public int insertGroup(@RequestParam Map<String, Object> param) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Group group = mapper.readValue((String) param.get("group"), Group.class);

        String uuid= UUID.randomUUID().toString();
        group.setId(uuid);
        group=setGroupTypeCrt(group);
        group=setGroupTypeUpd(group);
        return groupFeignService.insertGroup(group);
    }

    private Group setGroupTypeCrt(Group group){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String hostIp = ClientUtil.getClientIp(request);
        String token=request.getHeader("Authorization");//获取token值，取得登陆的账号
        UserInfo userInfo=userFeginService.queryUserInfoByUserName(JWTUtil.getUsername(token));
        // 默认属性
        String[] fields = {"crtHost","crtTime","crtUser","crtName"};
        Field field = ReflectionUtils.getAccessibleField(group, "crtTime");
        // 默认值
        Object [] value = null;
        if(field!=null&&field.getType().equals(Date.class)){
            value = new Object []{hostIp,new Date(),userInfo.getUsername(),userInfo.getName()};
        }
        // 填充默认属性值
        setDefaultValues(group, fields, value);
        return group;
    }


    private Group setGroupTypeUpd(Group group){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String hostIp = ClientUtil.getClientIp(request);
        String token=request.getHeader("Authorization");//获取token值，取得登陆的账号
        UserInfo userInfo=userFeginService.queryUserInfoByUserName(JWTUtil.getUsername(token));
        // 默认属性
        String[] fields = {"updHost","updTime","updUser","updName"};
        Field field = ReflectionUtils.getAccessibleField(group, "updTime");
        // 默认值
        Object [] value = null;
        if(field!=null&&field.getType().equals(Date.class)){
            value = new Object []{hostIp,new Date(),userInfo.getUsername(),userInfo.getName()};
        }
        // 填充默认属性值
        setDefaultValues(group, fields, value);
        return group;
    }
    /**
     * 依据对象的属性数组和值数组对对象的属性进行赋值
     *
     * @param entity 对象
     * @param fields 属性数组
     * @param value 值数组
     * @author 王浩彬
     */
    private static <T> void setDefaultValues(T entity, String[] fields, Object[] value) {
        for(int i=0;i<fields.length;i++){
            String field = fields[i];
            if(ReflectionUtils.hasField(entity, field)){
                ReflectionUtils.invokeSetter(entity, field, value[i]);
            }
        }
    }

    private List<GroupTree> createrGroupTree(List<Group> groups, String root) {
        List<GroupTree> trees = new ArrayList<GroupTree>();
        GroupTree node = null;
        for (Group group : groups) {
            node = new GroupTree();
            BeanUtils.copyProperties(group, node);
            node.setLabel(group.getName());
            trees.add(node);
        }
        return TreeUtil.bulid(trees, root);
    }

}
