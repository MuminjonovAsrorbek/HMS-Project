package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.DoctorDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DoctorService extends BaseService<DoctorDTO, Long> {

    List<LocalTime> getAvailable20MinuteSlots(Long doctorId, LocalDate date);

     PageableDTO getAllPaginated(Integer page, Integer size);
}
