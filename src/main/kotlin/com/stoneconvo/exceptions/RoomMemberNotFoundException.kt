package com.stoneconvo.exceptions

class RoomMemberNotFoundException(roomMemberId: RoomMemberId, message: String) :
    Exception("$message - RoomMemberId: $roomMemberId")
