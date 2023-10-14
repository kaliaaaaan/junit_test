package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;


public class SportsRuParametrizedTest {

    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";

    }

    @BeforeEach
    void setUp() {
        open("https://sports.ru/");
    }

    static Stream<Arguments> sportRuTest() {
        return Stream.of(
                Arguments.of(Sports.FOOTBALL, List.of("Новости", "Посты", "Матчи", "РПЛ", "Фэнтези", "АПЛ", "Лига чемпионов", "Евро-2024")),
                Arguments.of(Sports.HOCKEY, List.of("Новости", "Посты", "Матчи","НХЛ", "КХЛ", "ЧМХ", "Фэнтези", "Команды", "Турниры"))
        );
    }

    @MethodSource
    @DisplayName("Проверка меню для разных страниц")
    @ParameterizedTest
    void sportRuTest(Sports sports, List<String> expectedButtons){
        $$(".navigation-navbar__list a").find(href(sports.getSport())).click();
        $$(".navigation-navbar__list a").filter(visible).should(CollectionCondition.texts(expectedButtons));

    }

    @CsvFileSource(resources = "/sportsRuSearchLeague.csv")
    @DisplayName("Проверка поиска на сайте sports.ru")
    @ParameterizedTest (name = "При вводе в поиске названия лиги {0} отобразилась страница с наименованием страны в которой проходит лига {1}")
    void successfulSearch(String searhLeague, String nameCountry) {
        $(".navigation-search__toggle").click();
        $(".navigation-search__input").setValue(searhLeague).pressEnter();
        $(".mainPart").shouldHave(text(nameCountry));
    }

    @ValueSource(
            strings = {"ЛА Лига", "МЛС", "eredivisie"}
    )
    @Tags({
            @Tag("web"),
            @Tag("search")
    })
    @DisplayName("Проверка поиска на сайте sports.ru")
    @ParameterizedTest(name = "Результат поиска {0} не пустой")
    void searchResultsShouldNotBeEmpty(String searhLeague2) {
        $(".navigation-search__toggle").click();
        $(".navigation-search__input").setValue(searhLeague2).pressEnter();
        $(".search-result").shouldNotHave(text("Ничего не найдено"));
    }

}
