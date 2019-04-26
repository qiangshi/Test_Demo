package com.supermap.demo.test.net;

import com.supermap.demo.test.net.exception.ApiException;

import io.reactivex.functions.Function;

public class ResponseConvert<E> implements Function<BaseApiResponse<E>, BaseApiResponse<E>> {
    @Override
    public BaseApiResponse<E> apply(BaseApiResponse<E> response) {
        if (!response.getCode().equals("success")) {
            throw new ApiException(response.getCode(), response.getMsg());
        }
        return response;
    }
}
