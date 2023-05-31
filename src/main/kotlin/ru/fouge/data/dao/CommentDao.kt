package ru.fouge.data.dao

import ru.fouge.data.db.NeoDB
import ru.fouge.data.models.NeoTwitCommentModel

object CommentDao {

    suspend fun getById(id: Long): NeoTwitCommentModel? {
        return NeoDB.executeQueryWithResult { load(NeoTwitCommentModel::class.java, id) }
    }

    suspend fun deleteComment(comment: NeoTwitCommentModel): Boolean {
        return NeoDB.executeQuery { delete(comment) }
    }
}
