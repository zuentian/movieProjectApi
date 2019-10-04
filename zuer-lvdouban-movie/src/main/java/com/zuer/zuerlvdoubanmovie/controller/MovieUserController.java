package com.zuer.zuerlvdoubanmovie.controller;

import com.zuer.zuerlvdoubancommon.entity.MovieInfo;
import com.zuer.zuerlvdoubancommon.entity.MovieUser;
import com.zuer.zuerlvdoubancommon.utils.EntityUtils;
import com.zuer.zuerlvdoubanmovie.feginservice.MovieInfoFeignService;
import com.zuer.zuerlvdoubanmovie.feginservice.MovieUserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@EnableAutoConfiguration
@RequestMapping(value = "/MovieUserController")
@RestController
@Transactional(rollbackFor = {Exception.class})
public class MovieUserController {

    @Autowired
    private MovieUserFeignService movieUserFeignService;
    @Autowired
    private MovieInfoFeignService movieInfoFeignService;

    @Autowired
    private MovieCalculateScoreController movieCalculateScoreController;

    @RequestMapping(value = "/insertMovieUser",method = RequestMethod.POST)
    public void insertMovieUser(@RequestParam Map<String,Object> param) throws Exception {

        MovieUser movieUser=EntityUtils.mapToEntity(param,MovieUser.class);
        movieUser.setId(UUID.randomUUID().toString());
        EntityUtils.setCreatAndUpdatInfo(movieUser);
        if("1".equals(movieUser.getState())){
            movieUser.setWatchBeforeTime(new Date());
        }
        if("2".equals(movieUser.getState())){
            movieUser.setWatchAfterTime(new Date());
        }
        int i=movieUserFeignService.insertMovieUser(movieUser);
        if(i>0){//更新电影信息里的想看人数和看过人数
            if("1".equals(movieUser.getState())){
                movieInfoFeignService.addWatchBeforeNumber(movieUser.getMovieId());
            }else if("2".equals(movieUser.getState())){
                movieInfoFeignService.addWatchAfterNumber(movieUser.getMovieId());
                movieCalculateScoreController.calculateScore(movieUser.getMovieId());
            }
        }

    }


    @RequestMapping(value = "/queryMovieUserByMovieIdAndUserId",method = RequestMethod.POST)
    public MovieUser queryMovieUserByMovieIdAndUserId(@RequestParam Map<String,Object> param) throws Exception {
        String movieId = param.get("movieId") == null ? null : (String) param.get("movieId");
        String userId = param.get("userId") == null ? null : (String) param.get("userId");
        List<MovieUser> movieUserList = movieUserFeignService.queryMovieUserByMovieIdAndUserId(movieId, userId);
        if (movieUserList != null && movieUserList.size() > 0) {
            return movieUserList.get(0);

        }
        return new MovieUser();
    }
}
