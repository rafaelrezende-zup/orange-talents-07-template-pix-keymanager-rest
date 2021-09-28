package br.com.zup.lista

import br.com.zup.KeymanagerListaGrpcServiceGrpc
import br.com.zup.ListaChavePixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory

@Validated
@Controller("/cliente/{idCliente}/pix")
class ListaChavePixController(private val cadastraChavePixClient: KeymanagerListaGrpcServiceGrpc.KeymanagerListaGrpcServiceBlockingStub) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Get
    fun cadastra(idCliente: String): HttpResponse<Any> {
        logger.info("Iniciando fluxo de listagem de chaves pix do cliente: $idCliente")
        val response = cadastraChavePixClient.lista(ListaChavePixRequest
            .newBuilder()
            .setIdCliente(idCliente)
            .build())
        val chaves = response.chavesList.map { ListaChaveResponse(it) }
        return HttpResponse.ok(chaves)
    }

}