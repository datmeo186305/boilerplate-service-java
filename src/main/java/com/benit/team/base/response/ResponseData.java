package com.benit.team.base.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseData extends BaseResponse{

    private Meta meta;
    private Object data;

    @Data
    public static class Meta {
        private String message;
        private Integer page;
        private Integer pageSize;
    }

}
