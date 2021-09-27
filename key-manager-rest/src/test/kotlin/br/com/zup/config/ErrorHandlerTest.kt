package br.com.zup.config

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class ErrorHandlerTest {

    val request = HttpRequest.GET<Any>("/")

    @Test
    internal fun `deve retornar 404 quando StatusException NOT_FOUND`() {
        val mensagem = "Not Found"
        val exception = StatusRuntimeException(
            Status.NOT_FOUND
            .withDescription(mensagem))

        val response = ErrorHandler().handle(request, exception)

        assertEquals(HttpStatus.NOT_FOUND, response.status)
        assertNotNull(response.body())
        assertEquals(mensagem, (response.body() as JsonError).message)
    }

    @Test
    internal fun `deve retornar 400 quando StatusException INVALID_ARGUMENT`() {
        val mensagem = "INVALID_ARGUMENT"
        val exception = StatusRuntimeException(
            Status.INVALID_ARGUMENT
                .withDescription(mensagem))

        val response = ErrorHandler().handle(request, exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.status)
        assertNotNull(response.body())
        assertEquals(mensagem, (response.body() as JsonError).message)
    }

    @Test
    internal fun `deve retornar 422 quando StatusException ALREADY_EXISTS`() {
        val mensagem = "ALREADY_EXISTS"
        val exception = StatusRuntimeException(
            Status.ALREADY_EXISTS
                .withDescription(mensagem))

        val response = ErrorHandler().handle(request, exception)

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.status)
        assertNotNull(response.body())
        assertEquals(mensagem, (response.body() as JsonError).message)
    }

    @Test
    internal fun `deve retornar 401 quando StatusException PERMISSION_DENIED`() {
        val mensagem = "PERMISSION_DENIED"
        val exception = StatusRuntimeException(
            Status.PERMISSION_DENIED
                .withDescription(mensagem))

        val response = ErrorHandler().handle(request, exception)

        assertEquals(HttpStatus.UNAUTHORIZED, response.status)
        assertNotNull(response.body())
        assertEquals(mensagem, (response.body() as JsonError).message)
    }

    @Test
    internal fun `deve retornar 500 quando StatusException PERMISSION_DENIED`() {
        val exception = StatusRuntimeException(Status.INTERNAL)
        val response = ErrorHandler().handle(request, exception)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.status)
        assertNotNull(response.body())
    }

}