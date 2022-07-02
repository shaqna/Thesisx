package com.ngedev.thesisx.utils

import java.time.Year

object FirebaseConstant {
    object Collections {
        const val USER_COLLECTION = "librarian"
        const val THESIS_COLLECTION = "Notebook"
        const val LOCKER_COLLECTION = "locker_password"
    }

    object Fields {
        const val ABSTRACT_FIELD = "abstract"
        const val BORROWED_STATUS_FIELD = "borrowed"
        const val TITLE_FIELD = "title"
        const val AUTHOR_FIELD = "author"
        const val YEAR_FIELD = "year"
        const val ID_FIELD = "id"
        const val CATEGORY_FIELD = "category"
        const val FAVORITE_FIELD = "favorite"
        const val USERNAME_FIELD = "username"
        const val BORROWING_FIELD = "borrowing"
        const val PASSWORD_FIELD = "key"
    }

    object Docs {
        const val LOCKER_DOCUMENT = "Locker"
    }
}