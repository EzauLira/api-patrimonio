package br.com.carismapatrimonial.patrimonio.utils.validadores;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ValidationDateUtils {
    public String conveterDate(String inputDate){

        DateTimeFormatter originalformate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter newFormater = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate date = LocalDate.parse(inputDate, originalformate);

        return date.format(newFormater);
    }
}
