package br.com.carismapatrimonial.patrimonio.damain.command;

import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyRequestDto;
import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyResponseDto;
import br.com.carismapatrimonial.patrimonio.adapter.input.dto.ProductDto;
import br.com.carismapatrimonial.patrimonio.damain.entities.Patrimony;
import br.com.carismapatrimonial.patrimonio.damain.exception.CustomException;
import br.com.carismapatrimonial.patrimonio.port.input.IPatimony;
import br.com.carismapatrimonial.patrimonio.port.output.IPatrimonyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Service
public class PatrimonyCommand implements IPatimony {

    private final Logger LOGGER = LoggerFactory.getLogger(PatrimonyCommand.class);

    @Autowired
    IPatrimonyRepository iPatrimonyRepository;

    @Override
    public void rigisterProductCommand (PatrimonyRequestDto patrimonyRequestDto) {
        LOGGER.info("Início do método para registrar o produto - Service.");

        LOGGER.info("Inicio da construção do objeto - Service");
        Patrimony patrimony = Patrimony
                .builder().name(patrimonyRequestDto.getName())
                .area(patrimonyRequestDto.getArea())
                .inputDate(patrimonyRequestDto.getInputDate())
                .build();

        LOGGER.info("Entrando no método Reposiótio pela - Service ");
        iPatrimonyRepository.registerProductPatrimony(patrimony);
    }

    @Override
    public List<PatrimonyResponseDto> listAllProducts(){
        LOGGER.info("Início do método para listagem de todos os produtos cadastrados - Service.");

        LOGGER.info("Início da verificação se a lista está vazia no banco de dados - Service.");
        List<PatrimonyResponseDto> list = iPatrimonyRepository.listAllProducts();
        if(list.isEmpty()){
            throw new CustomException("Lista está vazia");
        }

        return list;
    }

    //-------------------------------------------------------------------------------------
    @Override
    public PatrimonyRequestDto productDetails(String numSerie){
        LOGGER.info("Início do método para busca de um produto - Service.");

        LOGGER.info("Início da verificação se existe o produto no banco de dados - Service.");
        List<PatrimonyRequestDto> checkNumSerie = iPatrimonyRepository.checkProduct(numSerie);
        if (checkNumSerie.isEmpty())
            throw new CustomException("Produto não encontrado.");

        LOGGER.info("Entrando no método do Reposiótio pela - Service ");
        return iPatrimonyRepository.productDetails(numSerie);

    }

    @Override
    public PatrimonyRequestDto productDetailsRemoved(String numSerie){
        LOGGER.info("Início do método para busca de um produto - Service.");

        LOGGER.info("Início da verificação se existe o produto no banco de dados - Service.");
        List<PatrimonyRequestDto> checkNumSerie = iPatrimonyRepository.checkProductRemoved(numSerie);
        if (checkNumSerie.isEmpty())
            throw new CustomException("Produto não encontrado.");

        LOGGER.info("Entrando no método do Reposiótio pela - Service ");
        return iPatrimonyRepository.productDetailsRemoved(numSerie);

    }

    //-------------------------------------------------------------------------------------

    @Override
    public void updateProduct(String numSerie, Map<String, String> updates){
        LOGGER.info("Início do método para alterar a area do produto - Service.");

        LOGGER.info("Início da verificação se existe o produto no banco de dados - Service.");
        List<PatrimonyRequestDto> checkNumSerie = iPatrimonyRepository.checkProduct(numSerie);
        if (checkNumSerie.isEmpty())
            throw new CustomException("Produto não encontrado.");

        LOGGER.info("Entrando no método do Reposiótio pela - Service ");
        iPatrimonyRepository.updateProduct(numSerie, updates);
    }

    @Override
    public List<PatrimonyResponseDto> listProductRemoved( ){
        LOGGER.info("Início do método para listagem de todos os produtos removidos - Service.");

        LOGGER.info("Início da verificação se existe o produto no banco de dados - Service.");
        List<PatrimonyResponseDto> list = iPatrimonyRepository.listProductRemoved();
        if (list.isEmpty()){
            throw new CustomException("lista está vazia");
        }
        return list;
    }

    @Override
    public void removeProduct(String numSerie){
        LOGGER.info("Início do método para remover um produto - Service.");


        LOGGER.info("Início da verificação se existe o produto no banco de dados - Service.");
        List<PatrimonyRequestDto> checkNumSerie = iPatrimonyRepository.checkProduct(numSerie);
        if (checkNumSerie.isEmpty())
            throw new CustomException("Produto não encontrado.");

        LOGGER.info("Entrando no método do Reposiótio pela - Service ");
        iPatrimonyRepository.removeProduct(numSerie);
    }

    @Override
    public List<ProductDto> listAllProductsForArea(String area){
        LOGGER.info("Início do método para listagem de todos os produtos cadastrados pela Área - Service.");

        LOGGER.info("Início da verificação se a lista está vazia no banco de dados - Service.");
        List<ProductDto> products = iPatrimonyRepository.listAllProductsForArea(area);
        if(products.isEmpty()){
            throw new CustomException("Nenhum produto cadastrado para a área informada.");
        }

        return products;
    }

    @Override
    public void restoreRemoveProduct(String numSerie){
        LOGGER.info("Início do método para remover um produto - Service.");


        LOGGER.info("Início da verificação se existe o produto no banco de dados - Service.");
        List<PatrimonyRequestDto> checkNumSerie = iPatrimonyRepository.checkProductRemoved(numSerie);
        if (checkNumSerie.isEmpty())
            throw new CustomException("Produto não encontrado.");

        LOGGER.info("Entrando no método do Reposiótio pela - Service ");
        iPatrimonyRepository.restoreRemoveProduct(numSerie);
    }

    @Override
    public List<PatrimonyRequestDto> filterProduct(String numSerie, String name, String area, String inputDate){
        LOGGER.info("Início do método para filtrar o produto - Service.");

        List<PatrimonyRequestDto> products = iPatrimonyRepository.filterProduct(numSerie, name, area, inputDate);
        if (products.isEmpty())
            throw new CustomException("Nenhum produto encontrado para os filtros fornecidos.");
        return products;
    }

    @Override
    public List<PatrimonyRequestDto> filterProductRemoved(String numSerie, String name, String area, String inputDate){
        LOGGER.info("Início do método para filtrar o produto dos removidos - Service.");

        List<PatrimonyRequestDto> products = iPatrimonyRepository.filterProductRemoved(numSerie, name, area, inputDate);
        if (products.isEmpty())
            throw new CustomException("Nenhum produto encontrado para os filtros fornecidos.");
        return products;
    }
}

