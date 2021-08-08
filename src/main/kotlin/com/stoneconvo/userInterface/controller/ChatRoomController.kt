package com.stoneconvo.userInterface.controller

import com.stoneconvo.application.chatRoom.AddMemberApplicationService
import com.stoneconvo.application.chatRoom.ChangeMemberNameApplicationService
import com.stoneconvo.application.chatRoom.ChangeRoomNameApplicationService
import com.stoneconvo.application.chatRoom.CreateRoomApplicationService
import com.stoneconvo.application.chatRoom.RemoveMemberApplicationService
import com.stoneconvo.application.chatRoom.command.AddMemberCommand
import com.stoneconvo.application.chatRoom.command.ChangeMemberNameCommand
import com.stoneconvo.application.chatRoom.command.ChangeRoomNameCommand
import com.stoneconvo.application.chatRoom.command.CreateRoomCommand
import com.stoneconvo.application.chatRoom.command.RemoveMemberCommand
import com.stoneconvo.common.authorization.AuthorizationService
import com.stoneconvo.userInterface.controller.requestBody.chatRoom.AddMemberRequestBody
import com.stoneconvo.userInterface.controller.requestBody.chatRoom.ChangeMemberNameRequestBody
import com.stoneconvo.userInterface.controller.requestBody.chatRoom.ChangeRoomNameRequestBody
import com.stoneconvo.userInterface.controller.requestBody.chatRoom.CreateRoomRequestBody
import com.stoneconvo.userInterface.controller.requestBody.chatRoom.RemoveMemberRequestBody
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
    private val createRoomApplicationService: CreateRoomApplicationService,
    @Autowired
    private val changeRoomNameApplicationService: ChangeRoomNameApplicationService,
    @Autowired
    private val addMemberApplicationService: AddMemberApplicationService,
    @Autowired
    private val removeMemberApplicationService: RemoveMemberApplicationService,
    @Autowired
    private val changeMemberNameApplicationService: ChangeMemberNameApplicationService,
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

        val roomId = createRoomApplicationService.handleCreate(command)

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

        changeRoomNameApplicationService.handleChangeName(command)
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

        addMemberApplicationService.handleAddMember(command)
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

        removeMemberApplicationService.handleRemoveMember(command)
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

        changeMemberNameApplicationService.handleChangeMemberName(command)
    }
}
