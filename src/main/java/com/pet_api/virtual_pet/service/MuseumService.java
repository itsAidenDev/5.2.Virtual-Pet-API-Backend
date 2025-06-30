package com.pet_api.virtual_pet.service;

import com.pet_api.virtual_pet.dto.activities.BugDTO;
import com.pet_api.virtual_pet.dto.activities.FishDTO;
import com.pet_api.virtual_pet.exception.custom.ResourceNotFoundException;
import com.pet_api.virtual_pet.mapper.BugMapper;
import com.pet_api.virtual_pet.mapper.FishMapper;
import com.pet_api.virtual_pet.model.Villager;
import com.pet_api.virtual_pet.model.activities.Bug;
import com.pet_api.virtual_pet.model.activities.Fish;
import com.pet_api.virtual_pet.model.museum.MuseumRecord;
import com.pet_api.virtual_pet.repository.VillagerRepository;
import com.pet_api.virtual_pet.repository.activities.BugRepository;
import com.pet_api.virtual_pet.repository.activities.FishRepository;
import com.pet_api.virtual_pet.repository.museum.MuseumRecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MuseumService {

    private final BugRepository bugRepository;
    private final FishRepository fishRepository;
    private final MuseumRecordRepository museumRecordRepository;
    private final VillagerRepository villagerRepository;
    private final BugMapper bugMapper;
    private final FishMapper fishMapper;

    @Transactional
    public void registerNewDiscovery(Long villagerId, MuseumRecord.RecordType recordType, Long entityId, String location) {
        // Verify if record already exists
        boolean exists = museumRecordRepository.existsByVillagerIdAndRecordTypeAndEntityId(villagerId, recordType, entityId);

        if (!exists) {
            // Create new record
            Villager villager = villagerRepository.findById(villagerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Villager not found"));

            MuseumRecord record = MuseumRecord.builder()
                    .villagerId(villagerId)
                    .recordType(recordType)
                    .entityId(entityId)
                    .firstCaughtAt(LocalDateTime.now())
                    .location(location)
                    .build();

            museumRecordRepository.save(record);
        }
    }

    public List<BugDTO> getMuseumBugs(Long villagerId) {
        // Get all bug records for this villager
        List<MuseumRecord> records = museumRecordRepository.findByVillagerIdAndRecordType(villagerId, MuseumRecord.RecordType.BUG);

        return records.stream()
                .map(record -> {
                    // Get bug details from repository
                    Bug bug = bugRepository.findById(record.getEntityId())
                            .orElseThrow(() -> new ResourceNotFoundException("Bug not found"));

                    BugDTO dto = bugMapper.toDto(bug);
                    dto.setCaughtAt(record.getFirstCaughtAt());
                    dto.setLocation(record.getLocation());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<FishDTO> getMuseumFish(Long villagerId) {
        // Get all fish records for this villager
        List<MuseumRecord> records = museumRecordRepository.findByVillagerIdAndRecordType(villagerId, MuseumRecord.RecordType.FISH);

        return records.stream()
                .map(record -> {
                    // Get fish details from repository
                    Fish fish = fishRepository.findById(record.getEntityId())
                            .orElseThrow(() -> new ResourceNotFoundException("Fish not found"));

                    FishDTO dto = fishMapper.toDto(fish);
                    dto.setCaughtAt(record.getFirstCaughtAt());
                    dto.setLocation(record.getLocation());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
