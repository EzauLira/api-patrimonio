package br.com.carismapatrimonial.patrimonio.port.input;

import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyRequestDto;
import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyResponseDto;

import java.util.List;

public interface IPatimony {

    void rigisterProductCommand (PatrimonyRequestDto patrimonyRequestDto);
    List<PatrimonyResponseDto> listAllProducts();

    PatrimonyResponseDto searchSpecificProduct(String name);

    void increaseQuantity(PatrimonyRequestDto patrimonyRequestDto);

    void decreaseQuantity(PatrimonyRequestDto patrimonyRequestDto);

    void updateArea(PatrimonyRequestDto patrimonyRequestDto);
}
