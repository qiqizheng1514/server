package org.example.server.common;

/**
 * 统一响应结果封装类
 * @param <T> 数据类型
 */
public class Result<T> {
    private Integer code;       // 状态码
    private String message;     // 消息
    private T data;             // 数据

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功结果
     * @param data 数据
     * @return 成功的响应结果
     * @param <T> 数据类型
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    /**
     * 成功结果（无数据）
     * @return 成功的响应结果
     * @param <T> 数据类型
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    /**
     * 失败结果
     * @param code 错误码
     * @param message 错误消息
     * @return 失败的响应结果
     * @param <T> 数据类型
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 失败结果（使用默认错误码500）
     * @param message 错误消息
     * @return 失败的响应结果
     * @param <T> 数据类型
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    /**
     * 设置数据并返回当前对象（用于链式调用）
     * @param data 数据
     * @return 当前对象
     */
    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }
} 