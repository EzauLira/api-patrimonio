package br.com.carismapatrimonial.patrimonio.adapter.input.patrimony;

import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyRequestDto;
import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyResponseDto;
import br.com.carismapatrimonial.patrimonio.config.dto.StandardResponseDto;
import br.com.carismapatrimonial.patrimonio.port.input.IPatimony;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/controle")
public class PartimonyController implements IPatrimonyController {
    private final Logger LOGGER = LoggerFactory.getLogger(PartimonyController.class);

    @Autowired
    IPatimony iPatimonyService;

    @Override
    @PostMapping("/registrar")
    public ResponseEntity<StandardResponseDto> registerProductController(@RequestBody PatrimonyRequestDto patrimonyRequestDto) {
        LOGGER.info("Início do método para cadastrar um novo cliente - controller");
        long startTime = System.currentTimeMillis();

        iPatimonyService.rigisterProductCommand(patrimonyRequestDto);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Tempo decorrido: {} milissegundos", elapsedTime);

        return ResponseEntity.ok(StandardResponseDto.builder().message("Produto registrado com sucesso!").build());
    }

    @Override
    @GetMapping("/listar-produtos")
    public List <PatrimonyResponseDto> listAllProducts(){
        LOGGER.info("Início do método para listar todo os produtos - controller.");
        return iPatimonyService.listAllProducts();
    }

    @Override
    @GetMapping("/buscar-produto/{name}")
    public PatrimonyResponseDto searchSpecificProduct(@PathVariable String name){
        LOGGER.info("Início do método para listar um produtos - controller.");
        return iPatimonyService.searchSpecificProduct(name);
    }

    @Override
    @PutMapping("/aumentar-quantidade")
    public ResponseEntity<StandardResponseDto> increaseQuantity(@RequestBody PatrimonyRequestDto patrimonyRequestDto){
        LOGGER.info("Início do método para aumentar a quantidade de produtos");

        iPatimonyService.increaseQuantity(patrimonyRequestDto);

        return ResponseEntity.ok(StandardResponseDto.builder().message("Quantidade alterada com sucesso!").build());
    }

    @Override
    @PutMapping("/diminuir-quantidade")
    public ResponseEntity<StandardResponseDto> decreaseQuantity(@RequestBody PatrimonyRequestDto patrimonyRequestDto){
        LOGGER.info("Início do método para aumentar a quantidade de produtos");

        iPatimonyService.decreaseQuantity(patrimonyRequestDto);

        return ResponseEntity.ok(StandardResponseDto.builder().message("Quantidade alterada com sucesso!").build());
    }

    @Override
    @PutMapping("/alterar-area")
    public ResponseEntity<StandardResponseDto> updateArea(@RequestBody PatrimonyRequestDto patrimonyRequestDto){
        LOGGER.info("Início do método para aumentar a quantidade de produtos");

        iPatimonyService.updateArea(patrimonyRequestDto);

        return ResponseEntity.ok(StandardResponseDto.builder().message("Area alterada com sucesso!").build());
    }

}
