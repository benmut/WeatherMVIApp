package com.mutondo.weathermviapp.data

data class Resource<out T>(
    val status: ResourceStatus,
    val data: T?,
    val errorType: ResourceErrorType) {

    fun isSuccessful(): Boolean {
        return status == ResourceStatus.SUCCESS
    }

    fun hasData(): Boolean {
        return data != null
    }

    fun hasError(): Boolean {
        return status == ResourceStatus.ERROR
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(ResourceStatus.SUCCESS, data, ResourceErrorType.NONE)
        }

        fun <T> error(errorType: ResourceErrorType, data: T?): Resource<T> {
            return Resource(ResourceStatus.ERROR, data, errorType)
        }
    }
}

enum class ResourceStatus {
    SUCCESS,
    ERROR
}