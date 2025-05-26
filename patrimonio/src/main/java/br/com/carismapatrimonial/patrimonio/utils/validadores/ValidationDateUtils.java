package br.com.carismapatrimonial.patrimonio.utils.validadores;

import br.com.carismapatrimonial.patrimonio.damain.exception.CustomException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ValidationDateUtils {

    public String converterDate(String inputDate) {
        String[] possiblePatterns = {"yyyy-MM-dd", "dd/MM/yyyy", "dd-MM-yyyy"};

        for (String pattern : possiblePatterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                LocalDate date = LocalDate.parse(inputDate, formatter);
                return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (CustomException e) {
               throw new CustomException("Formato de data inválido.");
            }
        }
        throw new IllegalArgumentException("Formato de data inválido: " + inputDate);
    }
}
