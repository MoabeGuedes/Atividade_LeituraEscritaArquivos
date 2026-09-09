package br.edu.mackenzie.gerenciadornomes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioAnalisesBD {

    private final Connection connection;

    public RepositorioAnalisesBD(Connection connection) {
        this.connection = connection;
    }

    public void salvar(
            String arquivo,
            int registrosValidos,
            int quantidadeFalhas,
            double tempoMedioPayments
    ) throws SQLException {

        String sql = """
            INSERT INTO analises
            (arquivo, registros_validos, quantidade_falhas, tempo_medio_payments)
            VALUES (?, ?, ?, ?)
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, arquivo);
            statement.setInt(2, registrosValidos);
            statement.setInt(3, quantidadeFalhas);
            statement.setDouble(4, tempoMedioPayments);

            statement.executeUpdate();
        }
    }

    public List<String> listarHistorico() throws SQLException {

        String sql = """
            SELECT id, arquivo, registros_validos,
                   quantidade_falhas, tempo_medio_payments
            FROM analises
            ORDER BY id
            """;

        List<String> historico = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                long id = resultSet.getLong("id");
                String arquivo = resultSet.getString("arquivo");
                int registrosValidos =
                        resultSet.getInt("registros_validos");
                int quantidadeFalhas =
                        resultSet.getInt("quantidade_falhas");
                double tempoMedio =
                        resultSet.getDouble("tempo_medio_payments");

                String tempo;

                if (tempoMedio == -1) {
                    tempo = "sem dados";
                } else {
                    tempo = String.format("%.2f ms", tempoMedio);
                }

                String linha = String.format(
                    "%d | %s | válidos: %d | falhas: %d | payments: %s",
                    id,
                    arquivo,
                    registrosValidos,
                    quantidadeFalhas,
                    tempo
                );

                historico.add(linha);
            }
        }

        return historico;
    }
}