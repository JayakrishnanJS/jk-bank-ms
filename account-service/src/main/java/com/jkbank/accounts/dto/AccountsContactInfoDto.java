package com.jkbank.accounts.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

// record class is used to hold immutable data objects with less boilerplate code
@ConfigurationProperties(prefix = "accounts") // to bind properties as in application.yml
@Getter
@Setter
public class AccountsContactInfoDto {
 private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;
}
