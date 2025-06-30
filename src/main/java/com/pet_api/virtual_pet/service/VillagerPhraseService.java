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
                    "Do you think fish have dreams too?",
                    "Isn't the weather lovely today?",
                    "I was just making some tea, would you like some?",
                    "I love our little chats!",
                    "I found the cutest sweater at Able Sisters today!",
                    "Sometimes I just like to sit and watch the clouds go by.",
                    "Have you read any good books lately?",
                    "I baked some cookies earlier, want one?",
                    "It's nice to have a moment of peace, isn't it?",
                    "I was just thinking about starting a new hobby.",
                    "The flowers in my garden are blooming so nicely!",
                    "Do you need someone to talk to? I'm always here to listen.",
                    "I love how cozy your home is!",
                    "Sometimes I hum to myself while I'm cooking.",
                    "I found a new recipe I'd love to try!",
                    "It's the simple things in life that bring the most joy, don't you think?"),
            "peppy", List.of(
                    "Today is going to be the best day in history!",
                    "Let's dance even if there's no music!",
                    "Guess what! I don't have anything to tell you, but I wanted to talk to you!",
                    "OMG! OMG! Guess what just happened!?",
                    "You're the best! Like, the actual best!",
                    "I'm so excited I could do a backflip!",
                    "SQUEEEE! I just had the BEST idea ever!",
                    "Let's be BFFs forever and ever and ever!",
                    "I just can't stop smiling today!",
                    "Eeeek! Your outfit is SO CUTE!",
                    "I'm gonna be a pop star someday, just you wait!",
                    "Let's have a party! Right now!",
                    "I'm just SO HAPPY to see you!",
                    "Guess what? Guess what? Guess what?",
                    "I could literally run around the island 100 times I'm so excited!",
                    "You're like, my favorite person ever!",
                    "I just got the most AMAZING idea!",
                    "Let's make today the BEST DAY EVER!"
            ),
            "lazy", List.of(
                    "Ugh, I'm so tired. Can we just take a nap?",
                    "I was thinking of cleaning the house, but then I thought, why bother?",
                    "Do you want to watch TV with me? I've got a great show to recommend.",
                    "I was gonna do something today, but then I took a nap...",
                    "Do you think pizza is a breakfast food? Asking for a friend...",
                    "I had the weirdest dream about a giant donut...",
                    "Is it snack time yet?",
                    "I love lying in the grass and watching the clouds... zzz...",
                    "I was gonna clean my room, but then I got tired...",
                    "Do you ever just... forget what you were saying?",
                    "I think I left my brain back in bed today...",
                    "I could really go for a snack right about now...",
                    "I love when food turns into more food, like pizza becoming cold pizza!",
                    "I tried to think of something clever to say, but then I got distracted...",
                    "Is it just me, or is napping the best hobby?",
                    "I was going to be productive today, but then I realized I'm allergic to work!",
                    "I like to take life one nap at a time...",
                    "I'm not lazy, I'm just on energy-saving mode!"
            ),
            "snooty", List.of(
                    "I'm not sure why you're here, but I suppose it's nice to see you.",
                    "I've been reading the most fascinating book on etiquette. Have you read it?",
                    "I do hope you're not planning on staying too long. I have more important things to attend to.",
                    "Darling, that's so... interesting.",
                    "I don't mean to be rude, but have you seen my latest designer bag?",
                    "I simply must tell you about my trip to the city...",
                    "I don't always give out compliments, but when I do... well, I usually don't.",
                    "That's... one way to dress, I suppose.",
                    "I was just thinking how much better everything would be if they asked my opinion first.",
                    "I don't do 'casual'... unless it's designer casual, of course.",
                    "I'd love to chat, but I'm expecting a very important call... from someone more important.",
                    "I don't gossip... much. But did you hear about...?",
                    "I'm not saying I'm better than everyone else... but I am, aren't I?",
                    "I would never say anything negative... out loud.",
                    "I'm not being snobby, I just have high standards.",
                    "I'd love to stay and chat, but I have a spa appointment with my reflection.",
                    "I don't always drink tea, but when I do, it's imported.",
                    "I'm not high maintenance, everyone else is just low maintenance."
            ),
            "jock", List.of(
                    "Dude, I just got back from the most epic workout! You should totally join me next time!",
                    "I'm so pumped for the big game this weekend! Are you coming?",
                    "Bro, have you seen my protein shake? I think I left it in the fridge.",
                    "Dude! Let's get SWOLE!",
                    "I did 100 pushups before breakfast! What about you?",
                    "Gotta stay in peak physical condition, bro!",
                    "No pain, no gain, am I right?",
                    "I could bench press a whole tree! Well, maybe a small one...",
                    "Gotta keep those gains coming!",
                    "I'm not tired! I could run around this island 50 more times!",
                    "Protein shakes are my best friend!",
                    "I don't always work out, but when I do, I work out ALL THE TIME!",
                    "Bro, you should join me for some reps!",
                    "I was going to count how many sit-ups I did, but I lost count after 500!",
                    "The only thing I like more than working out is talking about working out!",
                    "I don't need sleep! I need GAINS!",
                    "Pain is just weakness leaving the body, bro!",
                    "I don't always eat healthy, but when I do, I eat a whole chicken!"
            ),
            "cranky", List.of(
                    "Ugh, what do you want now?",
                    "Back in my day, we didn't have all these fancy items!",
                    "Hmph. I was napping before you showed up.",
                    "You again? Can't you see I'm busy being grumpy?",
                    "Kids these days... no respect for their elders!",
                    "*grumbles* The weather was better yesterday.",
                    "I don't always complain, but when I do, I have good reason to!",
                    "Bah! These newfangled gadgets are too complicated.",
                    "I'd rather be napping than talking to you, no offense.",
                    "Harrumph! The youth of today...",
                    "In my village, we did things differently!",
                    "You call that a catchphrase? Back in my day...",
                    "*sigh* Must you be so cheerful all the time?",
                    "I'm not mad, just... perpetually disappointed.",
                    "The state of this island... *shakes head*",
                    "I remember when this was all just trees and weeds...",
                    "Kids today with their smartphones and their... whatever it is they do.",
                    "*grumpy mumbling*",
                    "I was having a perfectly good bad mood before you came along!",
                    "Hmph. I suppose you want something from me?"
            ),
            "smug", List.of(
                    "Darling, you're looking... interesting today.",
                    "Oh, this old thing? It's just something I threw on.",
                    "I don't mean to brag, but I am quite popular, you know.",
                    "Ah, another day of being fabulous!",
                    "You should feel honored I'm gracing you with my presence.",
                    "I was just thinking about how much cooler I am than everyone else.",
                    "Oh, you noticed my new look? It's called 'effortless style'.",
                    "I'd love to chat, but I have a hot date with a mirror later.",
                    "I don't always visit the island, but when I do, I make it better.",
                    "I'm not saying I'm perfect, but have you seen anyone else who is?",
                    "I'd give you fashion advice, but I don't want to make you feel bad.",
                    "I was going to be humble today, but I'm just too good at it.",
                    "I'd tell you my secret, but then I'd have to make you sign an NDA.",
                    "Oh, this? Just my natural charisma shining through.",
                    "I'd be more modest if I wasn't so great at everything.",
                    "I don't keep track of my admirers, but I'm sure the list is long.",
                    "I'd say I'm one of a kind, but that would be an understatement.",
                    "I was going to be humble today, but I'm just too good at it.",
                    "I don't always wake up looking this good, but 99% of the time I do.",
                    "I'd say I'm surprised by my own greatness, but I'm really not."
            )
    );

    public static String getRandomPhrase(String personality) {
        var list = phrases.getOrDefault(personality.toLowerCase(), List.of("Hello!"));
        return list.get(new Random().nextInt(list.size()));
    }
}