package ai.wandering.retriever.common.storekit.api.rest

interface RestApi<Id : Any, Network : Any, CreateReq : Any, CreateRes : Any, UpdateReq : Any, UpdateRes : Any, DeleteRes : Any> :
    Create<CreateReq, CreateRes>,
    Read<Id, Network>,
    Update<UpdateReq, UpdateRes>,
    Delete<Id, DeleteRes>



