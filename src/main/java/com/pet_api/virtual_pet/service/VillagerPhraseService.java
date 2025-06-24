package com.pet_api.virtual_pet.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class VillagerPhraseService {

    private static final Map<String, List<String>> phrases = Map.of(
            "normal", List.of(
                    "I'm glad to see you again!",
                    "Today I swept the house three times, just in case.",
                    "Do you think fish have dreams too?" ),
            "peppy", List.of(
                    "Today is going to be the best day in history!",
                    "Let's dance even if there's no music!",
                    "Guess what! I don't have anything to tell you, but I wanted to talk to you!"
            ),
            "lazy", List.of(
                    "Ugh, I'm so tired. Can we just take a nap?",
                    "I was thinking of cleaning the house, but then I thought, why bother?",
                    "Do you want to watch TV with me? I've got a great show to recommend."
            ),
            "snooty", List.of(
                    "I'm not sure why you're here, but I suppose it's nice to see you.",
                    "I've been reading the most fascinating book on etiquette. Have you read it?",
                    "I do hope you're not planning on staying too long. I have more important things to attend to."
            ),
            "jock", List.of(
                    "Dude, I just got back from the most epic workout! You should totally join me next time!",
                    "I'm so pumped for the big game this weekend! Are you coming?",
                    "Bro, have you seen my protein shake? I think I left it in the fridge."
            )
    );

    public static String getRandomPhrase(String personality) {
        var list = phrases.getOrDefault(personality.toLowerCase(), List.of("Hello!"));
        return list.get(new Random().nextInt(list.size()));
    }
}