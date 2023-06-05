# Rendszerterv

## 1. A rendszer célja
A rendszer célja, hogy a túrázni vágyó felhasználók könnyedén tudjanak találni számukra megfelelő túra eseményeket. Az alkalmazás lehetővé teszi a túrákra való feljelentkezést, amelyekről pontos, naprakész és gyors tájékoztatást kapnak.
Ehhez mindössze elegendő egy egyszerű regisztráció, illetve a túrára való jelentkezés és a felhasználó máris megszabadul a hosszas e-mailezésektől, körülményes egyeztetésektől. Az alkalmazás segítésével elhagyhatóvá vállnak az unalmas űrlapok megkönnyítve ezzel a jelentkezők és a túraszervezők munkáját is. Továbbá az alkalmazás célja, hogy könnyen kezelhető és átlátható, letisztult felületet biztosítson a felek számára.


## 2. Projektterv
- **Developer Team**: Vámos Ákos János, Vámos Márton István

| Funkció                     | Feladat                                             |
| ----------------------------| ----------------------------------------------------|
| Rendszerterv                | A rendszer átfogó, részletesdokumentációja          |
| Adattárolás                 | Az adatbázis megvalósítása                          |
| Regisztráció                | A regisztrációs felület létrehozása                 |
| Bejelentkezés               | A bejelentkezési felület létrehozása                |
| Főoldal                     | A főoldal létrehozása, események megjelenítése      |
| Túra létrehozása            | A "create" nézet létrehozása                        |
| Túra jelentkezés            | A felhasználó feljelentkeztetése a túrára           |
| Túráról lejelentkezés       | A felhasználó lejelentkeztetése a túráról           |

## 3. Üzleti feladatok modellje
![Üzleti feladatok modellje](/img/model_of_business_processes.png)


## 4. Követelmények
### Funkcionális követelmények:
- Felhasználó adatainak tárolása
- Bejelentkezés gomb: email cím és jelszó együttes megadásával beléphetünk a saját fiókunkba.
- Regisztrációs gomb: az itt megadott adatainkkal kitöltve tudunk fiókot létrehozni.
- Túraesemény feltöltése gomb: Túraesemény feltöltésére szolgál.

### Nem funkcionális követelmények:
A felhasználók nem jutnak hozzá más felhasználók személyes adataihoz a nevükön és az azonosítókon kívül.

### Törvényi előírások, szabványok
Az Európai Parlament És A Tanács (EU) 2016/679 rendelete

Az információs önrendelkezési jogról és az információszabadságról szóló 2011. évi CXII. törvény 4.§ (1) és (2) bekezdései

Trello (Kanban-tábla)

Github (a kóddal való együttműködés)

IntelliJ IDEA Ultimate (a kódoláshoz használt IDE)

Sublime Text vagy egyéb szöveg- és forráskód-szerkesztő (a dokumentáció kidolgozásához, markdown kiterjesztésű formátumban)

XAMPP tervező nézet - adatbázisterv megvalósításához

Diagrams.net - az ábárák megvalósításához


## 5. Funkcionális terv
### Rendszerszereplők:
- **Vendég**
- **Felhasználó**
- **Adminisztrátor**

### Rendszerhasználati esetek és lefutásaik:
**Vendég (Regisztráció nélküli fiók):**
- Amennyiben rendelkezik érvényes fiókkal, bejelentkezhet a "Bejelentkezés" menüpontra kattintva
- Felhasználói fiók hiányában regisztrálhat a "Regisztrációs" fül alatt

**Felhasználó:**
- Bejelentkezhet felhasználói fiókjába a "Bejelentkezés" menüpontra kattintva
- A "Jelentkezés" gombra kattintva feliratkozik a túraeseményre
- A "Lejelentkezés" gombra kattintva leiratkozhat a túraeseményről
- Kijelentkezhet fiókjából a "Kijelentkezés" gomb segítségével

**Adminisztrátor (túraszervező):**
- Bejelentkezhet adminisztrátori fiókjába a "Bejelentkezés" menü pontra kattintva
- Jogosultsága van új túraesemények létrehozására/feltöltésére, valamint a már meglévők módosítására és archiválására
- Kijelentkezhet fiókjából a "Kijelentkezés" gomb segítségével

### Menü-hierarchiák:
- **REGISZTRÁCIÓ**
    - Bejelentkezés (Már van meglévő fiókja?)

- **BEJELENTKEZÉS**
    - Regisztráció (Még nem rendelkezem meglévő fiókkal)

- **TÚRAESEMÉNYEK**
    - Túraeseményre jelentkezés
    - Túraesemény feltöltése (adminisztrátori)
    - Kijelentkezés

### Menükhöz tartozó specifikációk:

| Modul       | ID | Név                      | v.  | Kifejtés                                                                 |
|-------------|----|--------------------------|-----|--------------------------------------------------------------------------|
| Jogosultság | T1 | Bejelentkezési felület   | 1.0 | A felhasználó az email címe és jelszava segítségével bejelentkezhet. Ha a megadott email vagy jelszó nem megfelelő, akkor a felhasználó hibaüzenetet kap.                                                               |
| Jogosultság | T2 | Regisztráció | 1.0 | A felhasználó az email címével és jelszavának megadásával regisztrálja magát. A jelszó tárolása kódolva történik az adatbázisban. Ha valamelyik adat ezek közül hiányzik vagy nem felel meg a követelményeknek, akkor a rendszer értesíti erről a felhasználót. |
| Jogosultság | T3 | Kijelentkezés | 1.0 | A bejelentkezett felhasználók a kijelentkezés gombra kattintva kitudnak jelentkezni, amely a bejelentkező felületre irányíja őket. |
| Jogosultásg | T4 | Túraesemények megtekintése + hozzá tartozó funkciók | 1.0 | Túrák megjelenítése, illetve a túrát szervezőknek lehetőségük van túraesemények feltöltésére és törlésére. A túrára jelentkezőknek pedig a jelentkezésre és leíratkozásra. |


## 6. Fizikai környezet
A program asztali alkalmazásnak készül, amelyhez egy egyszerű regisztráció társul. 
Megvásárolt komponenseket nem tartalmaz. 
A felhasználók adatai titkosítással védeve vannak.

### Fejlesztői eszközök
- IntelliJ IDEA Ultimate
- Visual Studio Code
- XAMPP 



## 7. Architekturális terv
**Backend:**
- A rendszerhez szükséges egy adatbázis szeverhez való csatlakozás. Ehhez a XAMPP segítségével MySQL-t használunk.
- A kliens java programnyelven készült.

**Frontend:**
- Az applikáció megjelenítéséhez Java Swing GUI-t használunk.


## 8. Adatbázis terv
![Adatbázis terv](/img/database_plan.png)


## 9. Telepítési terv
1. A GitHub [repository](https://github.com/vamosakos/ProgTech-VM-VA) letöltése/klónozása
2. A letöltött fájlok saját könyvtárba szervezése
3. Apache, MySQL elindítása a XAMPP vezérlő pultban ![XAMPP icon](img/xampp_icon.png)
4. A database.sql importálása a lokális adatbázisba
5. Könyvtár megnyitása IntelliJ IDEA-ban
6. Program futtatása (Main) ![Run icon](img/run_icon.png)


## 10. Implementációs terv
- Az OCP (Open-Closed Principle) és a SRP (Single Responsiblity Principle) betartásával készüljön az alkalmazás java programnyelven.
- Az alkalmazásban felhasznált további tervezési minták a Stratégia és az Observer. 
- Az adatok tárolása adatbázisban történik meg.
- Logolás használata
- Unit tesztek használata 

## 11. Tesztterv
Az alkalmazáson meglévő funkciók tesztelésére unit teszteket alkalmazunk.
- [ ] Regisztráció - érvényes inputokkal
- [ ] Regisztráció - érvénytelen inputokkal
- [ ] Bejelentkezés - érvényes inputokkal
- [ ] Bejelentkezés - érvénytelen inputokkal
- [ ] Túraesemény létrehozása
- [ ] Túraesemény törlése
- [ ] Túraeseményre jelentkezés
- [ ] (Observer tervezési minta működése)
- [ ] (Stratégia tervezési mintha működése)
- [ ] Gombok megfelelő működése


## 12. Használt technológiák
- Trello: kanban tábla a projekt szervezéséhez és kezeléséhez
- Git: verziókezelés
- MySQL / MariaDB: adatbázisszerver
- Visual Studio Code: dokumentáció megírásához
- IntelliJ IDEA Ultimate: programkód megírásához