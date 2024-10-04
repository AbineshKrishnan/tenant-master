package com.tennant.master.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class TemplateResponse {

    private UUID id;

    private String name;

    private String designation;

    private Integer availableDays;

    private List<String> availableLocations;

    private Instant updatedAt;

}
