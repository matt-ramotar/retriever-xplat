package ai.wandering.retriever.android.feature.account_tab

import ai.wandering.retriever.common.storekit.entity.AuthenticatedUser
import ai.wandering.retriever.common.storekit.entity.Campaign
import ai.wandering.retriever.common.storekit.repository.CampaignRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CampaignViewModel(private val user: AuthenticatedUser, private val campaignRepository: CampaignRepository) : ViewModel() {

    private val _state: MutableStateFlow<Campaign.Output?> = MutableStateFlow(null)
    val state: StateFlow<Campaign.Output?> = _state

    init {
        viewModelScope.launch {
            when (val response = campaignRepository.get(user.id, Campaign.Type.MobileBanner)) {
                null -> {}
                else -> {
                    println(response)
                    _state.value = response
                }
            }
        }
    }

}