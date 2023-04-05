import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GeradoraDeFigurinhas {

  public void criaSticker(InputStream inputStream, String nomeArquivo, String texto) throws Exception {
    // Leitura de imagem
    // InputStream inputStream = new FileInputStream(new
    // File("entrada/filme_maior.jpg"));
    /*
     * InputStream inputStream = new URL(
     * "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@.jpg")
     * .openStream();
     */
    BufferedImage imagemOriginal = ImageIO.read(inputStream);

    // Cria nova imagem em memória com transparencia e com tamanho novo
    int largura = imagemOriginal.getWidth();
    int altura = imagemOriginal.getHeight();
    int novaAltura = altura + 200;
    BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

    // Copiar a imagem original pra nova imagem (em memória)
    Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
    graphics.drawImage(imagemOriginal, 0, 0, null);

    // configurar a fonte
    var fonte = new Font("Impact", Font.BOLD, 80);
    graphics.setColor(Color.YELLOW);
    graphics.setFont(fonte);

    // escrever uma nova frase na nova imagem
    FontMetrics fontMetrics = graphics.getFontMetrics();
    Rectangle2D retangulo = fontMetrics.getStringBounds(texto, graphics);
    int larguraTexto = (int) retangulo.getWidth();
    int posicaoTextoX = (largura - larguraTexto) / 2;

    graphics.drawString(texto, posicaoTextoX, novaAltura - 100);

    // escrever a nova imagem em um arquivo
    ImageIO.write(novaImagem, "png", new File(nomeArquivo));
  }

}
