package ru.fouge.data.models

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity

@NodeEntity
data class NeoUserModel(
    @Id
    @GeneratedValue
    val id: Long? = null,

    val login: String,
    val password: String
)
