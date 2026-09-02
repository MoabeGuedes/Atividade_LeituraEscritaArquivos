import java.util.List;

public interface IAnalisadorLog {
  int contarRegistrosValidos(List<String> linhas);

  List<String> listarRequisicoesComFalha(List<String> linhas);

  double calcularTempoMedioPayments(List<String> linhas);
}