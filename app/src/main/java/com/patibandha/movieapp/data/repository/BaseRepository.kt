package com.patibandha.movieapp.data.repository

import com.patibandha.movieapp.data.Either
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

abstract class BaseRepository {

    suspend fun <R: Any> either(requestFunc: suspend () -> R): Either<MyAppException, R> {
        return try {
            Either.Right(requestFunc.invoke())
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Left(parseError(e))
        }
    }

    fun <R> eitherLocal(response: R): Either<MyAppException, R> {
        return try {
            Either.Right(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Left(MyAppException.DataBaseInsertionError(e))
        }
    }

    /*suspend fun <R> either(response: Deferred<R>): Either<MyAppException, R> {
        return try {
            Either.Right(response.await())
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Left(parseError(e))
        }
    }*/

    private fun parseError(e: Exception): MyAppException {
        return if (e is HttpException) {
            return when (e.code()) {
                404 -> MyAppException.ServerException(e)
                422 -> MyAppException.UnparcebleEntityException(e)
                else -> {
                    MyAppException.UnknownException(e)
                }
            }
        } else if (e is SocketTimeoutException || e is TimeoutException) {
            MyAppException.InternetException(e)
        } else {
            MyAppException.UnknownException(e)
        }
    }

    sealed class MyAppException(throwable: Throwable, key: String? = "", msg: String? = "") :
        Exception() {
        class ServerException(e: Exception) : MyAppException(e)
        class UnknownException(e: Exception) : MyAppException(e)
        class UnparcebleEntityException(e: Exception) : MyAppException(e)
        class DataBaseInsertionError(e: Exception) : MyAppException(e)
        class InternetException(e: Exception) : MyAppException(e)
    }
}