package com.zuer.movieprojectcommon.service;


import com.zuer.movieprojectcommon.entity.Dict;
import com.zuer.movieprojectcommon.entity.DictValue;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Mapper
@EnableFeignClients
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/Dict")
public interface DictService {

    @RequestMapping(value = "/queryDictByDictType",method = RequestMethod.GET)
    List<DictValue> queryDictByDictType(@RequestParam("dictType") String dictType);

    @RequestMapping(value = "queryDict",method = RequestMethod.POST)
    List<Dict> queryDict(@RequestBody Map<String,Object> map);

    @RequestMapping(value = "/queryDictTypeNameByDictType",method = RequestMethod.GET)
    List<Dict> queryDictTypeNameByDictType(@RequestParam("dictType") String dictType);
}
