package atividades;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Atividade 03 - Tabelas HTML.
 * Site: https://the-internet.herokuapp.com/tables
 *
 * Percorre as linhas da "Table 1" (id="table1") e, para os clientes com dívida
 * (coluna "Due") superior a $50.00, imprime Primeiro Nome, Último Nome e Web Site.
 *
 * Colunas da Table 1: 0=Last Name, 1=First Name, 2=Email, 3=Due, 4=Web Site, 5=Action
 */
class Questao03TabelaDividasTest extends BaseTest {

    @Test
    void listaClientesComDividaMaiorQue50() {
        driver.get("https://the-internet.herokuapp.com/tables");

        List<WebElement> linhas = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#table1 tbody tr")));

        List<String> encontrados = new ArrayList<>();

        System.out.println("Clientes com dívida superior a $50.00:");
        for (WebElement linha : linhas) {
            List<WebElement> colunas = linha.findElements(By.tagName("td"));

            String ultimoNome = colunas.get(0).getText();
            String primeiroNome = colunas.get(1).getText();
            double divida = Double.parseDouble(colunas.get(3).getText().replaceAll("[^\\d.]", ""));
            String site = colunas.get(4).getText();

            if (divida > 50.00) {
                System.out.printf("Primeiro Nome: %s | Último Nome: %s | Web Site: %s%n",
                        primeiroNome, ultimoNome, site);
                encontrados.add(primeiroNome + " " + ultimoNome);
            }
        }

        assertFalse(encontrados.isEmpty(), "Deveria haver clientes com dívida acima de $50.00");
    }
}
