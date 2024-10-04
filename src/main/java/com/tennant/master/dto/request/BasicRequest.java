package com.tennant.master.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicRequest {

    private String searchText;

    private int pageNo;

    private int pageSize;

}
