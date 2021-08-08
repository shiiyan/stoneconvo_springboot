package com.stoneconvo.domain.chatRoom

import com.stoneconvo.common.domain.Entity
import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.domain.administrator.Administrator
import com.stoneconvo.domain.chatRoom.roomMember.RoomMember

class ChatRoom(
    override val id: ChatRoomId,
    var name: ChatRoomName,
    val owner: Administrator,
    val members: MutableList<RoomMember>,
) : Entity<ChatRoom.Dto>(id) {
    companion object {
        fun create(
            name: ChatRoomName,
            owner: Administrator
        ) = ChatRoom(
            id = ChatRoomId.create(),
            name = name,
            owner = owner,
            members = mutableListOf(),
        )

        fun fromDto(dto: Dto) = ChatRoom(
            id = ChatRoomId(dto.id),
            name = ChatRoomName(dto.name),
            owner = Administrator(UserAccountId(dto.ownerId)),
            members = dto.members.map { RoomMember.fromDto(it) }.toMutableList()
        )
    }

    fun changeName(newName: ChatRoomName, currentUserId: UserAccountId) {
        if (!isMemberExist(currentUserId)) {
            throw CustomException.ChatRoomMemberNotExistException(
                chatRoomId = id,
                userAccountId = currentUserId
            )
        }

        name = newName
    }

    fun isMemberExist(userAccountId: UserAccountId): Boolean = members
        .map { it.userAccountId }.contains(userAccountId)

    fun isOwner(userAccountId: UserAccountId): Boolean = owner == Administrator(userAccountId)

    fun addMember(newMember: RoomMember) {
        if (isMemberFull()) {
            throw CustomException.ChatRoomMemberFullException(id)
        }

        if (isMemberExist(newMember.userAccountId)) {
            throw CustomException.ChatRoomMemberExistException(
                chatRoomId = id,
                userAccountId = newMember.userAccountId
            )
        }

        members.add(newMember)
    }

    fun removeMember(memberIdToRemove: UserAccountId) {
        if (!isMemberExist(memberIdToRemove)) {
            throw CustomException.ChatRoomMemberNotExistException(
                chatRoomId = id,
                userAccountId = memberIdToRemove
            )
        }

        members.removeIf { it.userAccountId == memberIdToRemove }
    }

    fun changeMemberName(memberWithNewName: RoomMember) {
        if (!isMemberExist(memberWithNewName.userAccountId)) {
            throw CustomException.ChatRoomMemberNotExistException(
                chatRoomId = id,
                userAccountId = memberWithNewName.userAccountId
            )
        }

        members.replaceAll {
            if (it.userAccountId == memberWithNewName.userAccountId) memberWithNewName
            else it
        }
    }

    fun verifyAuthorityToAddMember(currentUserId: UserAccountId) {
        if (
            !isMemberExist(currentUserId) &&
            !isOwner(currentUserId)
        ) {
            throw CustomException.AddRoomMemberUnauthorizedException(
                userAccountId = currentUserId,
                chatRoomId = id
            )
        }
    }

    private fun isMemberFull(): Boolean = members.size > 30

    override fun toDto(): Dto = Dto(
        id = id.value,
        name = name.value,
        ownerId = owner.id.value,
        members = members.map { it.toDto() }
    )

    data class Dto(
        override val id: String,
        val name: String,
        val ownerId: String,
        val members: List<RoomMember.Dto>
    ) : Entity.Dto(id)
}
