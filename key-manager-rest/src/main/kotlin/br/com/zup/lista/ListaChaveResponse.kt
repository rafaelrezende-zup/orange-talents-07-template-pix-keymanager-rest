package br.com.zup.lista

import br.com.zup.ListaChavePixResponse
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class ListaChaveResponse(it: ListaChavePixResponse.ChavePix) {

    val pixId = it.idChave
    val chavePix = it.chave
    val tipoChave = it.tipoChave
    val tipoConta = it.tipoConta
    val dataCriacao = it.dataCriacao.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
    }

}
