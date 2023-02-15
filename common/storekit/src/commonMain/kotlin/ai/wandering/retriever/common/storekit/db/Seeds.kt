package ai.wandering.retriever.common.storekit.db

import ai.wandering.retriever.common.storekit.LocalChannel
import ai.wandering.retriever.common.storekit.LocalGraph
import ai.wandering.retriever.common.storekit.LocalMention
import ai.wandering.retriever.common.storekit.LocalNote
import ai.wandering.retriever.common.storekit.LocalTag
import ai.wandering.retriever.common.storekit.LocalUser
import ai.wandering.retriever.common.storekit.NoteChannel
import ai.wandering.retriever.common.storekit.NoteMention
import ai.wandering.retriever.common.storekit.UserFollowingGraph
import ai.wandering.retriever.common.storekit.UserFollowingTag
import ai.wandering.retriever.common.storekit.UserFollowingUser
import ai.wandering.retriever.common.storekit.UserPinnedChannel
import ai.wandering.retriever.common.storekit.UserPinnedGraph
import ai.wandering.retriever.common.storekit.UserPinnedNote


internal object Seeds {

    object Instants {
        const val NOW = "2023-02-14T20:44:00.000Z"
    }

    object Ids {
        object Graph {
            object Matt {
                const val MWR = "63ea2db3e27c93056213a592"
            }
        }

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

        object Channel {
            object Matt {
                const val SKIING = "63ea2ab51b3bdf812d274873"
                const val BLACK_CROWS = "63ea2adfff11aaee12985d6b"
                const val SERVER_DRIVEN_UI = "63ea2ae669563e4d25a42d01"
                const val KOTLIN_MULTI_PLATFORM = "63ea2aecaa89cea612bb35d1"
                const val COMPONENTBOX = "63ea2af73c7a6a0994f80198"
                const val STORE = "63ea2afd975d870e955f658d"
            }
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

    object Graphs {
        object Matt {
            val MWR = LocalGraph(
                id = Ids.Graph.Matt.MWR,
                name = "MWR",
                ownerId = Ids.User.MATT,
                created = Instants.NOW
            )
        }
    }

    object UserPinnedChannels {
        object Matt {
            val BlackCrows = UserPinnedChannel(Ids.User.MATT, Ids.Channel.Matt.BLACK_CROWS)
        }
    }

    object UserPinnedGraphs {
        object Matt {
            val MWR = UserPinnedGraph(Ids.User.MATT, Ids.Graph.Matt.MWR)
        }
    }

    object UserPinnedNotes {
        object Matt {
            val SkiedKillington = UserPinnedNote(Ids.User.MATT, Ids.Note.SKIED_KILLINGTON)
        }
    }

    object UserFollowingUsers {
        object Matt {
            val Tag = UserFollowingUser(Ids.User.MATT, Ids.User.TAG)
            val Trot = UserFollowingUser(Ids.User.MATT, Ids.User.TROT)
            val Tugg = UserFollowingUser(Ids.User.MATT, Ids.User.TUGG)
        }
    }

    object UserFollowingGraphs {
        object Tag {
            val MWR = UserFollowingGraph(Ids.User.TAG, Ids.Graph.Matt.MWR)
        }

        object Trot {
            val MWR = UserFollowingGraph(Ids.User.TROT, Ids.Graph.Matt.MWR)
        }

        object Tugg {
            val MWR = UserFollowingGraph(Ids.User.TUGG, Ids.Graph.Matt.MWR)
        }
    }

    object UserFollowingTags {
        object Matt {
            val BlackCrows = UserFollowingTag(Ids.User.MATT, Ids.Tag.BLACK_CROWS)
        }
    }

    object Notes {
        val SkiedKillington = LocalNote(
            id = Ids.Note.SKIED_KILLINGTON,
            userId = Ids.User.MATT,
            content = "Skied Killington with @Tag @Trot @Tugg. New #black-crows!",
            is_read = false,
            created = Instants.NOW
        )

        val WorkingOnComponentBox = LocalNote(
            id = Ids.Note.WORKING_ON_COMPONENTBOX,
            userId = Ids.User.MATT,
            content = "Working on ComponentBox #server-driven-UI",
            is_read = false,
            created = Instants.NOW
        )


        val WorkingOnStore = LocalNote(
            id = Ids.Note.WORKING_ON_STORE,
            userId = Ids.User.MATT,
            content = "Working on Store #kotlin-multi-platform",
            is_read = false,
            created = Instants.NOW
        )
    }

    object NoteChannels {
        val SkiedKillingtonBlackCrows = NoteChannel(
            noteId = Ids.Note.SKIED_KILLINGTON,
            channelId = Ids.Channel.Matt.BLACK_CROWS
        )

        val WorkingOnComponentBoxServerDrivenUi = NoteChannel(
            noteId = Ids.Note.WORKING_ON_COMPONENTBOX,
            channelId = Ids.Channel.Matt.SERVER_DRIVEN_UI
        )

        val WorkingOnComponentBoxKMP = NoteChannel(
            noteId = Ids.Note.WORKING_ON_COMPONENTBOX,
            channelId = Ids.Channel.Matt.KOTLIN_MULTI_PLATFORM
        )

        val WorkingOnStoreKMP = NoteChannel(
            noteId = Ids.Note.WORKING_ON_STORE,
            channelId = Ids.Channel.Matt.KOTLIN_MULTI_PLATFORM
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

    object Channels {
        object Matt {
            val BlackCrows = LocalChannel(
                id = Ids.Channel.Matt.BLACK_CROWS,
                userId = Ids.User.MATT,
                graphId = Ids.Graph.Matt.MWR,
                tagId = Ids.Tag.BLACK_CROWS,
                created = Instants.NOW
            )

            val Skiing = LocalChannel(
                id = Ids.Channel.Matt.SKIING,
                userId = Ids.User.MATT,
                graphId = Ids.Graph.Matt.MWR,
                tagId = Ids.Tag.SKIING,
                created = Instants.NOW
            )

            val ServerDrivenUi = LocalChannel(
                id = Ids.Channel.Matt.SERVER_DRIVEN_UI,
                userId = Ids.User.MATT,
                graphId = Ids.Graph.Matt.MWR,
                tagId = Ids.Tag.SERVER_DRIVEN_UI,
                created = Instants.NOW
            )

            val KotlinMultiPlatform = LocalChannel(
                id = Ids.Channel.Matt.KOTLIN_MULTI_PLATFORM,
                userId = Ids.User.MATT,
                graphId = Ids.Graph.Matt.MWR,
                tagId = Ids.Tag.KOTLIN_MULTI_PLATFORM,
                created = Instants.NOW
            )

            val Store = LocalChannel(
                id = Ids.Channel.Matt.STORE,
                userId = Ids.User.MATT,
                graphId = Ids.Graph.Matt.MWR,
                tagId = Ids.Tag.STORE,
                created = Instants.NOW
            )

            val ComponentBox = LocalChannel(
                id = Ids.Channel.Matt.COMPONENTBOX,
                userId = Ids.User.MATT,
                graphId = Ids.Graph.Matt.MWR,
                tagId = Ids.Tag.COMPONENTBOX,
                created = Instants.NOW
            )
        }
    }

    object Users {
        val Matt = LocalUser(
            id = Ids.User.MATT,
            username = "matt",
            name = "Matthew Ramotar",
            email = "matt@wandering.ai",
            avatarUrl = "https://avatars.githubusercontent.com/u/59468153?v=4",
            bio = "Currently at Dropbox, I am Tech Lead of a multi-team initiative involving iOS, Android, and server. I co-authored and help maintain MobileNativeFoundation/Store and dropbox/componentbox. I have consulted to YC-backed startups. I love skiing, hiking, cycling, tennis, and squash. And I am the best friend of a 4-year-old Golden Retriever named Tag!",
            coverImageUrl = "https://i.imgur.com/hnrCgWt.png"
        )

        val Tag = LocalUser(
            id = Ids.User.TAG,
            username = "tag",
            name = "Tag Ramotar",
            email = "tag@wandering.ai",
            avatarUrl = "https://i.imgur.com/UJ6rFC6.jpg",
            bio = null,
            coverImageUrl = "https://i.imgur.com/hnrCgWt.png"
        )

        val Trot = LocalUser(
            id = Ids.User.TROT,
            username = "trot",
            name = "Trot Ramotar",
            email = "trot@wandering.ai",
            avatarUrl = "https://i.imgur.com/RSx7KUI.jpg",
            bio = null,
            coverImageUrl = "https://i.imgur.com/hnrCgWt.png"
        )

        val Tugg = LocalUser(
            id = Ids.User.TUGG,
            username = "tugg",
            name = "Tugg Sleeper",
            email = "tugg@wandering.ai",
            avatarUrl = "https://i.imgur.com/J6mZ2cn.jpg",
            bio = null,
            coverImageUrl = "https://i.imgur.com/hnrCgWt.png"
        )
    }


    val users = listOf(Users.Matt, Users.Tag, Users.Trot, Users.Tugg)
    val tags = listOf(Tags.Skiing, Tags.BlackCrows, Tags.ServerDrivenUi, Tags.KotlinMultiPlatform, Tags.ComponentBox, Tags.Store)
    val mentions = listOf(Mention.MattTag, Mention.MattTrot, Mention.MattTugg)
    val notes = listOf(Notes.SkiedKillington, Notes.WorkingOnStore, Notes.WorkingOnComponentBox)
    val noteChannels =
        listOf(NoteChannels.SkiedKillingtonBlackCrows, NoteChannels.WorkingOnStoreKMP, NoteChannels.WorkingOnComponentBoxKMP, NoteChannels.WorkingOnComponentBoxServerDrivenUi)
    val noteMentions = listOf(NoteMentions.SkiedKillingtonTag, NoteMentions.SkiedKillingtonTrot, NoteMentions.SkiedKillingtonTugg)
    val graphs = listOf(Graphs.Matt.MWR)
    val userFollowingTags = listOf(UserFollowingTags.Matt.BlackCrows)
    val userFollowingGraphs = listOf(UserFollowingGraphs.Tag.MWR, UserFollowingGraphs.Trot.MWR, UserFollowingGraphs.Tugg.MWR)
    val userFollowingUsers = listOf(UserFollowingUsers.Matt.Tag, UserFollowingUsers.Matt.Trot, UserFollowingUsers.Matt.Tugg)
    val userPinnedGraphs = listOf(UserPinnedGraphs.Matt.MWR)
    val userPinnedChannels = listOf(UserPinnedChannels.Matt.BlackCrows)
    val userPinnedNotes = listOf(UserPinnedNotes.Matt.SkiedKillington)
    val channels =
        listOf(Channels.Matt.BlackCrows, Channels.Matt.KotlinMultiPlatform, Channels.Matt.ComponentBox, Channels.Matt.Skiing, Channels.Matt.Store, Channels.Matt.ServerDrivenUi)
}
