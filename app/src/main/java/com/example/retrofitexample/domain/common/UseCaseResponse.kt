package com.example.retrofitexample.domain.common

import com.example.retrofitexample.domain.model.ApiError

interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(apiError: ApiError?)
}
