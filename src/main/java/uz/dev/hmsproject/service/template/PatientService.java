package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.PatientDTO;
import uz.dev.hmsproject.dto.PatientSearchDTO;
import uz.dev.hmsproject.dto.UserDTO;

import java.util.List;

/**
 * Created by: suhrob
 */
public interface PatientService extends BaseService<PatientDTO, Long>{

    List<PatientDTO> search(PatientSearchDTO searchDTO);

}
