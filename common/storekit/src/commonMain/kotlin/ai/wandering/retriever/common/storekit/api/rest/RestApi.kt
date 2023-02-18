package ai.wandering.retriever.common.storekit.api.rest

interface RestApi<Id : Any, Input : Any, Output : Any> :
    Get<Id, Output>,
    Upsert<Input>,
    Delete<Id>



