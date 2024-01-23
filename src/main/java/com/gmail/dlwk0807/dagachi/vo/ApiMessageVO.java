package com.gmail.dlwk0807.dagachi.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiMessageVO<T> implements Serializable {
    private String respCode;
    private String respMsg;
    private T respBody;
}
