package br.edu.mackenzie.gerenciadornomes;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ManipuladorArquivo {

    public List<String> lerLinhas(String caminho) throws IOException {
        return Files.readAllLines(Paths.get(caminho));
    }

    public void escreverLinhas(String caminho, List<String> linhas) throws IOException {
        Files.write(Paths.get(caminho), linhas);
    }
}