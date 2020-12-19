package com.benit.team.base.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class BaseResponse {
    protected Integer statusCode;
}
