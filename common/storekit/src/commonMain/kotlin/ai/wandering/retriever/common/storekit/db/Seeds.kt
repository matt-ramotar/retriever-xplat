package ai.wandering.retriever.common.storekit.db

import ai.wandering.retriever.common.storekit.LocalMention
import ai.wandering.retriever.common.storekit.LocalNote
import ai.wandering.retriever.common.storekit.LocalTag
import ai.wandering.retriever.common.storekit.LocalUser
import ai.wandering.retriever.common.storekit.NoteMention
import ai.wandering.retriever.common.storekit.NoteTag


internal object Seeds {
    object Ids {
        object Tag {
            const val SKIING = "63e9219cb06004ccb33ab2c1"
            const val BLACK_CROWS = "63e921a90a69d79361207da7"
            const val SERVER_DRIVEN_UI = "63e921b820e35dbd36423b95"
            const val KOTLIN_MULTI_PLATFORM = "63e921cf72b1cb7a347f77b5"
            const val COMPONENTBOX = "63e921e1e153bb52f124fabb"
            const val STORE = "63e921ead2e975ce0723a6bb"
        }

        object User {
            const val MATT = "63e5a4bfe9bbc74b66f03de7"
            const val TAG = "63e9220470fa231d40857ae9"
            const val TROT = "63e9220c7b65c0b4bccf8fe5"
            const val TUGG = "63e9221528bbe7827cf56a7b"
        }

        object Note {
            const val SKIED_KILLINGTON = "63e922368b84a3c4d532b879"
            const val WORKING_ON_STORE = "63e9223b95695bef560f92f1"
            const val WORKING_ON_COMPONENTBOX = "63e92241a1761a665190c48c"
        }
    }

    object Tags {
        val Skiing = LocalTag(Ids.Tag.SKIING, "skiing")
        val BlackCrows = LocalTag(Ids.Tag.BLACK_CROWS, "black-crows")
        val ServerDrivenUi = LocalTag(Ids.Tag.SERVER_DRIVEN_UI, "server-driven-UI")
        val KotlinMultiPlatform = LocalTag(Ids.Tag.KOTLIN_MULTI_PLATFORM, "kotlin-multi-platform")
        val ComponentBox = LocalTag(Ids.Tag.COMPONENTBOX, "componentbox")
        val Store = LocalTag(Ids.Tag.STORE, "store")

    }

    object Mention {
        val MattTag = LocalMention(Ids.User.MATT, Ids.User.TAG)
        val MattTrot = LocalMention(Ids.User.MATT, Ids.User.TROT)
        val MattTugg = LocalMention(Ids.User.MATT, Ids.User.TUGG)
    }

    object Notes {
        val SkiedKillington = LocalNote(
            id = Ids.Note.SKIED_KILLINGTON,
            userId = Ids.User.MATT,
            content = "Skied Killington with @Tag @Trot @Tugg. New #black-crows!",
            is_read = false
        )

        val WorkingOnComponentBox = LocalNote(
            id = Ids.Note.WORKING_ON_COMPONENTBOX,
            userId = Ids.User.MATT,
            content = "Working on ComponentBox #server-driven-UI",
            is_read = false
        )


        val WorkingOnStore = LocalNote(
            id = Ids.Note.WORKING_ON_STORE,
            userId = Ids.User.MATT,
            content = "Working on Store #kotlin-multi-platform",
            is_read = false
        )
    }

    object NoteTags {
        val SkiedKillingtonBlackCrows = NoteTag(
            noteId = Ids.Note.SKIED_KILLINGTON,
            tagId = Ids.Tag.BLACK_CROWS
        )

        val WorkingOnComponentBoxServerDrivenUi = NoteTag(
            noteId = Ids.Note.WORKING_ON_COMPONENTBOX,
            tagId = Ids.Tag.SERVER_DRIVEN_UI
        )

        val WorkingOnComponentBoxKMP = NoteTag(
            noteId = Ids.Note.WORKING_ON_COMPONENTBOX,
            tagId = Ids.Tag.KOTLIN_MULTI_PLATFORM
        )

        val WorkingOnStoreKMP = NoteTag(
            noteId = Ids.Note.WORKING_ON_STORE,
            tagId = Ids.Tag.KOTLIN_MULTI_PLATFORM
        )
    }

    object NoteMentions {
        val SkiedKillingtonTag = NoteMention(
            noteId = Ids.Note.SKIED_KILLINGTON,
            userId = Ids.User.MATT,
            otherUserId = Ids.User.TAG
        )

        val SkiedKillingtonTrot = NoteMention(
            noteId = Ids.Note.SKIED_KILLINGTON,
            userId = Ids.User.MATT,
            otherUserId = Ids.User.TROT
        )

        val SkiedKillingtonTugg = NoteMention(
            noteId = Ids.Note.SKIED_KILLINGTON,
            userId = Ids.User.MATT,
            otherUserId = Ids.User.TUGG
        )
    }

    object Users {
        val Matt = LocalUser(
            id = Ids.User.MATT,
            name = "Matthew Ramotar",
            email = "matt@wandering.ai",
            avatarUrl = "https://avatars.githubusercontent.com/u/59468153?v=4"
        )

        val Tag = LocalUser(
            id = Ids.User.TAG,
            name = "Tag Ramotar",
            email = "tag@wandering.ai",
            avatarUrl = "https://i.imgur.com/UJ6rFC6.jpg"
        )

        val Trot = LocalUser(
            id = Ids.User.TROT,
            name = "Trot Ramotar",
            email = "trot@wandering.ai",
            avatarUrl = "https://i.imgur.com/RSx7KUI.jpg"
        )

        val Tugg = LocalUser(
            id = Ids.User.TUGG,
            name = "Tugg Sleeper",
            email = "tugg@wandering.ai",
            avatarUrl = "https://i.imgur.com/J6mZ2cn.jpg"
        )
    }


    val users = listOf(Users.Matt, Users.Tag, Users.Trot, Users.Tugg)
    val tags = listOf(Tags.Skiing, Tags.BlackCrows, Tags.ServerDrivenUi, Tags.KotlinMultiPlatform, Tags.ComponentBox, Tags.Store)
    val mentions = listOf(Mention.MattTag, Mention.MattTrot, Mention.MattTugg)
    val notes = listOf(Notes.SkiedKillington, Notes.WorkingOnStore, Notes.WorkingOnComponentBox)
    val noteTags = listOf(NoteTags.WorkingOnComponentBoxKMP, NoteTags.WorkingOnComponentBoxServerDrivenUi, NoteTags.WorkingOnStoreKMP, NoteTags.SkiedKillingtonBlackCrows)
    val noteMentions = listOf(NoteMentions.SkiedKillingtonTag, NoteMentions.SkiedKillingtonTrot, NoteMentions.SkiedKillingtonTugg)
}
