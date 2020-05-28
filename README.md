# TKK-Projekt "Konwerter JSON -> XML"

Projekt wykonany w ramach przedmiotu: Teoria kompilacji i kompilatory.

**Grupa:**<br/>
Kamil Skomro<br/>Mateusz Ruciński<br/>Jakub Serweta

## Cel projektu
Celem poniższego projektu jest zbudowanie biblioteki, która pozwala konwertować łańcuch znaków w formacie JSON do ciągu znaków w formacie XML. Projekt zostanie napisany w języku Java.  Nie przewidujemy korzystania z żadnych bibliotek.

## Założenia
* algorytm będzie przetwarzał wejście znak po znaku
* w przypadku nieprawidłowego ciągu znaków na wejściu, zostaje  rzucony wyjątek z informacją w którym miejscu algorytm napotkał niespójność w formacie JSON
* podczas parsowania zostanie zbudowany obiekt o strukturze drzewiastej z którego potem można będzie w prosty sposób stworzyć poprawnego XML
