package br.com.carismapatrimonial.patrimonio.port.input;

import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyRequestDto;
import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyResponseDto;
import br.com.carismapatrimonial.patrimonio.adapter.input.dto.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IPatimony {

    void rigisterProductCommand (String name, String area, String inputDate, MultipartFile file);
    List<PatrimonyResponseDto> listAllProducts();

    PatrimonyRequestDto productDetails(String numSerie);

    void updateProduct(String numSerie, Map<String, String> updates);

    List<PatrimonyResponseDto> listProductRemoved();

    void removeProduct(String numSerie);

    List<ProductDto> listAllProductsForArea(String area);

    void restoreRemoveProduct(String numSerie);

    PatrimonyRequestDto productDetailsRemoved(String numSerie);
    List<PatrimonyRequestDto> filterProduct(String numSerie, String name, String area, String inputDate);
    List<PatrimonyRequestDto> filterProductRemoved(String numSerie, String name, String area, String inputDate);

}
