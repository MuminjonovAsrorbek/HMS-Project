package uz.dev.hmsproject.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.dev.hmsproject.dto.RoomDTO;
import uz.dev.hmsproject.entity.Room;
import uz.dev.hmsproject.mapper.template.BaseMapper;

@Component
public class RoomMapper implements BaseMapper<Room, RoomDTO> {

    @Override
    public Room toEntity(RoomDTO roomDTO) {
        return new Room(
                roomDTO.getNumber()
        );
    }

    @Override
    public RoomDTO toDTO(Room room) {
        return new RoomDTO(
                room.getId(),
                room.getNumber()
        );
    }
}
