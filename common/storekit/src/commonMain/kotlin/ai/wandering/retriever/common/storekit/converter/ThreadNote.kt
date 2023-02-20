package ai.wandering.retriever.common.storekit.converter

import ai.wandering.retriever.common.storekit.entity.ThreadNote

fun ThreadNote.Network.asNodeOutput() = ThreadNote.Output.Node(
    id = _id,
    threadId = threadId,
    noteId = noteId,
    parentNoteId = parentNoteId
)