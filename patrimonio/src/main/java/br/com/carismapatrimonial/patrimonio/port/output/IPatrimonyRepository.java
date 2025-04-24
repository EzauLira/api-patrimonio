package br.com.carismapatrimonial.patrimonio.port.output;

import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyResponseDto;
import br.com.carismapatrimonial.patrimonio.damain.entities.Patrimony;

import java.util.List;

public interface IPatrimonyRepository {


    void registerProductPatrimony(Patrimony patrimony);

    List<PatrimonyResponseDto> listAllProducts();

    PatrimonyResponseDto searchSpecificProduct(String name);

    List<PatrimonyResponseDto> checkProduct(String name);

   void increaseQuantity(Patrimony patrimony);

    void decreaseQuantity(Patrimony patrimony);

    void updateArea(Patrimony patrimony);

}
