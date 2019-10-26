package com.adeliosys.keybout.service

import com.adeliosys.keybout.model.Constants.MIN_WORD_LENGTH
import com.adeliosys.keybout.model.Word
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

/**
 * Generate random for a game round.
 */
@Service
class WordGenerator {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Value("classpath:words-en.txt")
    private lateinit var wordsEn: Resource

    @Value("classpath:words-fr.txt")
    private lateinit var wordsFr: Resource

    private var wordsByLang = mutableMapOf<String, MutableList<String>>()

    @PostConstruct
    private fun init() {
        loadWords(wordsEn, "en")
        loadWords(wordsFr, "fr")
    }

    /**
     * Load the words for one language.
     */
    private fun loadWords(resource: Resource, lang: String) {
        logger.debug("Loading '{}' words", lang)

        val words = mutableListOf<String>()

        // Do not use resource.file, it does not work with a Spring Boot fat jar
        resource.inputStream.bufferedReader(Charsets.UTF_8).readLines().forEach {
            if (it.length >= MIN_WORD_LENGTH) {
                words.add(it)
            }
        }

        wordsByLang[lang] = words

        logger.info("Loaded {} '{}' words", words.size, lang)
    }

    /**
     * Generate random words.
     */
    fun generateWords(language: String, count: Int): Map<String, Word> {
        val possibleWords = wordsByLang[language]!!
        val selectedWords = mutableMapOf<String, Word>()

        while (selectedWords.size < count) {
            val selectedWord = possibleWords[(0..possibleWords.size).random()]

            // Check if no other word begins with the same letters
            var found = false
            for (word in selectedWords.values) {
                if (word.label.startsWith(selectedWord)) {
                    found = true
                    break
                }
            }
            if (!found) {
                selectedWords[selectedWord] = Word(selectedWord)
            }
        }

        return selectedWords
    }
}
