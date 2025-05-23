package br.com.carismapatrimonial.patrimonio.damain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patrimony {

    private String numSerie;
    private String name;
    private String area;
    private String inputDate;
}
