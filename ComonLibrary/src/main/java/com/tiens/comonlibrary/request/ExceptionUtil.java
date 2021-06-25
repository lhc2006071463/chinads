package com.tiens.comonlibrary.request;

import androidx.lifecycle.MutableLiveData;

public class ExceptionUtil {
    private MutableLiveData<ApiException> exceptionLiveData = new MutableLiveData<>();
    private ExceptionUtil() {

    }

    public static ExceptionUtil getInstance() {
        return ExceptionHolder.util;
    }

    private static class ExceptionHolder {
        private static final ExceptionUtil util = new ExceptionUtil();
    }

    public void setExceptionLiveData(MutableLiveData<ApiException> exceptionLiveData) {
        this.exceptionLiveData = exceptionLiveData;
    }

    public MutableLiveData<ApiException> getExceptionLiveData() {
        return exceptionLiveData;
    }
}
