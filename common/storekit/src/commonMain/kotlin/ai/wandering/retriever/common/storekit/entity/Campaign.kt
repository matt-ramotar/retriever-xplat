package ai.wandering.retriever.common.storekit.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Campaign {

    @Serializable
    data class Network(
        val content: String? = null,
        val type: Type,
        val sequencedCampaign: Network? = null,
        val childrenCampaigns: List<Network>? = null

    ) : Campaign()

    @Serializable
    data class Output(
        val content: Content? = null,
        val type: Type,
        val sequencedCampaign: Output? = null,
        val childrenCampaigns: List<Output>? = null

    ) : Campaign()


    enum class Type(val value: String) {
        MobileBanner("MobileBanner"),
        MobileUpgradePage("MobileUpgradePage");

        companion object {
            val values = Type.values().associateBy { it.value }
            fun lookup(value: String): Type = values[value]!!
        }

    }
}

@Serializable
data class Modifier(
    val containerColor: String,
    val contentColor: String,
)

@Serializable
data class Events(
    val onClick: Event.LaunchUpsell? = null,
    val onDismiss: Event.Dismiss? = null
)

@Serializable
sealed class Event {
    @Serializable
    data class LaunchUpsell(val _id: String) : Event()

    @Serializable
    object Dismiss : Event()
}

@Serializable
enum class EventType(val value: String) {
    Dismiss("Dismiss"),
    LaunchUpsell("LaunchUpsell");

    companion object {
        val values = EventType.values().associateBy { it.value }
        fun lookup(value: String) = values[value]
    }
}

@Serializable
data class Content(
    val _id: String,
    val campaign: Campaign.Type,
    val modifier: Modifier? = null,
    val events: Events? = null,
    val root: Component
)

@Serializable
sealed class Component {

    @Serializable
    @SerialName("Row")
    data class Row(
        val horizontalArrangement: String,
        val verticalAlignment: String,
        val components: List<Component>
    ) : Component()

    @Serializable
    @SerialName("Column")
    data class Column(
        val verticalArrangement: String,
        val horizontalAlignment: String,
        val components: List<Component>
    ) : Component()

    @Serializable
    @SerialName("Text")

    data class Text(
        val content: String,
        val textStyle: String
    ) : Component()
}

@Serializable
enum class ComponentType(val value: String) {
    Row("Row"),
    Text("Text");

    companion object {
        val values = ComponentType.values().associateBy { it.value }
        fun lookup(value: String) = values[value]
    }
}

// const generateBanner = (id, cta, sequencedCampaignId) => ({
//  _id: id,
//  campaign: 'MobileBanner',
//  modifier: {
//    containerColor: 'PrimaryContainer',
//    contentColor: 'OnPrimary',
//  },
//  events: {
//    onClick: {
//      type: 'LaunchUpsell',
//      _id: sequencedCampaignId,
//    },
//    onDismiss: {
//      type: 'Dismiss',
//    },
//  },
//  root: {
//    type: 'Row',
//    horizontalArrangement: 'Center',
//    verticalAlignment: 'CenterVertically',
//    components: [
//      {
//        type: 'Text',
//        content: cta,
//        textStyle: 'HeadlineMedium',
//      },
//    ],
//  },
//});


