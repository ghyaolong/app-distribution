package com.cube.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ProvisionVo {

    private String id;
    /**
     *
     */
    private String uuid;
    /**
     *
     */
    private Date createDate;
    /**
     *
     */
    private Integer deviceCount;
    /**
     *
     */
    private String[] devices;
    /**
     *
     */
    private Date expirationDate;
    /**
     *
     */
    private Boolean isEnterprise;
    /**
     *
     */
    private String teamId;
    /**
     *
     */
    private String teamName;
    /**
     *
     */
    private String type;

}
