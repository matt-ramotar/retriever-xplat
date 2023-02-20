package ai.wandering.retriever.common.storekit.converter

import ai.wandering.retriever.common.storekit.entity.NoteRelationship

fun NoteRelationship.Network.asNodeOutput() = NoteRelationship.Output.Node(
    id = _id,
    noteId = noteId,
    otherNoteId = otherNoteId,
    type = NoteRelationship.Type.lookup(type)
)