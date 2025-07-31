package com.bft.ebs.queue

import com.bft.ebs.services.impl.JournalService
import jakarta.jms.Message
import jakarta.jms.TextMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

@Component
class BookReader(private val journalService: JournalService) {

    val log: Logger = LoggerFactory.getLogger(BookReader::class.java)
    @JmsListener(destination = "\${jms.queue}")
    fun readBook(message: Message) {
        if (message is TextMessage) {
            log.info("Received book XML:\n{}", message.text)
            try {
                val documentBuilderFactory = DocumentBuilderFactory.newInstance()
                val docBuilder = documentBuilderFactory.newDocumentBuilder()
                val document = docBuilder.parse(InputSource(StringReader(message.text)))

                val rootElement: Element = document.documentElement

                val bookElement: NodeList = rootElement.getElementsByTagName("book")
                val readerElement: NodeList = rootElement.getElementsByTagName("reader")
                if (bookElement.length == 1 && readerElement.length == 1) {
                    val book = (bookElement.item(0) as Element).getElementsByTagName("ISBN").item(0).textContent
                    val reader = (readerElement.item(0) as Element).getElementsByTagName("id").item(0).textContent

                    if (journalService.returnBookByIsbnAndReaderId(book, reader.toLong()))
                        log.info("Book returned successfully.")
                    else
                        log.warn("Book already returned.")
                } else
                    log.error("Invalid book XML format, expected 'bookIsbn' and 'readerId' elements.")
            } catch (e: Exception) {
                log.error("Error parsing book XML:\n{}", message.text)
            }
        }
        else
            log.error("Received non-text message, ignoring.")
    }
}