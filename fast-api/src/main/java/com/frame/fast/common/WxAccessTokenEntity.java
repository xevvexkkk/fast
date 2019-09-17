package com.frame.fast.common;

import lombok.Data;

@Data
public class WxAccessTokenEntity {

    private String session_key;

    private String openid;

    private String access_token;

    private String expires_in;

    private String errcode;

    private String errmsg;
}
