package br.com.carismapatrimonial.patrimonio.adapter.input.patrimony;

import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyRequestDto;
import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyResponseDto;
import br.com.carismapatrimonial.patrimonio.adapter.input.dto.ProductDto;
import br.com.carismapatrimonial.patrimonio.config.dto.StandardResponseDto;
import br.com.carismapatrimonial.patrimonio.port.input.IPatimony;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/controle")
public class PartimonyController implements IPatrimonyController {
    private final Logger LOGGER = LoggerFactory.getLogger(PartimonyController.class);

    private final IPatimony iPatimonyService;

    public PartimonyController(IPatimony iPatimony) {
        this.iPatimonyService = iPatimony;
    }

    //-------------------------------------------------------------------------------------------------------------------------------
    @Override
    @PostMapping("/registrar")
    public ResponseEntity<StandardResponseDto> registerProduct(@RequestBody PatrimonyRequestDto patrimonyRequestDto) {
        LOGGER.info("Início do método para cadastrar um novo produto - controller");
        iPatimonyService.rigisterProductCommand(patrimonyRequestDto);
        return ResponseEntity.ok(StandardResponseDto.builder().message("Produto registrado com sucesso!").build());
    }
    //-------------------------------------------------------------------------------------------------------------------------------


    //-------------------------------------------------------------------------------------------------------------------------------
    @Override
    @GetMapping("/listar-produtos")
    public List<PatrimonyResponseDto> listAllProducts() {
        LOGGER.info("Início do método para listar todo os produtos - controller.");
        return iPatimonyService.listAllProducts();
    }

    @Override
    @GetMapping("/listar-produtos-removidos")
    public List<PatrimonyResponseDto> listProductRemoved() {
        LOGGER.info("Início do método para listar os produtos removidos - contoller  ");
        return iPatimonyService.listProductRemoved();
    }

    @Override
    @GetMapping("/listar-produtos-area/{area}")
    public List<ProductDto> listAllProductsForArea(@PathVariable String area) {
        LOGGER.info("Início do método para listar todo os produtos pela Área - controller.");
        return iPatimonyService.listAllProductsForArea(area);
    }

    //-------------------------------------------------------------------------------------------------------------------------------


    //-------------------------------------------------------------------------------------------------------------------------------
    @Override
    @GetMapping("/detalhes-produto/{numSerie}")
    public PatrimonyRequestDto productDetails(@PathVariable String numSerie) {
        LOGGER.info("Início do método para buscar detalhes de produtos na lista ativa- controller.");
        return iPatimonyService.productDetails(numSerie);
    }

    @Override
    @GetMapping("/detalhe-produto-removido/{numSerie}")
    public PatrimonyRequestDto productDetailsRemoved(@PathVariable String numSerie) {
        LOGGER.info("Início do método para buscar detalhes de produtos na lista de removidos - controller.");
        return iPatimonyService.productDetailsRemoved(numSerie);
    }

    //-------------------------------------------------------------------------------------------------------------------------------


    //-------------------------------------------------------------------------------------------------------------------------------
    @Override
    @PutMapping("/alterar-produto/{numSerie}")
    public ResponseEntity<StandardResponseDto> updateProduct(@PathVariable String numSerie, @RequestBody Map<String, String> updates) {
        LOGGER.info("Início do método para alterar a area de produtos - controller");
        iPatimonyService.updateProduct(numSerie, updates);
        return ResponseEntity.ok(StandardResponseDto.builder().message("Produto atualizado com sucesso!").build());
    }

    //-------------------------------------------------------------------------------------------------------------------------------


    //-------------------------------------------------------------------------------------------------------------------------------

    @Override
    @PutMapping("/remover-produto/{numSerie}")
    public ResponseEntity<StandardResponseDto> removProduct(@PathVariable String numSerie) {
        LOGGER.info("Início do método para remover um produto - controller");
        iPatimonyService.removeProduct(numSerie);
        return ResponseEntity.ok(StandardResponseDto.builder().message("Produto Removido com sucesso").build());
    }

    @Override
    @PutMapping("/restaurar-produto/{numSerie}")
    public ResponseEntity<StandardResponseDto> restoreRemovProduct(@PathVariable String numSerie) {
        LOGGER.info("Início do método para remover um produto - controller");
        iPatimonyService.restoreRemoveProduct(numSerie);
        return ResponseEntity.ok(StandardResponseDto.builder().message("Produto Restaurado com sucesso").build());

    }

    //-------------------------------------------------------------------------------------------------------------------------------


    //-------------------------------------------------------------------------------------------------------------------------------

    @Override
    @GetMapping("/filtrar-produto")
    public List<PatrimonyRequestDto> filterProduct(@RequestParam(required = false) String numSerie,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) String area,
                                                   @RequestParam(required = false) String inputDate) {
        LOGGER.info("Início do método para filtrar o produto - controller.");
        return iPatimonyService.filterProduct(numSerie, name, area, inputDate);
    }

    @Override
    @GetMapping("/filtrar-produto-removido")
    public List<PatrimonyRequestDto> filterProductRemoved(@RequestParam(required = false) String numSerie,
                                                          @RequestParam(required = false) String name,
                                                          @RequestParam(required = false) String area,
                                                          @RequestParam(required = false) String inputDate) {
        LOGGER.info("Início do método para filtrar o produto dos removidos - controller.");
        return iPatimonyService.filterProductRemoved(numSerie, name, area, inputDate);
    }

    //-------------------------------------------------------------------------------------------------------------------------------
}
