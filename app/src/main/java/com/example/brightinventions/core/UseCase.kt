package com.example.brightinventions.core

abstract class UseCase<in T, out R> {

    abstract fun execute(params: T): R

    operator fun invoke(params: T): Result<R> {
        return try {
            val type = execute(params)
            Result.Success(type)
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

}


class None

