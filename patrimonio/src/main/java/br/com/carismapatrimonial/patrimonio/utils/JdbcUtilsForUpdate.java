package br.com.carismapatrimonial.patrimonio.utils;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JdbcUtilsForUpdate {

    public static List<String> buildParameters(String numSerie, Map<String, String> updates, String... keys) {
        List<String> params = new ArrayList<>();
        params.add(numSerie);

        for (String key : keys) {
            params.add(updates.getOrDefault(key, null));
        }

        return params;
    }

    public static void setParameter(PreparedStatement preparedStatement, int index, String value) throws SQLException {
        if (value instanceof String) {
            preparedStatement.setString(index,(String) value);
        } else if (value == null) {
            preparedStatement.setNull(index, Types.VARCHAR);
        } else {
            throw new IllegalArgumentException("Tipo de parâmetro não suportado: " + value.getClass());
        }
    }

}
