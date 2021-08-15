package com.blablaprincess.springboot_simplejava.rest.exceptionshandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
    private String path;
    private String className;
    private String message;
    private String timestamp;
}
