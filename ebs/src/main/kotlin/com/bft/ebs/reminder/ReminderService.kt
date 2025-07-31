package com.bft.ebs.reminder

import com.bft.ebs.repositories.impl.JournalRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class ReminderService(private val mailSender: JavaMailSender,
                      private val journalRepository: JournalRepository): IReminderService {

    @Value("\${spring.mail.username}")
    private val fromMail: String? = null
    override fun notifyDebtors() {
        val debtors = journalRepository.findAllDeadlineDebtors()

        debtors.forEach {
            val message = SimpleMailMessage()
            message.from = fromMail
            message.setTo(it.reader.email)
            message.subject = "Срок возврата книги в ebs истёк"
            message.text = "Привет, ${it.reader.name}.\nСрок возврата книги \"${it.book.book.name}\" истёк.\nПросим вернуть её в EBS."

            mailSender.send(message)
        }
    }
}