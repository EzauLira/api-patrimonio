package br.com.carismapatrimonial.patrimonio.adapter.output;

import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyResponseDto;
import br.com.carismapatrimonial.patrimonio.damain.entities.Patrimony;
import br.com.carismapatrimonial.patrimonio.damain.exception.BaseException;
import br.com.carismapatrimonial.patrimonio.damain.exception.CustomException;
import br.com.carismapatrimonial.patrimonio.port.output.IPatrimonyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PatrimonyRepository implements IPatrimonyRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatrimonyRepository.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void registerProductPatrimony(Patrimony patrimony) {
        LOGGER.info("Início do método para registrar um intem no banco de dados - Patrimony");


        LOGGER.info("Início do Try-Catch e preparamento do objeto para subir no Banco de dados");
        try {
            String sql = "SELECT * FROM inserir_patrimonio(?, ?, ?)";
            jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) preparedStatment -> {
                preparedStatment.setString(1, patrimony.getName());
                preparedStatment.setInt(2, patrimony.getQuantity());
                preparedStatment.setString(3, patrimony.getArea());
                preparedStatment.execute();
                return null;
            });
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException: {}", e.getMessage(), e);
            throw new BaseException(e.getMostSpecificCause().getMessage());
        } catch (Exception e) {
            LOGGER.error("Exception: {}", e.getMessage(), e);
            throw new CustomException("Erro ao cadastrar produto no banco de dados.");
        }
    }

    @Override
    public List<PatrimonyResponseDto> listAllProducts() {
        LOGGER.info("Início do método para listagem de todos os produtos do patrimônio - Repository");

        LOGGER.info("Início do Try-Catch e preparamento do objeto para subir no Banco de dados");
        try {
            String sql = "SELECT * FROM listar_produtos()";

            return jdbcTemplate.query(sql, new Object[]{}, (rs, rowNum) ->
                    new PatrimonyResponseDto(
                            rs.getString("name"),
                            rs.getInt("quantity"),
                            rs.getString("area")
                    )
            );
        }catch (DataAccessException e ){
            LOGGER.error("DataAccessException: {}", e.getMessage(), e);
            throw new BaseException(e.getMostSpecificCause().getMessage());
        }catch (Exception e ){
            LOGGER.error("Exception: {}", e.getMessage(), e);
            throw new CustomException("Erro ao listar os produtos do banco de dados.");
        }
    }

    @Override
    public PatrimonyResponseDto searchSpecificProduct(String name){
        LOGGER.info("Início do método de busca de um único item no banco de dados pela repository.");


        LOGGER.info("Início do try-catch e busca dos dados no banco de dados pela repository.");
        try{
            String sql = "SELECT * FROM buscar_produto(?)";
            return jdbcTemplate.queryForObject(sql, new Object[]{name}, (rs, rowNum) ->
                new PatrimonyResponseDto(rs.getString("name")
                        ,rs.getInt("quantity")
                        ,rs.getString("area")));

        }catch (DataAccessException e){
            LOGGER.error("DataAccessException: {}", e.getMessage(), e);
            throw new BaseException(e.getMostSpecificCause().getMessage());
        }catch (Exception e){
            LOGGER.error("Exception: {}", e.getMessage(), e);
            throw new CustomException("Erro ao buscar produto no banco de dados.");
        }
    }

    @Override
    public List<PatrimonyResponseDto> checkProduct(String name){
        LOGGER.info("Início do método de busca de um único item no banco de dados pela repository.");


        LOGGER.info("Início do try-catch e busca dos dados no banco de dados pela repository.");
        try{
            String sql = "SELECT * FROM buscar_produto(?)";
            return jdbcTemplate.query(sql, new Object[]{name}, (rs, rowNum) ->
                    new PatrimonyResponseDto(rs.getString("name")
                            ,rs.getInt("quantity")
                            ,rs.getString("area")));

        }catch (DataAccessException e){
            LOGGER.error("DataAccessException: {}", e.getMessage(), e);
            throw new BaseException(e.getMostSpecificCause().getMessage());
        }catch (Exception e){
            LOGGER.error("Exception: {}", e.getMessage(), e);
            throw new CustomException("Erro ao buscar produto no banco de dados.");
        }
    }

    @Override
    public void increaseQuantity(Patrimony patrimony) {

        try {
            String sql = "SELECT * FROM atualizar_quantidade(?, ?)";

            jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) preparedStatment -> {
                preparedStatment.setString(1, patrimony.getName());
                preparedStatment.setInt(2, patrimony.getQuantity());
                preparedStatment.execute();
                return null;
            });

        } catch (DataAccessException e) {
            LOGGER.error("DataAcessException: {}", e.getMessage(), e);
            throw new BaseException(e.getMostSpecificCause().getMessage());
        } catch (Exception e) {
            LOGGER.error("Execpton: {}", e.getMessage(), e);
            throw new CustomException("Erro ao atualizar a quantidade do produto.");
        }
    }

    @Override
    public void decreaseQuantity(Patrimony patrimony) {

        try {
            String sql = "SELECT * FROM atualizar_quantidade(?, ?)";

            jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) preparedStatment -> {
                preparedStatment.setString(1, patrimony.getName());
                preparedStatment.setInt(2, patrimony.getQuantity());
                preparedStatment.execute();
                return null;
            });

        } catch (DataAccessException e) {
            LOGGER.error("DataAcessException: {}", e.getMessage(), e);
            throw new BaseException(e.getMostSpecificCause().getMessage());
        } catch (Exception e) {
            LOGGER.error("Execpton: {}", e.getMessage(), e);
            throw new CustomException("Erro ao atualizar a quantidade do produto.");
        }
    }


    @Override
    public void updateArea(Patrimony patrimony) {

        try {
            String sql = "SELECT * FROM atualizar_area(?, ?)";

            jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) preparedStatment -> {
                preparedStatment.setString(1, patrimony.getName());
                preparedStatment.setString(2, patrimony.getArea());
                preparedStatment.execute();
                return null;
            });

        } catch (DataAccessException e) {
            LOGGER.error("DataAcessException: {}", e.getMessage(), e);
            throw new BaseException(e.getMostSpecificCause().getMessage());
        } catch (Exception e) {
            LOGGER.error("Execpton: {}", e.getMessage(), e);
            throw new CustomException("Erro ao atualizar a quantidade do produto.");
        }
    }
}

