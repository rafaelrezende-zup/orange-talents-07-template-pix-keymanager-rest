package br.com.zup.cadastra

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TipoChaveRequestTest {

    // Aleatoria
    @Test
    fun `deve validar chave aleatoria`() {
        val chaveAleatoria = TipoChaveRequest.ALEATORIA

        assertTrue(chaveAleatoria.valida(""))
        assertTrue(chaveAleatoria.valida(null))
    }

    @Test
    fun `nao deve validar chave aleatoria`() {
        val chaveAleatoria = TipoChaveRequest.ALEATORIA

        assertFalse(chaveAleatoria.valida("TESTE"))
    }

    // CPF
    @Test
    fun `deve validar chave cpf`() {
        val chaveAleatoria = TipoChaveRequest.CPF

        assertTrue(chaveAleatoria.valida("58092552053"))
    }

    @Test
    fun `nao deve validar chave cpf`() {
        val chaveAleatoria = TipoChaveRequest.CPF

        assertFalse(chaveAleatoria.valida("123456789"))
    }

    // EMAIL
    @Test
    fun `deve validar chave email`() {
        val chaveAleatoria = TipoChaveRequest.EMAIL

        assertTrue(chaveAleatoria.valida("teste@usuario.com"))
    }

    @Test
    fun `nao deve validar chave email`() {
        val chaveAleatoria = TipoChaveRequest.EMAIL

        assertFalse(chaveAleatoria.valida("TESTE"))
    }

    // TELEFONE
    @Test
    fun `deve validar chave telefone`() {
        val chaveAleatoria = TipoChaveRequest.TELEFONE

        assertTrue(chaveAleatoria.valida("+5537999999999"))
    }

    @Test
    fun `nao deve validar chave telefone`() {
        val chaveAleatoria = TipoChaveRequest.TELEFONE

        assertFalse(chaveAleatoria.valida("4002-8922"))
    }

}