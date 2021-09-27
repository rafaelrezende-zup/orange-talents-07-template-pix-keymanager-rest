package br.com.zup.cadastra

import br.com.zup.NovaChavePixRequest
import br.com.zup.TipoChave
import br.com.zup.TipoConta
import br.com.zup.validators.ValidPixKey
import io.micronaut.core.annotation.Introspected
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ValidPixKey
@Introspected
data class CadastraChavePixRequest(
    @field:NotNull val tipoChave: TipoChaveRequest?,
    @field:Size(max = 77) val chave: String,
    @field:NotNull val tipoConta: TipoContaRequest?
) {
    fun paraNovaChavePixResponse(idCliente: String): NovaChavePixRequest {
        return NovaChavePixRequest
            .newBuilder()
            .setIdCliente(idCliente)
            .setTipoChave(tipoChave?.attr ?: TipoChave.UNKNOWN_TIPOCHAVE)
            .setChave(chave ?: "")
            .setTipoConta(tipoConta?.attr ?: TipoConta.UNKNOWN_TIPOCONTA)
            .build()
    }

}

enum class TipoChaveRequest(val attr: TipoChave){
    CPF(TipoChave.CPF) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) return false
            return CPFValidator().run {
                initialize(null)
                isValid(chave, null)
            }
        }
    },
    TELEFONE(TipoChave.TELEFONE) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) return false
            return chave.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }
    },
    EMAIL(TipoChave.EMAIL) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) return false
            return chave.matches("^[A-Za-z0-9+_.-]+@(.+)\$".toRegex())
        }
    },
    ALEATORIA(TipoChave.ALEATORIA) {
        override fun valida(chave: String?): Boolean = chave.isNullOrBlank()
    };

    abstract fun valida(chave: String?): Boolean
}

enum class TipoContaRequest(val attr: TipoConta){
    CONTA_CORRENTE(TipoConta.CONTA_CORRENTE),
    CONTA_POUPANCA(TipoConta.CONTA_POUPANCA)
}