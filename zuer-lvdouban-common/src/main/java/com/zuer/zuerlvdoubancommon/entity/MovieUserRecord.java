package com.zuer.zuerlvdoubancommon.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author Zuer
 * @version 1.0
 * @date 2021/8/18 11:03
 * 用户和电影记录表
 */
@Data
@Table(name="MOVIE_USER_RECORD")
public class MovieUserRecord {

    @Id
    @Column
    private String movieId;

    @Id
    @Column
    private String userId;

    /**
     * 1-想看 2-看过
     */
    @Column
    private String state;


    /**
     * 评分
     */
    @Column
    private Double score;
    /**
     * 短评
     */
    @Column
    private String shortCommand;

    @Column
    private Date crtTime;


    /**
     * 临时增加
     */
    @Transient
    private String movieType;
}
