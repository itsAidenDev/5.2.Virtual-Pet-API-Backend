package com.pet_api.virtual_pet.mapper;

import com.pet_api.virtual_pet.dto.activities.BugDTO;
import com.pet_api.virtual_pet.model.activities.Bug;
import org.springframework.stereotype.Component;

@Component
public class BugMapper {

    public BugDTO toDto(Bug bug) {
        return BugDTO.builder()
                .bugId(bug.getBugId())
                .bugName(bug.getBugName())
                .bugDescription(bug.getBugDescription())
                .bugRarity(bug.getBugRarity())
                .bugValue(bug.getBugValue())
                .bugHabitat(bug.getBugHabitat())
                .catchDifficulty(bug.getCatchDifficulty())
                .build();
    }

    public Bug toEntity(BugDTO bugDTO) {
        return Bug.builder()
                .bugId(bugDTO.getBugId())
                .bugName(bugDTO.getBugName())
                .bugDescription(bugDTO.getBugDescription())
                .bugRarity(bugDTO.getBugRarity())
                .bugValue(bugDTO.getBugValue())
                .bugHabitat(bugDTO.getBugHabitat())
                .catchDifficulty(bugDTO.getCatchDifficulty())
                .build();
    }
}