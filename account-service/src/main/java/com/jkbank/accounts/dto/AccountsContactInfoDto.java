package com.jkbank.accounts.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

// record class is used to hold immutable data objects with less boilerplate code
@ConfigurationProperties(prefix = "accounts") // to bind properties as in application.yml
public record AccountsContactInfoDto(String message, Map<String, String> contactDetails,
                                     List<String> onCallSupport) {

}
