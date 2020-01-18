package com.adeliosys.keybout.model

import java.util.concurrent.atomic.AtomicLong

/**
 * A declared game.
 */
class GameDescriptor(
        val creator: String,
        mode: String,
        val rounds: Int,
        language: String,
        val wordsCount: Int,
        wordsLength: String,
        wordsEffect: String) {

    companion object {
        val counter = AtomicLong()
    }

    val id: Long = counter.incrementAndGet()

    val players = mutableListOf<String>()

    val mode: GameMode = GameMode.getByCode(mode)

    val language: Language = Language.getByCode(language)

    val wordsLength: WordLength = WordLength.getByCode(wordsLength)

    val wordsEffect: WordEffect = WordEffect.getByCode(wordsEffect)
}
