package com.taaggg.retriever.common.storekit.entities.note


interface Note {
    val id: String
    val content: String
    val tags: List<Tag>
    val mentions: List<Mention>
    val parents: List<Reference>
    val references: List<Reference>
    val children: List<Reference>
}


data class RealNote(
    override val id: String,
    override val content: String,
    override val tags: List<Tag> = listOf(),
    override val mentions: List<Mention> = listOf(),
    override val parents: List<Reference> = listOf(),
    override val references: List<Reference> = listOf(),
    override val children: List<Reference> = listOf()
) : Note


interface Tag {
    val name: String
}

data class RealTag(
    override val name: String,
) : Tag


interface Mention {
    val name: String
}

data class RealMention(
    override val name: String
) : Mention

interface Reference {
    val noteId: String
}

data class RealReference(
    override val noteId: String
) : Reference