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
                Arguments.of(Sports.football, List.of("�������", "�����", "�����", "���", "�������", "���", "���� ���������", "����-2024")),
                Arguments.of(Sports.hockey, List.of("�������", "�����", "�����","���", "���", "���", "�������", "�������", "�������"))
        );
    }

    @MethodSource
    @DisplayName("�������� ���� ��� ������ �������")
    @ParameterizedTest
    void sportRuTest(Sports sports, List<String> expectedButtons){

        $$(".navigation-navbar__list a").find(href(sports.name())).click();
        $$(".navigation-navbar__list a").filter(visible).should(CollectionCondition.texts(expectedButtons));

    }

    @CsvFileSource(resources = "/sportsRuSearchLeague.csv")

    @DisplayName("�������� ������ �� ����� sports.ru")
    @ParameterizedTest (name = "��� ����� � ������ �������� ���� {0} ������������ �������� � ������������� ������ � ������� �������� ���� {1}")
    void successfulSearch(String searhLeague, String nameCountry) {

        $(".navigation-search__toggle").click();
        $(".navigation-search__input").setValue(searhLeague).pressEnter();
        $(".mainPart").shouldHave(text(nameCountry));
    }


    @ValueSource(
            strings = {"�� ����", "���", "eredivisie"}
    )

    @Tags({
            @Tag("web"),
            @Tag("search")
    })

    @DisplayName("�������� ������ �� ����� sports.ru")
    @ParameterizedTest(name = "��������� ������ {0} �� ������")
    void searchResultsShouldNotBeEmpty(String searhLeague2) {
        $(".navigation-search__toggle").click();
        $(".navigation-search__input").setValue(searhLeague2).pressEnter();
        $(".search-result").shouldNotHave(text("������ �� �������"));
    }


}
