import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiNodeJS {
    // Método que recebe os dados extraídos da View e faz a mágica acontecer
    public static int enviarDadosParaApi(String urlDestino, String usuario, String senha) {
        try {
            // Monta o JSON com os dados recebidos
            String jsonInputString = String.format("{\"username\": \"%s\", \"senha\": \"%s\"}", usuario, senha);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlDestino))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonInputString))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Retorna uma mensagem baseada na resposta da sua API
            if (response.statusCode() == 200) {
                return 1; // Deu certo
            } else {
                return 2; //Deu errado
            }

        } catch (Exception e) {
            return 3; //"Erro de conexão: Verifique se o servidor Node.js está ligado."
        }
    }
}
