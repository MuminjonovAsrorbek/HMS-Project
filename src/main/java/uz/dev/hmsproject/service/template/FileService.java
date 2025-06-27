package uz.dev.hmsproject.service.template;

import org.apache.poi.ss.usermodel.Workbook;
import uz.dev.hmsproject.entity.Appointment;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/27/25 18:52
 **/

public interface FileService {

    Workbook generateExcelReport(List<Appointment> appointments , LocalDate date);

}
