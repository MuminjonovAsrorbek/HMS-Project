package uz.dev.hmsproject.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.entity.Appointment;
import uz.dev.hmsproject.service.template.FileService;
import uz.dev.hmsproject.utils.CommonUtils;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/27/25 18:52
 **/

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {


    @Override
    public Workbook generateExcelReport(List<Appointment> appointments, LocalDate reportDate) {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Daily Appointments");

        // Header va row style
        CellStyle headerStyle = createHeaderCellStyle(workbook);
        CellStyle rowStyle = createRowCellStyle(workbook);
        CellStyle scheduledStyle = createStatusCellStyle(workbook, IndexedColors.LIGHT_BLUE);
        CellStyle completedStyle = createStatusCellStyle(workbook, IndexedColors.LIGHT_GREEN);
        CellStyle canceledStyle = createStatusCellStyle(workbook, IndexedColors.RED1);


        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(reportDate.toString() + " - Sanadagi Appointmentlar");

        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 14);
        titleStyle.setFont(titleFont);
        titleCell.setCellStyle(titleStyle);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

        Row header = sheet.createRow(2);
        String[] columns = {"Bemor F.I.SH.", "Telefon raqami", "Email", "Shifokor", "Mutaxassislik", "Qabul vaqti", "Status"};

        for (int i = 0; i < columns.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowIndex = 3;
        for (Appointment appointment : appointments) {
            Row row = sheet.createRow(rowIndex++);
            createCell(row, 0, appointment.getPatient().getFullName(), rowStyle);
            createCell(row, 1, appointment.getPatient().getPhoneNumber(), rowStyle);

            String email = appointment.getPatient().getEmail();
            if (email != null && !email.isBlank()) {
                createCell(row, 2, email, rowStyle);
            } else {
                createCell(row, 2, "-", rowStyle);
            }

            createCell(row, 3, appointment.getDoctor().getUser().getFullName(), rowStyle);
            createCell(row, 4, appointment.getDoctor().getSpeciality().getName(), rowStyle);
            createCell(row, 5, CommonUtils.formattedDate(appointment.getAppointmentDateTime()), rowStyle);


            String status = appointment.getStatus().name();
            CellStyle statusStyle = switch (appointment.getStatus()) {
                case SCHEDULED -> scheduledStyle;
                case COMPLETED -> completedStyle;
                case CANCELED -> canceledStyle;
                default -> throw new IllegalStateException("Unexpected value: " + appointment.getStatus());
            };

            createCell(row, 6, status, statusStyle);
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        return workbook;

    }

    private CellStyle createStatusCellStyle(Workbook workbook, IndexedColors color) {

        CellStyle style = workbook.createCellStyle();

        style.setFillForegroundColor(color.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }

    private void createCell(Row row, int columnIndex, String value, CellStyle style) {

        Cell cell = row.createCell(columnIndex);

        cell.setCellValue(value);

        cell.setCellStyle(style);

    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {

        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();

        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);

        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    private CellStyle createRowCellStyle(Workbook workbook) {

        CellStyle style = workbook.createCellStyle();

        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }

}
