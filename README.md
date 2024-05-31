# O projekcie

Projekt jest realizowany w ramach zaliczenia labolatoriów z podstaw programowania na AEH.

Temat: System zarządzania zadaniami w zespole

Program ma za zadanie, umożliwić zarządzanie zadaniami zespołu. System opiera się na użytkownikach i natatkach.
Aby kożystać z aplikacji należy utworzyć konto użytkownika.
Wszelkie dane związane z aplikacją, są zapisywane w bazie danych sqlite.




# Spis treści

* [Wymagania](#wymagania)
* [Funkcjonalności](#Funkcjonalności)
* * [Logowanie](#1-logowanie)
* * [Elementy związane z sesją](#2-Elementy-związane-z-sesją)
* * [peracje na użytkownikach](#3-operacje-na-użytkownikach)
* * [Operacje na notatkach](#4-operacje-na-notatkach)
* * [Operacje na zespołach](#5-operacje-na-zespołach)
* * [Raporty](#6-raporty)
* * [GUI](#7-gui)
* [Elementy TO DO](#Elementy-na-liście-TO-DO)
* [Comunity](#Comunity)


# Wymagania
Do tworzenia aplikacji wykożystano menagera pakietów Maven. Projekt zawiera
plik pom.xml zawierający niezbędne zależności.

Do prawidłowego działania aplikacji potrzebne są zewnętrzne biblioteki:
- sqlite-jdbc w wersji 3.45.2.0


# Funkcjonalności

### 1. Logowanie
Do zalogowania wymagane jest posiadanie konta w aplikacji.
Login to e-mail, hasło to dowolny ciąg znaków alfanumerycznych.
Przy starcie aplikacji, pojawia się okno logowania.

Obecnie sprawdzenie jest maksymalnie uproszczone i sprowadza się 
do porównania loginu i hasła zapisanych w bazie danych. Nie dodano równiż hashowania haseł.

> Obecnie nie zaiplementowano dodawania użytkowników. Wersja demo zawiera 4 konta
> (user1@wp.pl,user2@wp.pl,user3@wp.pl,user4@wp.pl) do wszystkich hasło to 1234

[wróć na start](#o-projekcie)



### 2. Elementy związane z sesją
Po zalogowaniu system otwiera nową sesję, do której przypisuje zalogowanego użytkownika.
ma to na celu łatwe przekazywanie danych o użytkowniku oraz rozszerzenie dostępnych statystyk.
Prawidłowe zamknięcie aplikacji powoduje również zakończenie sesji.
Jeżeli po logowaniu system wykryje, że istnieje wcześniej utworzona otwarta sesja,
to używa wcześniejszej sesji i nie tworzy nowej.
[wróć na start](#o-projekcie)

### 3. Operacje na użytkownikach
Do obsługi użytkowników używane są przedewszystkim dwie klasy:
* User - odpowiedzialna za tworzenie obiektu User z potrzebnymi właściwościami i metodami do obsługi użytkownika
* UserRepository do obsługi użytkownika w obszarze bazy danych.

Obecnie dostępne czynności:
- Dodawanie użytkownika
- Usuwanie użytkownika
- modyfikacja danych użytkownika

[wróć na start](#o-projekcie)

### 4. Operacje na notatkach
Do obsługi notatek używane są następujące klasy:
* Note - która reprezentuje jedną notatkę i tworzy obiekt notatki z wszystkimi właściwościami i metodami dotyczącymi Notatki
* Notes - zawiera statyczne metody dotyczące obsługi wielu notatek
* AddNote - dziedziczy po Note i zawiera dodatkowo statyczne metody wymagane do utworzenia notatki
* NoteRepository - zawiera wszystkie metody obsługujące notatki w bazie danych

Obecnie dostępne czynności
- Dodawanie notatki (GUI)
- Edycja notatki (GUI)
- Przypisanie do zespołu (GUI)
- Przypisanie do użytkownika (GUI)
- Usunięcie notatki

[wróć na start](#o-projekcie)


### 5. Operacje na zespołach
Do obsługi użytkowników używane są przedewszystkim dwie klasy:
* Team - tworzy obiekt reprezentujący Zespół z wszystkimi potrzebnymi właściwościami i metodami
* TeamRepository - zawiera wszystkie metody niezbędne do obsługi zespołów w bazie danych

Obecnie dostępne czynności
- baza danych - dodanie zespołu
- baza danych - usunięcie zespołu
- baza danych - pobranie informacji o zespole na podstawie nazwy lub id zespołu
- baza danych - pobranie listy użytkowników przypisanych do zespołu
- baza danych - pobranie listę zespołów przypisanych do użytkownika

[wróć na start](#o-projekcie)

### 6. Raporty
Do obsługi raportów używana jest klasa ReportsRepository. 
Pobiera ona odpowiednie dane z bazy danych i zwraca do wywołujących ją modułów.

Obecnie dostępne raporty:
- czas obsługi notatki (GUI)
- Ilość notatek przypisanych do użytkownika
- Ilość notatek przypisanych do zespołu

[wróć na start](#o-projekcie)

### 7. GUI
Do budowy GUI zostały użyte biblioteki z JDK.
Interfejs jest minimalistyczny i wymaga dalszej pracy. Niestety z tym elementem schodzi najwięcej czasu :)

Generalnie interfejs składa się z kilku klas. Główną klasą jest AppWindow,
która przechowuje główne parametry okna i metody zamykające okno.
Po niej dziedziczą:
- Dashboar - to główne okno aplikacji po zalogowaniu. Przedstawia widok wszystkich notatek użytkownika
- AddNote - okno służące do dodawania i edycji notatek.
- MyTeam - okno które docelowo ma być miejscem do zarządzania zespołami użytkownika.

Dodatkowo jest jeszcze klasa LogIn - która jest poza głuwną strukturą GUI i jest pierwszym oknem które widzi użytkownik.
[wróć na start](#o-projekcie)




# Elementy na liście TO DO
Mam świadomość, że aplikacja nie jest idealna i na pewno wymaga dalszej pracy.
Poniżej przedstawiam elementy które planuję rozwijać:

* GUI - jest póki co zrobione po macoszemu, należy włożyć w niego jeszcze dużo pracy ;)
* Dodawanie nowych użytkowników - jest określone miejsce w GUI rozpoczynające proces, są klasy i metody obsługujące dodawanie użytkowników, brakuje jeszcze logiki i samego okna dodawania użytkownika.
* Zarządzanie zespołami - jest początek w GUI, są elementy bazodanowe, brakuje logiki w gui i klasie Team.
* Wizualizacja raportów - jest logika i elementy bazodanowe pobierające odpowiednie dane, jednak w większości brak wizualizacji graficznej.
* Rozbudowana instrukcja obsługi - krok po kroku ze zdjęciami
  
[wróć na start](#o-projekcie)


# Comunity 
Projekt ma charakter edukacyjny. Nie planuję angażować społeczności do wspólnej pracy w tym temacie.

(jeżeli byłby to projekt otwarty na społeczność tutaj pojawiły by się oczekiwania autorów i kontakt pod który można kierować ewentualne sugestie)
[wróć na start](#o-projekcie)