package com.stoneconvo.userInterface.controller

import com.stoneconvo.application.ChatRoomApplicationService
import com.stoneconvo.application.command.ChangeRoomNameCommand
import com.stoneconvo.application.command.CreateRoomCommand
import com.stoneconvo.common.authorization.AuthorizationService
import com.stoneconvo.userInterface.controller.requestBody.ChangeRoomNameRequestBody
import com.stoneconvo.userInterface.controller.requestBody.CreateRoomRequestBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/chat_room")
class ChatRoomController(
    @Autowired
    private val chatRoomApplicationService: ChatRoomApplicationService,
    @Autowired
    private val authorizationService: AuthorizationService
) {
    @PostMapping("/create")
    fun create(
        @Valid @RequestBody createRoomRequestBody: CreateRoomRequestBody
    ): String {
        val command = CreateRoomCommand.create(
            currentUserId = authorizationService.getCurrentUserId(),
            name = createRoomRequestBody.name
        )

        val roomId = chatRoomApplicationService.handleCreate(command)

        return roomId
    }

    @PostMapping("/change_name")
    fun changeName(
        @Valid @RequestBody changeRoomNameRequestBody: ChangeRoomNameRequestBody
    ) {
        val command = ChangeRoomNameCommand.create(
            currentUserId = authorizationService.getCurrentUserId(),
            chatRoomId = changeRoomNameRequestBody.roomId,
            newName = changeRoomNameRequestBody.name
        )

        chatRoomApplicationService.handleChangeName(command)
    }
}
