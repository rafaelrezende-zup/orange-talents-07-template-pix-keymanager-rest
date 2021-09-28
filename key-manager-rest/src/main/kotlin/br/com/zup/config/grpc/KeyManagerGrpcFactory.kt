package br.com.zup.config.grpc

import br.com.zup.KeymanagerCadastraGrpcServiceGrpc
import br.com.zup.KeymanagerConsultaGrpcServiceGrpc
import br.com.zup.KeymanagerListaGrpcServiceGrpc
import br.com.zup.KeymanagerRemoveGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import jakarta.inject.Singleton

@Factory
class KeyManagerGrpcFactory(@GrpcChannel("keymanager") val channel: ManagedChannel) {

    @Singleton
    fun cadastraChavePix() = KeymanagerCadastraGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun removeChavePix() = KeymanagerRemoveGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun consultaChavePix() = KeymanagerConsultaGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listaChavePix() = KeymanagerListaGrpcServiceGrpc.newBlockingStub(channel)

}