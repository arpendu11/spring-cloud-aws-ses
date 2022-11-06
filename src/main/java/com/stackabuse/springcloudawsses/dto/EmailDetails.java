package com.stackabuse.springcloudawsses.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {

    private String fromEmail;
    private String toEmail;
    private String subject;
    private String body;
}
