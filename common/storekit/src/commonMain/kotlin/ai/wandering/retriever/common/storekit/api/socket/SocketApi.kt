package ai.wandering.retriever.common.storekit.api.socket

interface SocketApi<Id : Any, Response : Any>
    : Subscribe<Id, Response>