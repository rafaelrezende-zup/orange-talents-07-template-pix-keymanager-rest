package br.com.zup.cadastra

import br.com.zup.KeymanagerCadastraGrpcServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.validation.Valid

@Validated
@Controller("/cliente/{idCliente}/pix")
class CadastraChavePixController(private val cadastraChavePixClient: KeymanagerCadastraGrpcServiceGrpc.KeymanagerCadastraGrpcServiceBlockingStub) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Post
    fun cadastra(idCliente: String, @Valid @Body request: CadastraChavePixRequest): HttpResponse<Any> {
        val response = cadastraChavePixClient.cadastra(request.paraNovaChavePixResponse(idCliente))
        return HttpResponse.created(HttpResponse.uri("/cliente/$idCliente/pix/${response.idPix}"))
    }

}