package br.com.carismapatrimonial.patrimonio.adapter.input.patrimony;

import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyRequestDto;
import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyResponseDto;
import br.com.carismapatrimonial.patrimonio.adapter.input.dto.ProductDto;
import br.com.carismapatrimonial.patrimonio.config.dto.StandardResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface IPatrimonyController {

    ResponseEntity<StandardResponseDto> registerProduct (PatrimonyRequestDto patrimonyRequestDto);
    List<PatrimonyResponseDto> listAllProducts();

    PatrimonyRequestDto productDetails(String numSerie);

    ResponseEntity<StandardResponseDto> updateProduct(@PathVariable String numSerie,@RequestBody Map<String, String> updates);

    List <PatrimonyResponseDto> listProductRemoved();
    ResponseEntity<StandardResponseDto> removProduct(@PathVariable String numSerie);

    List<ProductDto> listAllProductsForArea(String area);
    ResponseEntity<StandardResponseDto> restoreRemovProduct(@PathVariable String numSerie);

    PatrimonyRequestDto productDetailsRemoved(@PathVariable String numSerie);

    List<PatrimonyRequestDto> filterProduct(@RequestParam(required = false) String numSerie,
                                            @RequestParam(required = false) String name,
                                            @RequestParam(required = false) String area,
                                            @RequestParam(required = false) String inputDate);

    List<PatrimonyRequestDto> filterProductRemoved(@RequestParam(required = false) String numSerie,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) String area,
                                                   @RequestParam(required = false) String inputDate);
}
