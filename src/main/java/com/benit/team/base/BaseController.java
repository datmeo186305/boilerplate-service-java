package com.benit.team.base;

import com.benit.team.base.response.ResponseData;
import com.benit.team.base.response.ResponseError;
import com.benit.team.common.Constant;
import com.benit.team.entities.mongo.user.UserAccount;
import com.benit.team.exception.InValidObjectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@Component
public class BaseController {

    protected static ResponseData.Meta buildMetaResponse(String message, Integer page, Integer pageSize) {
        ResponseData.Meta meta = new ResponseData.Meta();
        meta.setPageSize(Optional.ofNullable(pageSize).orElse(Constant.DEFAULT_PAGE_SIZE));
        meta.setPage(Optional.ofNullable(page).orElse(1));
        meta.setMessage(message);
        return meta;
    }

    protected UserAccount getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserAccount) {
            return (UserAccount) authentication.getPrincipal();
        }
        return null;
    }

    @ExceptionHandler(InValidObjectException.class)
    public ResponseEntity<ResponseError> handleInValidateObjectException(InValidObjectException e) {
        ResponseError data = new ResponseError();
        data.setError(e.getMessage());
        data.setMessage(e.getMessage());
        return new ResponseEntity<>(data, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleException(Exception e) {
        ResponseError data = new ResponseError();
        data.setError(e.getMessage());
        data.setMessage(e.getMessage());
        return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
