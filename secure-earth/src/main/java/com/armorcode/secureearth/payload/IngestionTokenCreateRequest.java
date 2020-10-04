package com.armorcode.secureearth.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class IngestionTokenCreateRequest {
    @NotBlank
    private String description;
}
