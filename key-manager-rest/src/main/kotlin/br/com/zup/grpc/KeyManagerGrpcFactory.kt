package br.com.zup.grpc

import br.com.zup.KeymanagerCadastraGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import jakarta.inject.Singleton

@Factory
class KeyManagerGrpcFactory(@GrpcChannel("keymanager") val channel: ManagedChannel) {

    @Singleton
    fun cadastraChavePix() = KeymanagerCadastraGrpcServiceGrpc.newBlockingStub(channel)

}