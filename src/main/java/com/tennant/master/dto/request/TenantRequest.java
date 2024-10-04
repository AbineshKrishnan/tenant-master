package com.tennant.master.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TenantRequest {

    private String name;
    private String mobileNumber;
    private String emailId;
    private String driver;
    private Integer port;
    private String dbUrl;
    private String dbName;
    private String dbUsername;
    private String dbPassword;

}
