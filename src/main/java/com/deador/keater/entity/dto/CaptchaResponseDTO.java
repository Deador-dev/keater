package com.deador.keater.entity.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CaptchaResponseDTO {
    private Boolean success;
    @JsonAlias("error-codes")
    private Set<String> errorCodes;
}
