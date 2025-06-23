package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.RoomDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;


public interface RoomService  {

    PageableDTO getAll(Integer page, Integer size);

    RoomDTO getById(Long id);

    void create(RoomDTO roomDTO);

    void update(Long id, RoomDTO roomDTO);

    void delete(Long id);


}
