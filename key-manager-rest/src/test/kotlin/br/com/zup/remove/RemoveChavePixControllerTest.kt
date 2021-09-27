package br.com.zup.remove

import br.com.zup.KeymanagerRemoveGrpcServiceGrpc
import br.com.zup.RemoveChavePixResponse
import br.com.zup.cadastra.CadastraChavePixRequest
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
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.util.*

@MicronautTest
internal class RemoveChavePixControllerTest {

    @field:Inject
    lateinit var grpcClient: KeymanagerRemoveGrpcServiceGrpc.KeymanagerRemoveGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/cliente")
    lateinit var client: HttpClient

    companion object {
        val UUID_RANDOM = UUID.randomUUID().toString()
    }

    @Test
    fun `deve remover chave pix`() {

        // cenario
        BDDMockito.given(grpcClient.remove(Mockito.any())).willReturn(RemoveChavePixResponse.getDefaultInstance())

        // acao
        val response = client
            .toBlocking()
            .exchange(
                HttpRequest.DELETE<Any>("/${UUID_RANDOM}/pix/${UUID_RANDOM}"),
                CadastraChavePixRequest::class.java
            )

        // validacao
        with(response) {
            assertEquals(HttpStatus.OK, status)
        }

    }

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class Clients {
        @Singleton
        fun blockingStub() = Mockito.mock(KeymanagerRemoveGrpcServiceGrpc.KeymanagerRemoveGrpcServiceBlockingStub::class.java)
    }
}