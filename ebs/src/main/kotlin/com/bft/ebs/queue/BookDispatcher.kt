package com.bft.ebs.queue

import org.springframework.beans.factory.annotation.Value
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Service

@Service
class BookDispatcher(private val jmsTemplate: JmsTemplate) {
    @Value("\${jms.queue}")
    private val queueName: String? = null

    fun sendBook(bookIsbn: String, readerId: String) {
        if (queueName == null) throw IllegalStateException("jms.queue is null in application properties")
        jmsTemplate.convertAndSend(queueName, """
            <readerBooks>
                <book>
                    <ISBN>$bookIsbn</ISBN>
                </book>
                <reader>
                    <id>$readerId</id>
                </reader>
            </readerBooks>
        """.trimIndent())
    }
}