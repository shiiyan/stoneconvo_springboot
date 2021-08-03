package com.stoneconvo.userInterface.controller

import com.stoneconvo.application.MessageApplicationService
import com.stoneconvo.application.command.message.EditMessageCommand
import com.stoneconvo.application.command.message.SendMessageCommand
import com.stoneconvo.common.authorization.AuthorizationService
import com.stoneconvo.userInterface.controller.requestBody.message.EditMessageRequestBody
import com.stoneconvo.userInterface.controller.requestBody.message.SendMessageRequestBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/message")
class MessageController(
    @Autowired
    private val messageApplicationService: MessageApplicationService,
    @Autowired
    private val authorizationService: AuthorizationService
) {
    @PostMapping("/send")
    fun send(
        @Valid @RequestBody sendMessageRequestBody: SendMessageRequestBody
    ): String {
        val command = SendMessageCommand.create(
            messageContent = sendMessageRequestBody.messageContent,
            roomId = sendMessageRequestBody.roomId,
            currentUserId = authorizationService.getCurrentUserId()
        )

        val messageId = messageApplicationService.handleSend(command)

        return messageId
    }

    @PostMapping("/edit")
    fun edit(
        @Valid @RequestBody editMessageRequestBody: EditMessageRequestBody
    ) {
        val command = EditMessageCommand.create(
            messageId = editMessageRequestBody.messageId,
            newContent = editMessageRequestBody.messageContent,
            currentUserId = authorizationService.getCurrentUserId()
        )

        messageApplicationService.handleEdit(command)
    }
}
