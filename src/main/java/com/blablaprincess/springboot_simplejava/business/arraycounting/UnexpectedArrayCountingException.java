package com.blablaprincess.springboot_simplejava.business.arraycounting;

import com.blablaprincess.springboot_simplejava.business.common.exceptions.BusinessException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnexpectedArrayCountingException extends BusinessException {

    public UnexpectedArrayCountingException(String message) {
        super(message);
    }

}
