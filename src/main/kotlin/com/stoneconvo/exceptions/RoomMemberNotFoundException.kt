package com.stoneconvo.exceptions

import com.stoneconvo.domain.roomMember.RoomMemberId

class RoomMemberNotFoundException(roomMemberId: RoomMemberId, message: String) :
    Exception("$message - RoomMemberId: $roomMemberId")
