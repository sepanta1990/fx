package com.paf.exercise.exercise.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private Boolean ok;
    private T result;

    public BaseResponse() {
    }

    public BaseResponse(Boolean ok, T result) {
        this.ok = ok;
        this.result = result;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "ok=" + ok +
                ", result=" + result +
                '}';
    }
}
