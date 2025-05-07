package br.com.carismapatrimonial.patrimonio.adapter.output;

import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyRequestDto;
import br.com.carismapatrimonial.patrimonio.adapter.input.dto.PatrimonyResponseDto;
import br.com.carismapatrimonial.patrimonio.adapter.input.dto.ProductDto;
import br.com.carismapatrimonial.patrimonio.damain.entities.Patrimony;
import br.com.carismapatrimonial.patrimonio.damain.exception.BaseException;
import br.com.carismapatrimonial.patrimonio.damain.exception.CustomException;
import br.com.carismapatrimonial.patrimonio.port.output.IPatrimonyRepository;
import br.com.carismapatrimonial.patrimonio.utils.JdbcUtilsForUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            String sql = "SELECT * FROM inserir_produtos(?, ?, ?)";
            jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) preparedStatment -> {
                preparedStatment.setString(1, patrimony.getName());
                preparedStatment.setString(2, patrimony.getArea());
                preparedStatment.setString(3, patrimony.getInputDate());
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

        LOGGER.info("Início do Try-Catch e preparamento para listagem dos produtos do banco de dados.");
        try {
            String sql = "SELECT * FROM listar_produtos()";

            return jdbcTemplate.query(sql, new Object[]{}, (rs, rowNum) ->
                    new PatrimonyResponseDto(
                            rs.getString("num_serie"),
                            rs.getString("name"),
                            rs.getString("area"),
                            rs.getString("input_date")
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

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public PatrimonyRequestDto productDetails(String numSerie){
        LOGGER.info("Início do método de busca de um único item no banco de dados pela repository.");


        LOGGER.info("Início do try-catch e busca dos dados no banco de dados pela repository.");
        try{
            String sql = "SELECT * FROM detalhe_produto(?)";
            return jdbcTemplate.queryForObject(sql, new Object[]{numSerie}, (rs, rowNum) ->
                new PatrimonyRequestDto(rs.getString("num_serie")
                        ,rs.getString("name")
                        ,rs.getString("area")
                        ,rs.getString("input_date")));

        }catch (DataAccessException e){
            LOGGER.error("DataAccessException: {}", e.getMessage(), e);
            throw new BaseException(e.getMostSpecificCause().getMessage());
        }catch (Exception e){
            LOGGER.error("Exception: {}", e.getMessage(), e);
            throw new CustomException("Erro ao buscar produto no banco de dados.");
        }
    }

    @Override
    public PatrimonyRequestDto productDetailsRemoved(String numSerie){
        LOGGER.info("Início do método de busca de um único item no banco de dados pela repository.");


        LOGGER.info("Início do try-catch e busca dos dados no banco de dados pela repository.");
        try{
            String sql = "SELECT * FROM detalhe_produto_removido(?)";
            return jdbcTemplate.queryForObject(sql, new Object[]{numSerie}, (rs, rowNum) ->
                    new PatrimonyRequestDto(rs.getString("num_serie")
                            ,rs.getString("name")
                            ,rs.getString("area")
                            ,rs.getString("input_date")));

        }catch (DataAccessException e){
            LOGGER.error("DataAccessException: {}", e.getMessage(), e);
            throw new BaseException(e.getMostSpecificCause().getMessage());
        }catch (Exception e){
            LOGGER.error("Exception: {}", e.getMessage(), e);
            throw new CustomException("Erro ao buscar produto no banco de dados.");
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public List<PatrimonyRequestDto> checkProduct(String numSerie){
        LOGGER.info("Início do método para verificar se existe o produto no banco pela repository.");


        LOGGER.info("Início do try-catch e busca dos dados no banco de dados pela repository.");
        try{
            String sql = "SELECT * FROM buscar_produto(?)";
            return jdbcTemplate.query(sql, new Object[]{numSerie}, (rs, rowNum) ->
                    new PatrimonyRequestDto( rs.getString("num_serie"),
                            rs.getString("name")
                            ,rs.getString("area")
                            ,rs.getString("input_date")));

        }catch (DataAccessException e){
            LOGGER.error("DataAccessException: {}", e.getMessage(), e);
            throw new BaseException(e.getMostSpecificCause().getMessage());
        }catch (Exception e){
            LOGGER.error("Exception: {}", e.getMessage(), e);
            throw new CustomException("Erro ao buscar produto no banco de dados.");
        }
    }

    @Override
    public void updateProduct(String numSerie, Map<String, String> updates) {
        LOGGER.info("Início do método para alterar a area do produto pela repository.");

        LOGGER.info("Início do try-catch e atualizar a area dos dados no banco de dados pela repository.");
        try {
            String sql = "SELECT * FROM atualizar_produto(?, ?, ?, ?)";

            List<String> params = JdbcUtilsForUpdate.buildParameters(numSerie, updates, "name", "area","inputDate");

            jdbcTemplate.execute(sql.toString(), (PreparedStatementCallback<Void>) preparedStatment -> {
                for (int i = 0; i < params.size(); i++){
                    JdbcUtilsForUpdate.setParameter(preparedStatment, i + 1, params.get(i));
                }
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
    public List<PatrimonyResponseDto> listProductRemoved(){
        LOGGER.info("Início do método para listar produtos já removidos - Repository");

        LOGGER.info("Início do Try-Catch e preparamento para listagem dos produtos removidos do banco de dados.");
        try{
            String sql = "SELECT * FROM listar_produtos_removidos()";
            return jdbcTemplate.query(sql, new Object[]{}, (rs, rowNum) ->
                    new PatrimonyResponseDto( rs.getString("num_serie"),
                            rs.getString("name"),
                            rs.getString("area"),
                            rs.getString("input_date")
                    )
            );

        }catch (DataAccessException e){
            LOGGER.error("DataAcessException: {}", e.getMessage(), e);
            throw new BaseException(e.getMostSpecificCause().getMessage());
        }catch (Exception e){
            LOGGER.error("Exception: {}", e.getMessage(), e);
            throw new CustomException("Erro ao listar os produtos removidos.");

        }
    }

    @Override
    public void removeProduct(String numSerie){
        LOGGER.info("Início do método para remover produtos da tabela produtos e enviar para tabela histórico de removidos.");

        try{
            String sql = "CALL remover_produto(?)";

            jdbcTemplate.update(sql, numSerie);

        }catch (DataAccessException e){
            LOGGER.error("DataAcessException: {}", e.getMostSpecificCause(), e.getMessage());
            throw new BaseException(e.getMostSpecificCause().getMessage());
        }catch (Exception e){
            LOGGER.error("Exception: {}", e.getMessage(), e);
            throw new CustomException("Erro ao remover produtos da tabela produtos.");

        }
    }

    @Override
    public List<ProductDto> listAllProductsForArea(String area) {
        LOGGER.info("Início do método para listagem de todos os produtos do patrimônio pela Área - Repository");

        try {
            String sql = "SELECT * FROM buscar_produto_area(?)";

            return jdbcTemplate.query(sql, new Object[]{area}, (rs, rowNum) ->
                    new ProductDto(
                            rs.getString("num_serie"),
                            rs.getString("name"),
                            rs.getString("area")
                    )
            );
        }catch (DataAccessException e ){
            LOGGER.error("DataAccessException: {}", e.getMessage(), e);
            throw new BaseException(e.getMostSpecificCause().getMessage());
        }catch (Exception e ){
            LOGGER.error("Exception: {}", e.getMessage(), e);
            throw new CustomException("Erro ao listar os produtos.");
        }
    }

    @Override
    public void restoreRemoveProduct(String numSerie){
        LOGGER.info("Início do método para restaurar produtos da tabela historico de produtos removidos e enviar para tabela produtos.");

        try{
            String sql = "CALL restaurar_produto(?)";

            jdbcTemplate.update(sql, numSerie);

        }catch (DataAccessException e){
            LOGGER.error("DataAcessException: {}", e.getMostSpecificCause(), e.getMessage());
            throw new BaseException(e.getMostSpecificCause().getMessage());
        }catch (Exception e){
            LOGGER.error("Exception: {}", e.getMessage(), e);
            throw new CustomException("Erro ao remover produtos da tabela produtos.");

        }
    }

    @Override
    public List<PatrimonyRequestDto> checkProductRemoved(String numSerie){
        LOGGER.info("Início do método para verificar se existe o produto no banco pela repository.");


        LOGGER.info("Início do try-catch e busca dos dados no banco de dados pela repository.");
        try{
            String sql = "SELECT * FROM buscar_produto_removido(?)";
            return jdbcTemplate.query(sql, new Object[]{numSerie}, (rs, rowNum) ->
                    new PatrimonyRequestDto( rs.getString("num_serie"),
                            rs.getString("name")
                            ,rs.getString("area")
                            ,rs.getString("input_date")));

        }catch (DataAccessException e){
            LOGGER.error("DataAccessException: {}", e.getMessage(), e);
            throw new BaseException(e.getMostSpecificCause().getMessage());
        }catch (Exception e){
            LOGGER.error("Exception: {}", e.getMessage(), e);
            throw new CustomException("Erro ao buscar produto no banco de dados.");
        }
    }

    @Override
    public List<PatrimonyRequestDto> filterProduct(String numSerie, String name, String area, String inputDate){
        LOGGER.info("Início do método para filtrar um produto - Repository.");

        try{
            String sql = "SELECT * FROM filtrar_produto(?, ?, ?, ?)";
            return jdbcTemplate.query(sql, new Object[]{numSerie, name, area, inputDate}, (rs, rowNum) ->
                    new PatrimonyRequestDto( rs.getString("num_serie"),
                            rs.getString("name")
                            ,rs.getString("area")
                            ,rs.getString("input_date")));

        }catch (DataAccessException e){
            LOGGER.error("DataAccessException: {}", e.getMessage(), e);
            throw new BaseException(e.getMostSpecificCause().getMessage());
        }catch (Exception e){
            LOGGER.error("Exception: {}", e.getMessage(), e);
            throw new CustomException("Erro ao buscar produto no banco de dados.");
        }
    }

    @Override
    public List<PatrimonyRequestDto> filterProductRemoved(String numSerie, String name, String area, String inputDate){
        LOGGER.info("Início do método para filtrar um produto dos removidos - Repository.");

        try{
            String sql = "SELECT * FROM filtrar_produto_removido(?, ?, ?, ?)";
            return jdbcTemplate.query(sql, new Object[]{numSerie, name, area, inputDate}, (rs, rowNum) ->
                    new PatrimonyRequestDto( rs.getString("num_serie"),
                            rs.getString("name")
                            ,rs.getString("area")
                            ,rs.getString("input_date")));

        }catch (DataAccessException e){
            LOGGER.error("DataAccessException: {}", e.getMessage(), e);
            throw new BaseException(e.getMostSpecificCause().getMessage());
        }catch (Exception e){
            LOGGER.error("Exception: {}", e.getMessage(), e);
            throw new CustomException("Erro ao buscar produto no banco de dados.");
        }
    }
}
