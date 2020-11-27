package com.paf.exercise.exercise.dto;

import java.util.List;

public class BaseResponse<T> {
    private Boolean ok;
    private List<T> result;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
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
