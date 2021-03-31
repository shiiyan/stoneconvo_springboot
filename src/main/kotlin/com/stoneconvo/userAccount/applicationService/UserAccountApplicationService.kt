package com.stoneconvo.userAccount.applicationService

import com.stoneconvo.administrator.domain.AdministratorRepository
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.userAccount.applicationService.command.ChangeNameCommand
import com.stoneconvo.userAccount.applicationService.command.CreateCommand
import com.stoneconvo.userAccount.domain.UserAccount
import com.stoneconvo.userAccount.domain.UserAccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserAccountApplicationService(
    @Autowired
    private val administratorRepository: AdministratorRepository,
    @Autowired
    private val userAccountRepository: UserAccountRepository,
) {
    @Transactional
    fun create(createCommand: CreateCommand): String {
        val administrator = administratorRepository.findByUserId(createCommand.currentUserId)
            ?: throw CustomException.AdministratorNotFoundException(
                userId = createCommand.currentUserId
            )

        val newUserAccount = UserAccount.create(
            name = createCommand.name,
            passwordHash = createCommand.passwordHash,
            creator = administrator
        )

        userAccountRepository.insert(newUserAccount)

        return newUserAccount.id.value
    }

    @Transactional
    fun changeName(changeNameCommand: ChangeNameCommand) {
        val foundUserAccount =
            userAccountRepository.findByUserId(changeNameCommand.currentUserId)
                ?: throw CustomException.UserAccountNotFoundException(
                    userId = changeNameCommand.currentUserId.value
                )

        foundUserAccount.changeName(changeNameCommand.newName)

        userAccountRepository.update(foundUserAccount)
    }

    // changePassword
}