package com.dailyCode.expenseTrackerApi.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorObjects {

    private Integer statusCode;
    private String message;
    private Date timestamp;
}
