package br.com.zup.consulta

import br.com.zup.ConsultaChavePixResponse
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class ChavePixResponse(response: ConsultaChavePixResponse) {

    val idCliente = response.idCliente
    val idChave = response.idChave
    val chavePix = response.chavePix.chave

    val criadaEm = response.chavePix.dataCriacao.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
    }

    val contaBancaria = mapOf(
        Pair("nomeInstituicao", response.chavePix.contaBancaria.nomeInstituicao),
        Pair("nomeTitular", response.chavePix.contaBancaria.nomeTitular),
        Pair("cpfTitular", response.chavePix.contaBancaria.cpfTitular),
        Pair("agencia", response.chavePix.contaBancaria.agencia),
        Pair("conta", response.chavePix.contaBancaria.conta),
        Pair("tipoConta", response.chavePix.contaBancaria.tipoConta)
    )

}
