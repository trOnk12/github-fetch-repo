package com.example.brightinventions.core.functional

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Type

    suspend operator fun invoke(params: Params): Result<Type> {
        return try {
            //returns the value suspends the block
            withContext(Dispatchers.Default) {
                run(params).let {
                    Result.Success(
                        it
                    )
                }
            }
        } catch (exception: Exception) {
            return Result.Error(
                exception
            )
        }
    }
}

class None

