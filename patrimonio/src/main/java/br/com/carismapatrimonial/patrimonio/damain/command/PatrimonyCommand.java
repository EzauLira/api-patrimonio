package br.com.carismapatrimonial.patrimonio.damain.command;

import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyRequestDto;
import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyResponseDto;
import br.com.carismapatrimonial.patrimonio.damain.entities.Patrimony;
import br.com.carismapatrimonial.patrimonio.damain.exception.CustomException;
import br.com.carismapatrimonial.patrimonio.port.input.IPatimony;
import br.com.carismapatrimonial.patrimonio.port.output.IPatrimonyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatrimonyCommand implements IPatimony {

    private final Logger LOGGER = LoggerFactory.getLogger(PatrimonyCommand.class);

    @Autowired
    IPatrimonyRepository iPatrimonyRepository;

    @Override
    public void rigisterProductCommand (PatrimonyRequestDto patrimonyRequestDto) {
        LOGGER.info("Início do método para validações - Service.");

        LOGGER.info("Inicio da construção do objeto - Service");
        Patrimony patrimony = Patrimony
                .builder().name(patrimonyRequestDto.getName())
                .quantity(patrimonyRequestDto.getQuantity())
                .area(patrimonyRequestDto.getArea())
                .build();

        LOGGER.info("Entrando no método Reposiótio pela - Command ");
        iPatrimonyRepository.registerProductPatrimony(patrimony);
    }

    @Override
    public List<PatrimonyResponseDto> listAllProducts(){
        LOGGER.info("Início do método para listagem de todos os produtos cadastrados - Service.");
        List<PatrimonyResponseDto> lista = iPatrimonyRepository.listAllProducts();
        if(lista.isEmpty()){
            throw new CustomException("Lista está vazia");
        }

        return lista;
    }

    @Override
    public PatrimonyResponseDto searchSpecificProduct(String name){
        LOGGER.info("Início do método para busca de um produto - Service.");

        List<PatrimonyResponseDto> nomee = iPatrimonyRepository.checkProduct(name);
        if (nomee.isEmpty())
            throw new CustomException("Produto não encontrado.");


        return iPatrimonyRepository.searchSpecificProduct(name);

    }

    @Override
    public void increaseQuantity(PatrimonyRequestDto patrimonyRequestDto){

        List<PatrimonyResponseDto> verifed = iPatrimonyRepository.checkProduct(patrimonyRequestDto.getName());

        if (verifed.isEmpty()){
            throw new CustomException("Procuto não encontrado.");
        }

        Patrimony patrimony = Patrimony
                .builder()
                .name(patrimonyRequestDto.getName())
                .quantity(patrimonyRequestDto.getQuantity())
                .build();
        iPatrimonyRepository.increaseQuantity(patrimony);

    }

    @Override
    public void decreaseQuantity(PatrimonyRequestDto patrimonyRequestDto){

        List<PatrimonyResponseDto> verifed = iPatrimonyRepository.checkProduct(patrimonyRequestDto.getName());

        if (verifed.isEmpty()){
            throw new CustomException("Procuto não encontrado.");
        }

        Patrimony patrimony = Patrimony
                .builder()
                .name(patrimonyRequestDto.getName())
                .quantity(patrimonyRequestDto.getQuantity())
                .build();
        iPatrimonyRepository.decreaseQuantity(patrimony);
    }


    @Override
    public void updateArea(PatrimonyRequestDto patrimonyRequestDto){

        List<PatrimonyResponseDto> nomee = iPatrimonyRepository.checkProduct(patrimonyRequestDto.getName());
        if (nomee.isEmpty())
            throw new CustomException("Produto não encontrado.");

        Patrimony patrimony = Patrimony
                .builder()
                .name(patrimonyRequestDto.getName())
                .area(patrimonyRequestDto.getArea())
                .build();

        iPatrimonyRepository.updateArea(patrimony);
    }
}
