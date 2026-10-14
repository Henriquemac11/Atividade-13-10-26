package atividades;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Questao02LivrosPaginacaoTest extends BaseTest {

    private static final By LIVROS = By.cssSelector("article.product_pod");

    @Test
    void extraiLivrosDasDuasPrimeirasPaginas() {
        driver.get("http://books.toscrape.com/");

        List<String[]> livros = new ArrayList<>();

        // ---------- página 1 ----------
        System.out.println("=== Página 1 ===");
        extrairPaginaAtual(livros);

        // ---------- clica em "next" ----------
        WebElement primeiroDaPagina1 = driver.findElement(LIVROS);
        WebElement next = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.next a")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", next);
        wait.until(ExpectedConditions.elementToBeClickable(next)).click();

        // espera a página 1 "sumir" e a página 2 carregar
        wait.until(ExpectedConditions.stalenessOf(primeiroDaPagina1));
        wait.until(ExpectedConditions.urlContains("page-2"));

        // ---------- página 2 ----------
        System.out.println("=== Página 2 ===");
        extrairPaginaAtual(livros);

        System.out.println("Total de livros extraídos: " + livros.size());
        assertEquals(40, livros.size(), "Cada página tem 20 livros, o total deveria ser 40");
    }

    private void extrairPaginaAtual(List<String[]> destino) {
        List<WebElement> artigos = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(LIVROS));
        for (WebElement artigo : artigos) {
            String titulo = artigo.findElement(By.cssSelector("h3 a")).getDomAttribute("title");
            String preco = artigo.findElement(By.cssSelector(".price_color")).getText();
            destino.add(new String[]{titulo, preco});
            System.out.printf("%s | %s%n", titulo, preco);
        }
    }
}
