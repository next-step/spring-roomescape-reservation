package com.nextstep.web.theme.exception;

import nextsetp.common.BusinessException;

public class ThemeNotFoundException extends BusinessException {
    private static final String MESSAGE = "존재하지 않는 테마 입니다.";

    public ThemeNotFoundException() {
        super(MESSAGE);
    }
}
