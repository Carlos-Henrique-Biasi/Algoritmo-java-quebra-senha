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
                System.out.println("[API] 200 - Senha correta!");
                return 1; // Deu certo

            } else if(response.statusCode() == 401) {
                return 2; //Deu errado

            } else{
                System.out.println("[API] Erro HTTP: " + response.statusCode());
                System.out.println("[API] Resposta: " + response.body());
                return 3;
            }

        } catch (Exception e) {
            System.out.println("[API] ERRO DE CONEXÃO!");
            System.out.println("[API] Verifique se o servidor Node.js está ligado.");
            e.printStackTrace();
            return 4; //"Erro de conexão: Verifique se o servidor Node.js está ligado."
            
        }
    }
}
