package com.taaggg.retriever.android.app.db

import com.taaggg.retriever.common.storekit.entities.note.RealMention
import com.taaggg.retriever.common.storekit.entities.note.RealNote
import com.taaggg.retriever.common.storekit.entities.note.RealTag


internal object RetrieverDatabase {
    val Skiing = RealTag("skiing")
    val BlackCrows = RealTag("black-crows")
    val ServerDrivenUi = RealTag("server-driven-UI")
    val KotlinMultiPlatform = RealTag("kotlin-multi-platform")
    val ComponentBox = RealTag("componentbox")
    val Store = RealTag("store")

    val Tag = RealMention("Tag")
    val Trot = RealMention("Trot")
    val Tugg = RealMention("Tugg")

    val SkiedKillingtonWithTag = RealNote(
        id = "1",
        content = "Skied Killington with @Tag @Trot @Tugg. New #black-crows!",
        tags = listOf(BlackCrows, Skiing),
        mentions = listOf(Tag, Trot, Tugg)
    )

    val WorkingOnComponentBox = RealNote(
        id = "2",
        content = "Working on ComponentBox #server-driven-UI",
        tags = listOf(ComponentBox, ServerDrivenUi, KotlinMultiPlatform)
    )

    val WorkingOnStore = RealNote(
        id = "3",
        content = "Working on Store",
        tags = listOf(Store, KotlinMultiPlatform)
    )


}
