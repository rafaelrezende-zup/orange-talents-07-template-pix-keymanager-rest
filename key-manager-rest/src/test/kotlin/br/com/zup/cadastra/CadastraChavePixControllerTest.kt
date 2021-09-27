package br.com.zup.cadastra

import br.com.zup.KeymanagerCadastraGrpcServiceGrpc
import br.com.zup.NovaChavePixResponse
import br.com.zup.grpc.KeyManagerGrpcFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*

@MicronautTest
internal class CadastraChavePixControllerTest {

    @field:Inject
    lateinit var grpcClient: KeymanagerCadastraGrpcServiceGrpc.KeymanagerCadastraGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/cliente")
    lateinit var client: HttpClient

    companion object {
        val UUID_RANDOM = UUID.randomUUID().toString()
    }

    @Test
    fun `deve cadastrar uma nova chave pix`() {

        // cenario
        val request = chavePixRequest()
        val responseGrpc = chavePixResponse()

        given(grpcClient.cadastra(Mockito.any())).willReturn(responseGrpc)

        // acao
        val response = client
            .toBlocking()
            .exchange(
                HttpRequest.POST("/$UUID_RANDOM/pix", request),
                CadastraChavePixRequest::class.java
            )

        // validacao
        with(response) {
            assertEquals(HttpStatus.CREATED, status)
        }

    }

    private fun chavePixRequest(): CadastraChavePixRequest {
        return CadastraChavePixRequest(
            TipoChaveRequest.EMAIL,
            "usuario@teste.com",
            TipoContaRequest.CONTA_CORRENTE
        )
    }

    private fun chavePixResponse(): NovaChavePixResponse {
        return NovaChavePixResponse
            .newBuilder()
            .setIdPix(UUID_RANDOM)
            .build()
    }

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class Clients {
        @Singleton
        fun blockingStub() = Mockito.mock(KeymanagerCadastraGrpcServiceGrpc.KeymanagerCadastraGrpcServiceBlockingStub::class.java)
    }

}