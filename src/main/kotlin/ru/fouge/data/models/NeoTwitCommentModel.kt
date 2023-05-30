package ru.fouge.data.models

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
data class NeoTwitCommentModel(
    @Id
    @GeneratedValue
    val id: Long? = null,

    val text: String? = null,

    @Relationship(type = "CREATED_BY", direction = Relationship.Direction.OUTGOING)
    val author: NeoUserModel? = null
)
