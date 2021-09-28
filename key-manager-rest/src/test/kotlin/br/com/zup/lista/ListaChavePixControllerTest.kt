package br.com.zup.lista

import br.com.zup.KeymanagerConsultaGrpcServiceGrpc
import br.com.zup.KeymanagerListaGrpcServiceGrpc
import br.com.zup.ListaChavePixResponse
import br.com.zup.cadastra.CadastraChavePixControllerTest
import br.com.zup.cadastra.CadastraChavePixRequest
import br.com.zup.config.grpc.KeyManagerGrpcFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.util.*

@MicronautTest
internal class ListaChavePixControllerTest {

    @field:Inject
    lateinit var grpcClient: KeymanagerListaGrpcServiceGrpc.KeymanagerListaGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/cliente")
    lateinit var client: HttpClient

    companion object {
        val UUID_RANDOM = UUID.randomUUID().toString()
    }

    @Test
    fun `deve listar chaves pix`() {

        // cenario

        BDDMockito.given(grpcClient.lista(Mockito.any())).willReturn(ListaChavePixResponse
            .newBuilder()
            .build())

        // acao
        val response = client
            .toBlocking()
            .exchange(
                HttpRequest.GET<Any>("/$UUID_RANDOM/pix"),
                Any::class.java
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
        fun blockingStub() = Mockito.mock(KeymanagerListaGrpcServiceGrpc.KeymanagerListaGrpcServiceBlockingStub::class.java)
    }
}