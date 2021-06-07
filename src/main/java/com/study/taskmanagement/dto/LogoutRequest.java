package com.study.taskmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Data
@NoArgsConstructor @AllArgsConstructor
public class LogoutRequest {

    private HttpServletRequest request;
    private HttpServletResponse response;

}
