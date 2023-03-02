package com.deador.keater.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MultipartToStringConverter implements Converter<MultipartFile, String> {
    @Override
    public String convert(MultipartFile file) {
        return file.getOriginalFilename();
    }
}
