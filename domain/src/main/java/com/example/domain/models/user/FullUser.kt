package com.example.domain.models.user

class FullUser(
    override val id: String,
    phone: UserPhoneNumber,
    var avatar: UserAvatar? = null,
    var name : String? = null,
    var privateData: UserPrivateData? = null,
    var sizeData: UserSizeData? = null,
) : User(id, phone) {
}