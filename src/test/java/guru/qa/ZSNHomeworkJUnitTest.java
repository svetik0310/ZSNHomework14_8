package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ZSNHomeworkJUnitTest {
    @BeforeAll
    static void setup() {
        Configuration.browserSize = "1500x740";
        Configuration.browserPosition = "0x0";
    }

    @DisplayName("Find nothing by value hdjkhfksdfklsjf")
    @Test
    void findSynonymsNothingTest() {
        open("https://synonyms.reverso.net/синонимы/");
        $("#searchbox").setValue("hdjkhfksdfklsjf").pressEnter();
        $(".results-not-found").shouldHave(text("Результаты не найдены"));
    }

    @ValueSource(strings = {"Мама", "Школа"})
    @ParameterizedTest(name = "Find something by value {0}")
    void findSynonymsTest(String data) {
        open("https://synonyms.reverso.net/синонимы/");
        $("#searchbox").setValue(data).pressEnter();
        $$(".word-box").shouldBe(CollectionCondition.sizeGreaterThan(0));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/TestData.csv", numLinesToSkip = 0)
    void findSmthFromFileTest(String data, String result) {
        open("https://www.reverso.net/text-translation");
        $("button[class=app-new-top-bar-desktop__lang-trigger-button]").click();
        $$(".app-interface-languages__list-item").find(text(data)).click();
        $$(".app-new-top-bar-desktop__anchors-wrapper").first().shouldHave(text(result));
    }

    @CsvSource(value = {
            "Deutsch, Übersetzung",
            "Português, Tradução",
    })
    @ParameterizedTest(name = "Change language for {0}")
    void changeLanguageCsvTest(String data, String result) {
        open("https://www.reverso.net/text-translation");
        $("button[class=app-new-top-bar-desktop__lang-trigger-button]").click();
        $$(".app-interface-languages__list-item").find(text(data)).click();
        $$(".app-new-top-bar-desktop__anchors-wrapper").first().shouldHave(text(result));
    }

    static Stream<Arguments> changeLanguageTest() {
        return Stream.of(
                Arguments.of("Deutsch", List.of("Übersetzung", "Context", "Rechtschreibprüfung ", "Synonyme", "Konjugation")),
                Arguments.of("Português", List.of("Tradução", "Context", "Corretor ", "Sinónimos", "Conjugação"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Change language for {0}")
    void changeLanguageTest(String language, List<String> menuName) {
        open("https://www.reverso.net/text-translation");
        $("button[class=app-new-top-bar-desktop__lang-trigger-button]").click();
        $$(".app-interface-languages__list-item").find(text(language)).click();
        $$(".app-new-top-bar-desktop__anchors-wrapper a").shouldHave(CollectionCondition.texts(menuName));
    }

    @EnumSource(EnumLanguage.class)
    @ParameterizedTest(name = "Change language for {0}")
    void changeLanguageEnumTest(EnumLanguage language) {
        open("https://www.reverso.net/text-translation");
        $("button[class=app-new-top-bar-desktop__lang-trigger-button]").click();
        $$(".app-interface-languages__list-item").find(text(language.name())).click();
    }
}
