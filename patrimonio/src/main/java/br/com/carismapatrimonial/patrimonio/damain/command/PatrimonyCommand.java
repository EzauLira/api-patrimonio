package br.com.carismapatrimonial.patrimonio.damain.command;

import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyRequestDto;
import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyResponseDto;
import br.com.carismapatrimonial.patrimonio.adapter.input.dto.ProductDto;
import br.com.carismapatrimonial.patrimonio.config.ImageDefaultLoader;
import br.com.carismapatrimonial.patrimonio.damain.entities.Patrimony;
import br.com.carismapatrimonial.patrimonio.damain.exception.CustomException;
import br.com.carismapatrimonial.patrimonio.port.input.IPatimony;
import br.com.carismapatrimonial.patrimonio.port.output.IPatrimonyRepository;
import br.com.carismapatrimonial.patrimonio.utils.validadores.ValidationDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class PatrimonyCommand implements IPatimony {

    private final Logger LOGGER = LoggerFactory.getLogger(PatrimonyCommand.class);

    private final IPatrimonyRepository iPatrimonyRepository;

    public PatrimonyCommand(IPatrimonyRepository iPatrimonyRepository){
        this.iPatrimonyRepository = iPatrimonyRepository;
    }

    //-------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void rigisterProductCommand(String name, String area, String inputDate, MultipartFile file) {
        LOGGER.info("Início do método para registrar o produto - Service.");

        byte[] imageBytes;

        try {
            if (file != null && !file.isEmpty()) {
                // Foto recebida → converte para byte[]
                imageBytes = file.getBytes();
            } else {
                // Usa foto padrão embutida
                imageBytes = ImageDefaultLoader.getDefaultProductImageBytes();
            }

            // Monta o objeto Patrimony
            Patrimony patrimony = new Patrimony();
            patrimony.setName(name);
            patrimony.setArea(area);
            patrimony.setInputDate(inputDate);
            patrimony.setFoto(imageBytes);

            LOGGER.info("Entrando no método Repositório - Service ");
            iPatrimonyRepository.registerProductPatrimony(patrimony);

        } catch (IOException e) {
            LOGGER.error("Erro ao processar arquivo", e);
            throw new CustomException("Erro ao processar o arquivo.");
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------




    //-------------------------------------------------------------------------------------------------------------------------------

    @Override
    public List<PatrimonyResponseDto> listAllProducts(){
        LOGGER.info("Início do método para listagem de todos os produtos cadastrados - Service.");

        LOGGER.info("Início da verificação se a lista está vazia no banco de dados - Service.");
        List<PatrimonyResponseDto> list = iPatrimonyRepository.listAllProducts();
        if(list.isEmpty()){
            throw new CustomException("A lista de produtos está vazia no momento. Que tal cadastrar um novo produto?");
        }

        return list;
    }

    @Override
    public List<PatrimonyResponseDto> listProductRemoved( ){
        LOGGER.info("Início do método para listagem de todos os produtos removidos - Service.");

        LOGGER.info("Início da verificação se existe o produto no banco de dados - Service.");
        List<PatrimonyResponseDto> list = iPatrimonyRepository.listProductRemoved();
        if (list.isEmpty()){
            throw new CustomException("Parece que a lista está vazia.");
        }
        return list;
    }

    @Override
    public List<ProductDto> listAllProductsForArea(String area){
        LOGGER.info("Início do método para listagem de todos os produtos cadastrados pela Área - Service.");

        LOGGER.info("Início da verificação se a lista está vazia no banco de dados - Service.");
        List<ProductDto> products = iPatrimonyRepository.listAllProductsForArea(area);
        if(products.isEmpty()){
            throw new CustomException("Ainda não há produtos cadastrados para esta área. Que tal começar a adicionar alguns agora?");
        }

        return products;
    }

    //-------------------------------------------------------------------------------------------------------------------------------


    //-------------------------------------------------------------------------------------------------------------------------------
    @Override
    public PatrimonyRequestDto productDetails(String numSerie){
        LOGGER.info("Início do método para busca de um produto - Service.");

        LOGGER.info("Início da verificação se existe o produto no banco de dados - Service.");
        List<PatrimonyRequestDto> checkNumSerie = iPatrimonyRepository.checkProduct(numSerie);
        if (checkNumSerie.isEmpty())
            throw new CustomException("Não foi possível encontrar o produto. Verifique os dados informados e tente novamente.");

        LOGGER.info("Entrando no método do Reposiótio pela - Service ");
        return iPatrimonyRepository.productDetails(numSerie);

    }

    @Override
    public PatrimonyRequestDto productDetailsRemoved(String numSerie){
        LOGGER.info("Início do método para busca de um produto - Service.");

        LOGGER.info("Início da verificação se existe o produto no banco de dados - Service.");
        List<PatrimonyRequestDto> checkNumSerie = iPatrimonyRepository.checkProductRemoved(numSerie);
        if (checkNumSerie.isEmpty())
            throw new CustomException("Parece que o produto não faz parte da lista de itens removidos.");

        LOGGER.info("Entrando no método do Reposiótio pela - Service ");
        return iPatrimonyRepository.productDetailsRemoved(numSerie);

    }
    //-------------------------------------------------------------------------------------------------------------------------------



    //-------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void updateProduct(String numSerie, Map<String, String> updates){
        LOGGER.info("Início do método para alterar a area do produto - Service.");

        

        LOGGER.info("Início da verificação se existe o produto no banco de dados - Service.");
        List<PatrimonyRequestDto> checkNumSerie = iPatrimonyRepository.checkProduct(numSerie);
        if (checkNumSerie.isEmpty())
            throw new CustomException("Ops! Não conseguimos encontrar o produto. Confira as informações e tente outra vez.");

        LOGGER.info("Entrando no método do Reposiótio pela - Service ");
        iPatrimonyRepository.updateProduct(numSerie, updates);
    }
    //-------------------------------------------------------------------------------------------------------------------------------


    //-------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void removeProduct(String numSerie){
        LOGGER.info("Início do método para remover um produto - Service.");


        LOGGER.info("Início da verificação se existe o produto no banco de dados - Service.");
        List<PatrimonyRequestDto> checkNumSerie = iPatrimonyRepository.checkProduct(numSerie);
        if (checkNumSerie.isEmpty())
            throw new CustomException("O produto que você tentou remover não foi encontrado. Por favor, verifique os dados e tente novamente.");

        LOGGER.info("Entrando no método do Reposiótio pela - Service ");
        iPatrimonyRepository.removeProduct(numSerie);
    }



    @Override
    public void restoreRemoveProduct(String numSerie){
        LOGGER.info("Início do método para remover um produto - Service.");


        LOGGER.info("Início da verificação se existe o produto no banco de dados - Service.");
        List<PatrimonyRequestDto> checkNumSerie = iPatrimonyRepository.checkProductRemoved(numSerie);
        if (checkNumSerie.isEmpty())
            throw new CustomException("O produto que você tentou restaurar não foi encontrado na lista de removidos. Por favor, verifique os dados e tente novamente.");

        LOGGER.info("Entrando no método do Reposiótio pela - Service ");
        iPatrimonyRepository.restoreRemoveProduct(numSerie);
    }
    //-------------------------------------------------------------------------------------------------------------------------------



    //-------------------------------------------------------------------------------------------------------------------------------
    @Override
    public List<PatrimonyRequestDto> filterProduct(String numSerie, String name, String area, String inputDate){
        LOGGER.info("Início do método para filtrar o produto - Service.");

        List<PatrimonyRequestDto> products = iPatrimonyRepository.filterProduct(numSerie, name, area, inputDate);
        if (products.isEmpty())
            throw new CustomException("Nenhum produto encontrado com os filtros aplicados. Por favor, ajuste os critérios e tente novamente.");
        return products;
    }

    @Override
    public List<PatrimonyRequestDto> filterProductRemoved(String numSerie, String name, String area, String inputDate){
        LOGGER.info("Início do método para filtrar o produto dos removidos - Service.");

        List<PatrimonyRequestDto> products = iPatrimonyRepository.filterProductRemoved(numSerie, name, area, inputDate);
        if (products.isEmpty())
            throw new CustomException("Nenhum produto removido encontrado para os filtros aplicados. Por favor, ajuste os critérios de busca.");
        return products;
    }

    //-------------------------------------------------------------------------------------------------------------------------------

}

