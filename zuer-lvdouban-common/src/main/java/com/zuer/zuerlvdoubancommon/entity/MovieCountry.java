package com.zuer.zuerlvdoubancommon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "MOVIE_COUNTRY")
public class MovieCountry {
    @Id
    private String id;
    @Column(name = "movie_id")
    private String movieId;
    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "crt_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crtTime;

    @Column(name = "crt_user")
    private String crtUser;

    @Column(name = "crt_name")
    private String crtName;

    @Column(name = "crt_host")
    private String crtHost;

}
