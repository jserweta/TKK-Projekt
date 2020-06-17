# TKK-Projekt "Konwerter JSON -> XML"

Projekt wykonany w ramach przedmiotu: Teoria kompilacji i kompilatory.

**Grupa:**<br/>
Kamil Skomro<br/>Mateusz Ruciński<br/>Jakub Serweta

## Cel projektu
Celem poniższego projektu jest zbudowanie biblioteki, która pozwala konwertować łańcuch znaków w formacie JSON do ciągu znaków w formacie XML. Projekt zostanie napisany w języku Java.  Nie przewidujemy korzystania z żadnych bibliotek.

## Założenia
* algorytm będzie przetwarzał wejście znak po znaku
* w przypadku nieprawidłowego ciągu znaków na wejściu, zostaje  rzucony wyjątek z informacją w którym miejscu algorytm napotkał niespójność w formacie JSON
* podczas parsowania zostanie zbudowany obiekt o strukturze drzewiastej, z którego w prosty sposób będzie można stworzyć poprawny plik XML

## Źródła
* https://www.freeformatter.com/json-to-xml-converter.html - konwerter online używany do testowania 

## Co zostało zrobione
Projekt został wykonany w 100%. Program konwertuje pliki w formacie JSON do plików w formacie XML.

## Teoria
* JSON (JavaScript Object Notation) - format tekstowy wymiany danych komputerowych wywodzący się z języka JavaScript. Komunikat JSON jest literałem obiektu języka Javascript, który w tym języku jest tablicą asocjacyjną. Wszystkie dane są zmiennymi (nie stanowią kodu wykonywalnego), a nazwy składników (właściwości) obiektów są otoczone cudzysłowami. Wartości mogą być typu string (napis otoczony cudzysłowem), number (liczba typu double), stanowić jedną ze stałych: false, null, true, być tablicą złożoną z takich elementów lub obiektem. Obiekty i tablice mogą być dowolnie zagnieżdżane. Cały komunikat jest kodowany w unikodzie i domyślnie jest to UTF-8.
* XML (Extensible Markup Language) - uniwersalny język znaczników przeznaczony do reprezentowania różnych danych w strukturalizowany sposób. XML jest standardem rekomendowanym oraz specyfikowanym przez organizację W3C. Język ten to podzbiór również HTML. Poprawny składniowo dokument XML powinien być tworzony zgodnie z kilkoma zasadami:
    * Powinien zawierać deklarację XML, która musi być umieszczona na samym początku pliku oraz musi posiadać atrybut version (dopuszczalne wartości to 1.0 albo 1.1) oraz opcjonalnie atrybuty:
        1. encoding – deklaruje zestaw znaków używanych w dokumencie XML, wartością domyślną jest kodowanie UTF-8 w systemie Unicode.
        1. standalone – określa tryb dokumentu XML, może przyjmować wartość yes lub no. Jeśli ustawimy wartość na yes to będzie oznaczało, że dokument nie zawiera innych plików, które muszą zostać przetworzone wraz z dokumentem. Może to być np. zewnętrzny arkusz stylów lub definicja DTD;
    * Każdy element musi zaczynać się znacznikiem początku elementu, oraz kończyć identycznym znacznikiem końca elementu, wyjątek stanowią elementy puste, czyli takie, które nie zawierają żadnych danych, ani innych elementów, mogą zawierać atrybuty;
Nazwy elementów mogą zawierać znaki alfanumeryczne (litery a–z, A–Z oraz cyfry 0–9) oraz dowolny znak z przedziałów: c0-d6, d8-f6, f8-2ff, 370-37d, 37f-1fff, 200c-200d, 2070, 218f, 2c00-2fef, 3001-d7ff, f900-fdcf, fdfo-fffd, 10000-efffffi. Znak dwukropka zarezerwowany jest dla identyfikacji przestrzeni nazw, której nazwa dopisywana jest przed nazwą elementu.
    * Nazwy elementów nie mogą zaczynać się od znaku łącznika (-), kropki, czy cyfry.
    * Elementy można zagnieżdżać w sobie i wtedy każdy element znajdujący się wewnątrz innego elementu jest nazywany „dzieckiem” tego elementu, a element, wewnątrz którego znajdują się inne elementy, zwany jest „rodzicem” tych elementów.
    * Każdy element może zawierać atrybuty, które definiuje się w znaczniku początku elementu.
    * Informacje, które zawiera element, muszą być zapisane pomiędzy znacznikiem początku i końca elementu;
    * W danych, atrybutach oraz nazwach elementów nie mogą pojawiać się niektóre znaki. Przykładem może być znak mniejszości (<), lub ampersand (&). Znaków tych nie można używać, ponieważ parsery XML „widząc” np. znak mniejszości wewnątrz elementu stwierdzą, że jest to początek znacznika i dokument zostanie błędnie zinterpretowany. Specyfikacja XML daje możliwość używania takich znaków z wykorzystaniem predefiniowanych odniesień jednostki. Jeśli więc chcemy wstawić znak mniejszości (<), wpisujemy zamiast niego sekwencję &lt; (ang. less than), natomiast gdy chcemy wprowadzić znak ampersand (&), wpisujemy – &amp;;
    * Jeżeli nie chcemy używać predefiniowanych odniesień jednostek, możemy część danych, które zawierają np. kod html lub xml, zapisać w sekcji danych znakowych, która nie będzie przetwarzana przez analizator składni XML. Znacznik początku sekcji danych znakowych to: <![CDATA[, a znacznik końca: ]]>.
    * W dokumencie XML możemy wykorzystywać komentarze, które zaczynają się znakami: <!--, a kończą: -->. Specyfikacja XML zezwala na wstawianie instrukcji przetwarzania, które są wykorzystywane do przeniesienia informacji do aplikacji. Instrukcje przetwarzania rozpoczynają się znakami: <?, a kończą: ?>.
