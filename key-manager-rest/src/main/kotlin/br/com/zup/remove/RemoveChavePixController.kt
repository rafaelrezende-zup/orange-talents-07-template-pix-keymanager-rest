package br.com.zup.remove

import br.com.zup.KeymanagerCadastraGrpcServiceGrpc
import br.com.zup.KeymanagerRemoveGrpcServiceGrpc
import br.com.zup.RemoveChavePixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.validation.Valid

@Validated
@Controller("/cliente/{idCliente}/pix/{pixId}")
class RemoveChavePixController(private val removeChavePixClient: KeymanagerRemoveGrpcServiceGrpc.KeymanagerRemoveGrpcServiceBlockingStub) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Delete
    fun remove(idCliente: String, pixId: String): HttpResponse<Any> {
        val request = RemoveChavePixRequest
            .newBuilder()
            .setIdCliente(idCliente)
            .setIdChave(pixId)
            .build()
        logger.info("Iniciando fluxo de remoção de chave pix: $request")
        removeChavePixClient.remove(request)
        return HttpResponse.ok()
    }

}