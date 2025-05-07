package br.com.carismapatrimonial.patrimonio.adapter.input.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatrimonyResponseDto {

    private String numSerie;
    private String name;
    private String area;
    private String inputDate;

}
