package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.DoctorDTO;
import uz.dev.hmsproject.dto.DoctorFilterDTO;
import uz.dev.hmsproject.dto.response.DoctorResponseDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DoctorService  {

    List<LocalTime> getAvailable20MinuteSlots(Long doctorId, LocalDate date);

     PageableDTO getAllPaginated(Integer page, Integer size);

    List<DoctorResponseDTO> filter(DoctorFilterDTO filterDTO);

    DoctorResponseDTO getById(Long id);

    void create(DoctorDTO doctorDTO);

    void update(Long id, DoctorDTO doctorDTO);

    void delete(Long id);
}
