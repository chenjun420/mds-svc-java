package com.pts.mds.common;

import lombok.Data;

/**
 *  统一的JSON格式数据响应类
 */
@Data
public final class ApiResponse<T> {

    /**
     * 操作结果，true 执行成功；false 执行失败
     */
    private boolean success;

    /**
     * 返回码，0表示 正常返回值；负数表示错误码，正数表示警告码
     */
    private Integer code;

    /**
     * 返回信息，用户错误或告警描述
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 默认构造函数
     */
    public ApiResponse() {
    }

    /**
     * 构造函数
     * @param success
     * @param code
     * @param msg
     * @param data
     */
    public ApiResponse(boolean success, Integer code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
