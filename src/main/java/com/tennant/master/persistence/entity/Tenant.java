package com.tennant.master.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@Table("tenant")
public class Tenant {

    @Id
    private UUID id;
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