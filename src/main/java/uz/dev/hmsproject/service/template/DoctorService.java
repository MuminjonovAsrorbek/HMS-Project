package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.DoctorDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;

public interface DoctorService extends BaseService<DoctorDTO, Long> {
     PageableDTO getAllPaginated(Integer page, Integer size);
}
