package com.pet_api.virtual_pet.config;

import com.pet_api.virtual_pet.model.User;
import com.pet_api.virtual_pet.model.activities.Bug;
import com.pet_api.virtual_pet.model.activities.Fish;
import com.pet_api.virtual_pet.repository.BugRepository;
import com.pet_api.virtual_pet.repository.FishRepository;
import com.pet_api.virtual_pet.repository.UserRepository;
import com.pet_api.virtual_pet.utils.Habitat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final BugRepository bugRepository;
    private final FishRepository fishRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username:admin}")
    private String adminUsername;

    @Value("${admin.password:admin123}")
    private String adminPassword;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            initAdminUser();
            initBugs();
            initFish();
        };
    }

    private void initAdminUser() {
        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            String hashedPassword = passwordEncoder.encode(adminPassword);
            User admin = User.builder()
                    .username(adminUsername)
                    .password(hashedPassword)
                    .role("ROLE_ADMIN")
                    .build();
            userRepository.save(admin);
            log.info("Admin user created with username: {}", adminUsername);
        } else {
            log.info("Admin user already exists");
        }
    }

    private void initBugs() {
        if (bugRepository.count() == 0) {
            List<Bug> bugs = Arrays.asList(
                    Bug.builder().bugName("Common Butterfly").bugDescription("A beautiful common butterfly").bugRarity("common").bugValue(160).bugHabitat(Habitat.GRASSLAND).catchDifficulty(0.2).build(),
                    Bug.builder().bugName("Honeybee").bugDescription("A busy little bee").bugRarity("common").bugValue(200).bugHabitat(Habitat.GRASSLAND).catchDifficulty(0.3).build(),
                    Bug.builder().bugName("Ant").bugDescription("A hardworking ant").bugRarity("common").bugValue(80).bugHabitat(Habitat.FOREST).catchDifficulty(0.1).build(),
                    Bug.builder().bugName("Tiger Butterfly").bugDescription("A striking orange and black butterfly").bugRarity("uncommon").bugValue(240).bugHabitat(Habitat.FOREST).catchDifficulty(0.4).build(),
                    Bug.builder().bugName("Mantis").bugDescription("A patient predator").bugRarity("uncommon").bugValue(430).bugHabitat(Habitat.FOREST).catchDifficulty(0.5).build(),
                    Bug.builder().bugName("Spider").bugDescription("An eight-legged hunter").bugRarity("rare").bugValue(600).bugHabitat(Habitat.FOREST).catchDifficulty(0.6).build(),
                    Bug.builder().bugName("Scorpion").bugDescription("A dangerous desert dweller").bugRarity("rare").bugValue(8000).bugHabitat(Habitat.DESERT).catchDifficulty(0.8).build(),
                    Bug.builder().bugName("Golden Stag").bugDescription("A magnificent golden beetle").bugRarity("legendary").bugValue(12000).bugHabitat(Habitat.FOREST).catchDifficulty(0.9).build()
            );
            bugRepository.saveAll(bugs);
            log.info("Initialized {} bugs", bugs.size());
        }
    }

    private void initFish() {
        if (fishRepository.count() == 0) {
            List<Fish> fish = Arrays.asList(
                    Fish.builder().fishName("Sea Bass").fishDescription("A very common fish").fishRarity("common").fishValue(400).habitat(Habitat.OCEAN).catchDifficulty(0.2).build(),
                    Fish.builder().fishName("Horse Mackerel").fishDescription("A small, common fish").fishRarity("common").fishValue(150).habitat(Habitat.OCEAN).catchDifficulty(0.1).build(),
                    Fish.builder().fishName("Goldfish").fishDescription("A popular pet fish").fishRarity("common").fishValue(1300).habitat(Habitat.POND).catchDifficulty(0.3).build(),
                    Fish.builder().fishName("Crucian Carp").fishDescription("A freshwater fish").fishRarity("common").fishValue(160).habitat(Habitat.RIVER).catchDifficulty(0.2).build(),
                    Fish.builder().fishName("Red Snapper").fishDescription("A prized catch").fishRarity("uncommon").fishValue(3000).habitat(Habitat.OCEAN).catchDifficulty(0.4).build(),
                    Fish.builder().fishName("Salmon").fishDescription("A strong swimming fish").fishRarity("uncommon").fishValue(700).habitat(Habitat.RIVER).catchDifficulty(0.5).build(),
                    Fish.builder().fishName("Tuna").fishDescription("A large ocean fish").fishRarity("rare").fishValue(7000).habitat(Habitat.OCEAN).catchDifficulty(0.7).build(),
                    Fish.builder().fishName("Blue Marlin").fishDescription("A legendary sport fish").fishRarity("legendary").fishValue(10000).habitat(Habitat.OCEAN).catchDifficulty(0.9).build()
            );
            fishRepository.saveAll(fish);
            log.info("Initialized {} fish", fish.size());
        }
    }
}


