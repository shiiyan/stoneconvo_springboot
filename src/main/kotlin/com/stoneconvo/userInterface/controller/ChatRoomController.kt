package com.stoneconvo.userInterface.controller

import com.stoneconvo.application.ChatRoomApplicationService
import com.stoneconvo.application.command.AddMemberCommand
import com.stoneconvo.application.command.ChangeMemberNameCommand
import com.stoneconvo.application.command.ChangeRoomNameCommand
import com.stoneconvo.application.command.CreateRoomCommand
import com.stoneconvo.application.command.RemoveMemberCommand
import com.stoneconvo.common.authorization.AuthorizationService
import com.stoneconvo.userInterface.controller.requestBody.AddMemberRequestBody
import com.stoneconvo.userInterface.controller.requestBody.ChangeMemberNameRequestBody
import com.stoneconvo.userInterface.controller.requestBody.ChangeRoomNameRequestBody
import com.stoneconvo.userInterface.controller.requestBody.CreateRoomRequestBody
import com.stoneconvo.userInterface.controller.requestBody.RemoveMemberRequestBody
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

    @PostMapping("/add_member")
    fun addMember(
        @Valid @RequestBody addMemberRequestBody: AddMemberRequestBody
    ) {
        val command = AddMemberCommand.create(
            chatRoomId = addMemberRequestBody.chatRoomId,
            name = addMemberRequestBody.memberName,
            userAccountId = addMemberRequestBody.userAccountId,
            currentUserId = authorizationService.getCurrentUserId()
        )

        chatRoomApplicationService.handleAddMember(command)
    }

    @PostMapping("/remove_member")
    fun removeMember(
        @Valid @RequestBody removeMemberRequestBody: RemoveMemberRequestBody
    ) {
        val command = RemoveMemberCommand.create(
            chatRoomId = removeMemberRequestBody.chatRoomId,
            userAccountId = removeMemberRequestBody.userAccountId,
            currentUserId = authorizationService.getCurrentUserId()
        )

        chatRoomApplicationService.handleRemoveMember(command)
    }

    @PostMapping("/change_member_name")
    fun changeMemberName(
        @Valid @RequestBody changeMemberNameRequestBody: ChangeMemberNameRequestBody
    ) {
        val command = ChangeMemberNameCommand.create(
            chatRoomId = changeMemberNameRequestBody.chatRoomId,
            name = changeMemberNameRequestBody.name,
            userAccountId = changeMemberNameRequestBody.userAccountId,
            currentUserId = authorizationService.getCurrentUserId()
        )

        chatRoomApplicationService.handleChangeMemberName(command)
    }
}
