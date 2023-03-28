import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        // fazer conexão HTTP e buscar os top 250 filmes
        // top movies -> String url =
        // "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopTVs.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // pegar os dados que interessam (titulo, poster, classificção)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        // System.out.println(listaDeFilmes.size());
        // System.out.println(listaDeFilmes.get(0));

        // extrair e manipular os dados
        /*
         * for da lista completa
         * for (Map<String, String> filme : listaDeFilmes) {
         * System.out.println(filme.get("title"));
         * System.out.println(filme.get("image"));
         * System.out.println(filme.get("imDbRating"));
         * System.out.println();
         * }
         */

        // for da lista com 3 elementos
        for (int i = 0; i < 3; i++) {
            Map<String, String> filme = listaDeFilmes.get(i);
            System.out.println(filme.get("title"));
            System.out.println(filme.get("image"));
            System.out.println(filme.get("imDbRating"));
            System.out.println();

        }
    }
}