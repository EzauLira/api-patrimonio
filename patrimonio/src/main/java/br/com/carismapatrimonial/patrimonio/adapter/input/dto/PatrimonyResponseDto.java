package br.com.carismapatrimonial.patrimonio.adapter.input.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatrimonyResponseDto {

    private String name;
    private int quantity;
    private String area;

}
