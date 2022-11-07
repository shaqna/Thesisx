package com.ngedev.thesisx.data.mapper

import com.ngedev.thesisx.data.source.remote.response.CupBoardResponse
import com.ngedev.thesisx.domain.model.CupBoard


fun CupBoardResponse.toModel(): CupBoard {
    return CupBoard(
        this.key
    )
}