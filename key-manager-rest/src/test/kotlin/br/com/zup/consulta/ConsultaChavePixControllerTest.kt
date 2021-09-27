package br.com.zup.consulta

import br.com.zup.*
import br.com.zup.cadastra.CadastraChavePixRequest
import br.com.zup.grpc.KeyManagerGrpcFactory
import br.com.zup.remove.RemoveChavePixControllerTest
import com.google.protobuf.Timestamp
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
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@MicronautTest
internal class ConsultaChavePixControllerTest {

    @field:Inject
    lateinit var grpcClient: KeymanagerConsultaGrpcServiceGrpc.KeymanagerConsultaGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/cliente")
    lateinit var client: HttpClient

    companion object {
        val UUID_RANDOM = UUID.randomUUID().toString()
    }

    @Test
    fun `deve consultar chave pix`() {

        // cenario
        BDDMockito.given(grpcClient.consulta(Mockito.any())).willReturn(consultaChavePixResponse())

        // acao
        val response = client
            .toBlocking()
            .exchange(
                HttpRequest.GET<Any>("/${UUID_RANDOM}/pix/${RemoveChavePixControllerTest.UUID_RANDOM}"),
                Any::class.java
            )

        // validacao
        with(response) {
            assertEquals(HttpStatus.OK, status)
            assertNotNull(body())
        }

    }

    fun consultaChavePixResponse() : ConsultaChavePixResponse {
        return ConsultaChavePixResponse
            .newBuilder()
            .setIdCliente(UUID_RANDOM)
            .setIdChave(UUID_RANDOM)
            .setChavePix(ConsultaChavePixResponse.ChavePix
                .newBuilder()
                .setTipoChave("EMAIL")
                .setChave("teste@usuario.com")
                .setContaBancaria(ConsultaChavePixResponse.ChavePix.BankAccount
                    .newBuilder()
                    .setNomeTitular("Teste")
                    .setCpfTitular("27757823021")
                    .setNomeInstituicao("Teste")
                    .setAgencia("3166")
                    .setConta("21724")
                    .setTipoConta("CONTA_CORRENTE")
                    .build())
                .setDataCriacao(LocalDateTime.now().let {
                    val dataCriacao = it.atZone(ZoneId.of("UTC")).toInstant()
                    Timestamp.newBuilder()
                        .setSeconds(dataCriacao.epochSecond)
                        .setNanos(dataCriacao.nano)
                        .build()
                })
                .build())
            .build()
    }

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class Clients {
        @Singleton
        fun blockingStub() = Mockito.mock(KeymanagerConsultaGrpcServiceGrpc.KeymanagerConsultaGrpcServiceBlockingStub::class.java)
    }

}