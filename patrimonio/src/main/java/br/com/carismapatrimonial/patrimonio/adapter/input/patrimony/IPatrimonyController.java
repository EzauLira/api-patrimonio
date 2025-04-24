package br.com.carismapatrimonial.patrimonio.adapter.input.patrimony;

import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyRequestDto;
import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyResponseDto;
import br.com.carismapatrimonial.patrimonio.config.dto.StandardResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IPatrimonyController {

    ResponseEntity<StandardResponseDto> registerProductController (PatrimonyRequestDto patrimonyRequestDto);
    List<PatrimonyResponseDto> listAllProducts();

    PatrimonyResponseDto searchSpecificProduct(String name);

    ResponseEntity<StandardResponseDto> increaseQuantity(@RequestBody PatrimonyRequestDto patrimonyRequestDto);

    ResponseEntity<StandardResponseDto> decreaseQuantity(@RequestBody PatrimonyRequestDto patrimonyRequestDto);

    ResponseEntity<StandardResponseDto> updateArea(@RequestBody PatrimonyRequestDto patrimonyRequestDto);
}
