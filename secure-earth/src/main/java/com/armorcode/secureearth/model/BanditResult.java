package com.armorcode.secureearth.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BanditResult {
    private String code;
    private String filename;
    private String issue_confidence;
    private String issue_severity;
    private String issue_text;
    private Long line_number;
    private String more_info;
    private String test_id;
    private String test_name;
}
