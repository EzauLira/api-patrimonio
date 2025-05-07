package br.com.carismapatrimonial.patrimonio.port.output;

import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyRequestDto;
import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyResponseDto;
import br.com.carismapatrimonial.patrimonio.adapter.input.dto.ProductDto;
import br.com.carismapatrimonial.patrimonio.damain.entities.Patrimony;

import java.util.List;
import java.util.Map;

public interface IPatrimonyRepository {


    //------------------------------------------------------------------------------------------------------------------
    void registerProductPatrimony(Patrimony patrimony);

    //------------------------------------------------------------------------------------------------------------------

    /*
     */

    //------------------------------------------------------------------------------------------------------------------
    List<PatrimonyResponseDto> listAllProducts();
    List<ProductDto> listAllProductsForArea(String area);
    List<PatrimonyResponseDto> listProductRemoved();

    //------------------------------------------------------------------------------------------------------------------

    /*
     */

    //------------------------------------------------------------------------------------------------------------------
    PatrimonyRequestDto productDetails(String numSerie);
    PatrimonyRequestDto productDetailsRemoved(String numSerie);

    //------------------------------------------------------------------------------------------------------------------

    /*
    */

    //------------------------------------------------------------------------------------------------------------------
    List<PatrimonyRequestDto> checkProduct(String numSerie);
    List<PatrimonyRequestDto> checkProductRemoved(String numSerie);

    //------------------------------------------------------------------------------------------------------------------

    /*
     */

    //------------------------------------------------------------------------------------------------------------------
    void updateProduct(String numSerie, Map<String, String> updates);

    //------------------------------------------------------------------------------------------------------------------

    /*
     */

    //------------------------------------------------------------------------------------------------------------------
    void removeProduct(String numSerie);
    void restoreRemoveProduct(String numSerie);

    //------------------------------------------------------------------------------------------------------------------

    /*
     */

    //------------------------------------------------------------------------------------------------------------------
    List<PatrimonyRequestDto> filterProduct(String numSerie, String name, String area, String inputDate);
    List<PatrimonyRequestDto> filterProductRemoved(String numSerie, String name, String area, String inputDate);

    //------------------------------------------------------------------------------------------------------------------

}
