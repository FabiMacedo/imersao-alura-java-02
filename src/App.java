import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
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

        // pegar var de ambiente -> String imdbKey = System.getenv("IMDB_API_KEY");

        // String url =
        // "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";

        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";

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

        GeradoraDeFigurinhas geradora = new GeradoraDeFigurinhas();

        var diretorio = new File("figurinhas/");
        diretorio.mkdir();

        /*
         * for da lista completa
         * for (Map<String, String> filme : listaDeFilmes) {
         * String urlImagem = filme.get("image");
         * String titulo = filme.get("title");
         * 
         * InputStream inputStream = new URL(urlImagem).openStream();
         * String nomeArquivo = "figurinhas/" + titulo + ".png";
         * 
         * geradora.criaSticker(inputStream, nomeArquivo);
         * 
         * System.out.println(titulo);
         * System.out.println(filme.get("imDbRating"));
         * System.out.println();
         * }
         */

        System.out.println("\n");

        // for da lista com 3 elementos e terminal personalizado
        for (int i = 0; i < listaDeFilmes.size(); i++) {
            Map<String, String> filme = listaDeFilmes.get(i);

            String urlImagem = filme.get("image");
            String titulo = filme.get("title");
            double classificacao = Double.parseDouble(filme.get("imDbRating"));

            String textoFigurinha;
            if (classificacao >= 8.0) {
                textoFigurinha = "TOPZERA";
            } else {
                textoFigurinha = "HMMMMMM...";
            }

            InputStream inputStream = new URL(urlImagem).openStream();
            String nomeArquivo = "figurinhas/" + titulo + ".png";

            geradora.criaSticker(inputStream, nomeArquivo, textoFigurinha);

            System.out.println("\u001b[1mTítulo:\u001b[0m " + titulo);
            // System.out.println("\u001b[1mURL da imagem:\u001b[0m " + urlImagem);
            System.out.println("\u001b[37;1m\u001b[45;1mRanking: " + filme.get("imDbRating") + "\u001b[m");

            int numeroEstrelinhas = (int) classificacao;
            if (numeroEstrelinhas <= 3) {
                for (int j = 0; j < numeroEstrelinhas; j++) {
                    System.out.print("🍅");
                }
            } else {
                for (int s = 1; s <= numeroEstrelinhas; s++) {
                    System.out.print("⭐");
                }
            }
            System.out.println("\n");
        }
    }
}
