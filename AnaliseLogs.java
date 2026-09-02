import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnaliseLogs {

    public static void main(String[] args) {

        String arquivoEntrada = "access_grande.log";
        String arquivoSaida = "relatorio.txt";

        ManipuladorArquivo manipulador = new ManipuladorArquivo();
        AnalisadorLog analisador = new AnalisadorLog();

        try {

            List<String> linhas = manipulador.lerLinhas(arquivoEntrada);

            int totalValidos = analisador.contarRegistrosValidos(linhas);

            List<String> falhas = analisador.listarRequisicoesComFalha(linhas);

            double media = analisador.calcularTempoMedioPayments(linhas);

            List<String> relatorio = new ArrayList<>();

            relatorio.add("RELATORIO DE ANALISE DO SERVIDOR");
            relatorio.add("================================");
            relatorio.add("Registros validos processados: " + totalValidos);
            relatorio.add("Requisicoes com falha: " + falhas.size());

            relatorio.add("");
            relatorio.add("FALHAS ENCONTRADAS");

            for (String falha : falhas) {
                relatorio.add(falha);
            }

            relatorio.add("");
            relatorio.add("TEMPO MEDIO - /api/v1/payments");

            if (media == -1) {
                relatorio.add(
                    "Nao ha dados suficientes para calcular a media."
                );
            } else {
                relatorio.add(
                    String.format("%.2fms", media)
                );
            }

            manipulador.escreverLinhas(arquivoSaida,relatorio);

            System.out.println("Relatorio gerado com sucesso.");

        } catch (IOException e) {

            System.out.println(
                "Erro: o arquivo de entrada nao foi encontrado."
            );
        }
    }
}