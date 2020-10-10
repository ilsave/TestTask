package ru.ilsave.testtask.model.afterLogin

import ru.ilsave.testtask.model.afterLogin.Response


//то что приходит, когда ты входишь в приложение (логинишься)
data class apiResponse(
    val count: Int,
    val response: Response,
    val status: Int,
    val statusCode: Int
)