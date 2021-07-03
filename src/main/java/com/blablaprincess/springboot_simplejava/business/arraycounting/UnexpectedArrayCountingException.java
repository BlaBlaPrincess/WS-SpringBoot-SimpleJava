package com.blablaprincess.springboot_simplejava.business.arraycounting;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnexpectedArrayCountingException extends RuntimeException {

    public UnexpectedArrayCountingException(String message) {
        super(message);
    }

}
