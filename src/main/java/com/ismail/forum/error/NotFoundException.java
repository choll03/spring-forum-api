package com.ismail.forum.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Data Not Found!")
public class NotFoundException extends RuntimeException{
}
