package com.pet_api.virtual_pet.dto;

public record ActionResultDTO(String message, int newEnergy, int newFriendship) {
    public static ActionResultDTOBuilder builder() {
        return new ActionResultDTOBuilder();
    }

    public static class ActionResultDTOBuilder {
        private String message;
        private int newEnergy;
        private int newFriendship;

        public ActionResultDTOBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ActionResultDTOBuilder newEnergy(int newEnergy) {
            this.newEnergy = newEnergy;
            return this;
        }

        public ActionResultDTOBuilder newFriendship(int newFriendship) {
            this.newFriendship = newFriendship;
            return this;
        }

        public ActionResultDTO build() {
            return new ActionResultDTO(message, newEnergy, newFriendship);
        }
    }
}
