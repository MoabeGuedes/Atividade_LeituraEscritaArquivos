package br.edu.mackenzie.gerenciadornomes;
import java.util.ArrayList;
import java.util.List;

public class AnalisadorLog implements IAnalisadorLog {

    private boolean linhaValida(String linha) {

        if (linha.trim().isEmpty()) {
            return false;
        }

        String[] campos = linha.split("\\|");

        if (campos.length != 6) {
            return false;
        }

        for (int i = 0; i < campos.length; i++) {
            campos[i] = campos[i].trim();
        }

        try {
            Integer.parseInt(campos[4]);
        } catch (NumberFormatException e) {
            return false;
        }

        if (!campos[5].endsWith("ms")) {
            return false;
        }

        return true;
    }

    @Override
    public int contarRegistrosValidos(List<String> linhas) {

        int contador = 0;

        for (String linha : linhas) {

            if (linhaValida(linha)) {
                contador++;
            }
        }

        return contador;
    }

    @Override
    public List<String> listarRequisicoesComFalha(List<String> linhas) {

        List<String> falhas = new ArrayList<>();

        for (String linha : linhas) {

            if (!linhaValida(linha)) {
                continue;
            }

            String[] campos = linha.split("\\|");

            for (int i = 0; i < campos.length; i++) {
                campos[i] = campos[i].trim();
            }

            int status = Integer.parseInt(campos[4]);

            if (status >= 400) {

                String falha = campos[1] + " | " + campos[2] + " | " + campos[3] + " | " + campos[4] + " | " + campos[5];
                falhas.add(falha);
                
            }
        }

        return falhas;
    }

    @Override
    public double calcularTempoMedioPayments(List<String> linhas) {

        double soma = 0;
        int quantidade = 0;

        for (String linha : linhas) {

            if (!linhaValida(linha)) {
                continue;
            }

            String[] campos = linha.split("\\|");

            for (int i = 0; i < campos.length; i++) {
                campos[i] = campos[i].trim();
            }

            if (campos[3].equals("/api/v1/payments")) {

                String tempoTexto = campos[5].replace("ms", "");

                double tempo = Double.parseDouble(tempoTexto);

                soma += tempo;
                quantidade++;
            }
        }

        if (quantidade == 0) {
            return -1;
        }

        return soma / quantidade;
    }
}