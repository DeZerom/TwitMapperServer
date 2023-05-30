package ru.fouge.data.models

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
data class NeoTwitModel(
    @Id
    @GeneratedValue
    val id: Long? = null,

    val lat: Double? = null,
    val lon: Double? = null,
    val text: String? = null,

    @Relationship(type = "CREATED_BY", direction = Relationship.Direction.OUTGOING)
    val author: NeoUserModel? = null,
    @Relationship(type = "HAS_COMMENT", direction = Relationship.Direction.OUTGOING)
    val comments: List<NeoTwitCommentModel> = emptyList()
)
