package com.benit.team.base.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseError extends BaseResponse {
    private String message;
    private String error;
}
