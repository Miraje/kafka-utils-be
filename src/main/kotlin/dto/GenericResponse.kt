package pt.miraje.dto

data class GenericResponse<T>(
    val data: T,
    val errors: MutableList<GenericError>? = null
)

data class GenericError(
    val httpCode: Int?,
    val code: String,
    val message: String,
)

fun <T> T.toSuccessResponse(): GenericResponse<T> = GenericResponse<T>(data = this)

fun <T> T.toErrorResponse(): GenericResponse<T> = GenericResponse<T>(data = this, errors = mutableListOf())

fun <T> GenericResponse<T>.addError(
    httpCode: Int?,
    code: String,
    message: String
): GenericResponse<T> {
    this.errors?.add(GenericError(httpCode = httpCode, code = code, message = message))
    return this
}