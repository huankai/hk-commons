package com.hk.commons;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hk.commons.annotations.EnumDisplay;
import com.hk.commons.util.EnumDisplayUtils;
import com.hk.commons.util.StringUtils;

/**
 * Json返回结果
 *
 * @author kevin
 * @date 2017年9月27日上午11:09:08
 */
public final class JsonResult<T> {

    public enum Status {

        /**
         * 成功
         */
        @EnumDisplay(value = "operation.success", order = 10200)
        SUCCESS,

        /**
         * 失败
         */
        @EnumDisplay(value = "operation.failure", order = -1)
        FAILURE,

        /**
         * 重定向
         */
        @EnumDisplay(value = "operation.redirect", order = 10302)
        REDIRECT,

        /**
         * 坏的请求
         */
        @EnumDisplay(value = "operation.bad_request", order = 10400)
        BAD_REQUEST,

        /**
         * 未认证
         */
        @EnumDisplay(value = "operation.unauthorized", order = 10401)
        UNAUTHORIZED,

        /**
         * 无权限
         */
        @EnumDisplay(value = "operation.forbidden", order = 10403)
        FORBIDDEN,

        /**
         * 资源不存在
         */
        @EnumDisplay(value = "operation.not_found", order = 10404)
        NOT_FOUND,

        /**
         * 请求方法不支持
         */
        @EnumDisplay(value = "operation.method_not_allowed", order = 10405)
        METHOD_NOT_ALLOWED,

        /**
         * 服务器错误
         */
        @EnumDisplay(value = "operation.server_error", order = 10500)
        SERVER_ERROR
    }

    /**
     * 返回数据
     */
    private T data;

    /**
     * 返回状态
     */
    @JsonIgnore
    private Status status;

    /**
     * 返回消息信息
     */
    private String message;

    /**
     * 请求成功
     *
     * @return JsonResult
     */
    public static JsonResult<Void> success() {
        return success(null);
    }

    public static JsonResult<Void> success(String message) {
        return new JsonResult<>(true, message);
    }

    /**
     * 请求成功
     *
     * @return JsonResult
     */
    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<>(data);
    }

    /**
     * 请求失败
     *
     * @return JsonResult
     */
    public static JsonResult<Void> failure() {
        return new JsonResult<>(Status.FAILURE);
    }

    /**
     * 请求失败
     *
     * @param message 失败信息
     * @return JsonResult
     */
    public static JsonResult<Void> failure(String message) {
        return new JsonResult<>(false, message);
    }

    /**
     * 请求姿势不正确
     *
     * @param message message
     * @return JsonResult
     */
    public static JsonResult<Void> badRequest(String message) {
        return new JsonResult<>(Status.BAD_REQUEST, message);
    }

    /**
     * 用户未认证
     *
     * @param message message
     * @return JsonResult
     */
    public static JsonResult<Void> unauthorized(String message) {
        return new JsonResult<>(Status.UNAUTHORIZED, message);
    }

    /**
     * 访问拒绝
     *
     * @param message message
     * @return JsonResult
     */
    public static JsonResult<Void> forbidden(String message) {
        return new JsonResult<>(Status.FORBIDDEN, message);
    }

    /**
     * 请求失败
     *
     * @param data 失败数据
     * @return JsonResult
     */
    public static <T> JsonResult<T> failure(T data) {
        return new JsonResult<>(Status.FAILURE, data);
    }

    /**
     * 请求错误
     *
     * @return JsonResult
     */
    public static JsonResult<Void> error() {
        return new JsonResult<>(Status.SERVER_ERROR);
    }

    /**
     * 请求错误
     *
     * @param message 错误信息
     * @return JsonResult
     */
    public static JsonResult<Void> error(String message) {
        return new JsonResult<>(Status.SERVER_ERROR, message);
    }

    /**
     * 请求重定向
     *
     * @param redirectUrl 重定向地址
     * @return JsonResult
     */
    public static JsonResult<String> redirect(String redirectUrl) {
        return new JsonResult<>(Status.REDIRECT, null, redirectUrl);
    }

    public JsonResult() {
        this(Status.SUCCESS);
    }

    public JsonResult(boolean success) {
        this(success ? Status.SUCCESS : Status.FAILURE);
    }

    public JsonResult(boolean success, String message) {
        this(success ? Status.SUCCESS : Status.FAILURE, message);
    }

    public JsonResult(T data) {
        this(Status.SUCCESS, data);
    }

    public JsonResult(boolean success, T data) {
        this(success ? Status.SUCCESS : Status.FAILURE, null, data);
    }

    public JsonResult(Status status) {
        this(status, null, null);
    }

    public JsonResult(Status status, String message) {
        this(status, message, null);
    }

    public JsonResult(Status status, T data) {
        this(status, null, data);
    }

    public JsonResult(Status status, String message, T data) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return StringUtils.isEmpty(message) ? EnumDisplayUtils.getDisplayText(status.name(), Status.class) : message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return EnumDisplayUtils.getDisplayOrder(status);
    }

}
