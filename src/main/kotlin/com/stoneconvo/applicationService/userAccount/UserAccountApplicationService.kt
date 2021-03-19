package com.stoneconvo.applicationService.userAccount

import com.stoneconvo.applicationService.userAccount.command.ChangeNameCommand
import com.stoneconvo.applicationService.userAccount.command.CreateCommand
import com.stoneconvo.applicationService.userAccount.command.LoginCommand
import com.stoneconvo.domain.userAccount.UserAccount
import com.stoneconvo.domain.userAccount.UserAccountService
import com.stoneconvo.exception.CustomException
import com.stoneconvo.repository.administrator.AdministratorRepository
import com.stoneconvo.repository.userAccount.UserAccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserAccountApplicationService(
    @Autowired
    private val administratorRepository: AdministratorRepository,
    @Autowired
    private val userAccountRepository: UserAccountRepository,
    @Autowired
    private val userAccountService: UserAccountService,
) {
    @Transactional
    fun create(createCommand: CreateCommand) {
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
    }

    @Transactional
    fun login(loginCommand: LoginCommand) = userAccountService.login(
        name = loginCommand.name,
        password = loginCommand.password
    )

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
