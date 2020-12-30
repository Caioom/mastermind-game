package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    val rightPosition: Int = calculateRightPositions(secret = secret, guess = guess);
    val wrongPosition: Int = calculateWrongPositions(secret = secret, guess = guess);

    return Evaluation(rightPosition = rightPosition, wrongPosition = wrongPosition);
}

fun calculateRightPositions(secret: String, guess: String): Int {
    var rightPosition: Int = 0;
    secret.forEachIndexed { index, secretLetter ->
        val guessLetter: Char = guess[index];
        if(guessLetter == secretLetter) rightPosition++;
    }

    return rightPosition;
}

fun calculateWrongPositions(secret: String, guess: String): Int {
    var wrongWordSecret: String = "";
    var wrongWordGuess: String = "";
    var wrongPositions: Int = 0;
    val evaluatedLetters = mutableListOf<Char>();

    secret.forEachIndexed { index, secretChar ->
       if(secretChar != guess[index]) {
           wrongWordSecret += secretChar;
           wrongWordGuess += guess[index];
       }
    }

    if(wrongWordSecret.isNotEmpty()) {
        guess.forEach { guessChar ->
            val letter: Char = guessChar;
            if(!evaluatedLetters.contains(letter)) {
                val timesOfLetterInWrongSecret = calculateHowManyLetters(wrongWordSecret, letter);
                val timesOfLetterInWrongGuess = calculateHowManyLetters(wrongWordGuess, letter);

                wrongPositions += if(timesOfLetterInWrongGuess == timesOfLetterInWrongSecret || timesOfLetterInWrongSecret > timesOfLetterInWrongGuess) {
                    timesOfLetterInWrongGuess;
                } else {
                    timesOfLetterInWrongSecret;
                }

                evaluatedLetters.add(letter);
            }
        }
    }

    return wrongPositions;
}

fun calculateHowManyLetters(word: String, letter: Char): Int {
    var contains: Int = 0;
    word.forEach { wordChar -> if(wordChar == letter) contains++; }
    return contains;
}
