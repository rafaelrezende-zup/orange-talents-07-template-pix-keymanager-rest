package br.com.zup.validators

import br.com.zup.cadastra.CadastraChavePixRequest
import jakarta.inject.Singleton
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [ValidPixKeyValidator::class])
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.PROPERTY)
annotation class ValidPixKey(
    val message: String = "Formato da chave PIX inválido.",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

@Singleton
class ValidPixKeyValidator : ConstraintValidator<ValidPixKey, CadastraChavePixRequest> {
    override fun isValid(value: CadastraChavePixRequest?, context: ConstraintValidatorContext?): Boolean {
        return value?.tipoChave!!.valida(value.chave)
    }
}