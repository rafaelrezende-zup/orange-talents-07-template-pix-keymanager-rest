package br.com.zup.consulta

import br.com.zup.*
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.validation.Valid

@Validated
@Controller("/cliente/{idCliente}/pix/{pixId}")
class ConsultaChavePixController(private val consultaChavePixClient: KeymanagerConsultaGrpcServiceGrpc.KeymanagerConsultaGrpcServiceBlockingStub) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Get
    fun consulta(idCliente: String, pixId: String): HttpResponse<Any> {
        logger.info("Iniciando fluxo de consulta de chave pix: $pixId")
        val response = consultaChavePixClient.consulta(ConsultaChavePixRequest
            .newBuilder()
            .setPixId(ConsultaChavePixRequest.ConsultaPixId
                .newBuilder()
                .setIdCliente(idCliente)
                .setIdChave(pixId)
                .build())
            .build())
        return HttpResponse.ok(ChavePixResponse(response))
    }

}