# Rendszerterv

## 1. A rendszer célja
A rendszer célja, hogy a túrázni vágyó felhasználók könnyedén tudjanak találni számukra megfelelő túra eseményeket. Az alkalmazás lehetővé teszi a túrákra való feljelentkezést, amelyekről a túra idejének közeledtével pontos, naprakész és gyors tájékoztatást kapnak.
Ehhez mindössze elegendő egy egyszerű regisztráció, illetve a túrára való jelentkezés és a felhasználó máris megszabadul a hosszas e-mailezésektől, körülményes egyeztetésektől. Hiszen a már említett módon az eseményre feljelentkezők automatikus értesítést kapnak az esetleges változásokról és információkról, megkönnyítve ezzel a túraszervezők munkáját is. Továbbá az alkalmazás célja, hogy könnyen kezelhető és átlátható, letisztult felületet biztosítson a felek számára.

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



## 4. Követelmények
### Funkcionális követelmények:


### Nem funkcionális követelmények:


### Törvényi előírások, szabványok



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
    - Túraesemény szerkesztése (adminisztrátori)
    - Túraesemény feltöltése (adminisztrátori)
    - Kijelentkezés

### Menükhöz tartozó specifikációk:

| Modul       | ID | Név                      | v.  | Kifejtés                                                                 |
|-------------|----|--------------------------|-----|--------------------------------------------------------------------------|
| Jogosultság | T1 | Bejelentkezési felület   | 1.0 | A felhasználó az email címe és jelszava segítségével bejelentkezhet. Ha a megadott email vagy jelszó nem megfelelő, akkor a felhasználó hibaüzenetet kap.                                                               |
| Jogosultság | T2 | Regisztráció | 1.0 | A felhasználó az email címével és jelszavának megadásával regisztrálja magát. A jelszó tárolása kódolva történik az adatbázisban. Ha valamelyik adat ezek közül hiányzik vagy nem felel meg a követelményeknek, akkor a rendszer értesíti erről a felhasználót. |
| Jogosultság | T3 | Kijelentkezés | 1.0 | A bejelentkezett felhasználók a kijelentkezés gombra kattintva kitudnak jelentkezni, amely a bejelentkező felületre irányíja őket. |
| Feltöltés | T4 | Túraesemény feltöltése | 1.0 | A túrát szervezőknek lehetőségük van túraesemények feltöltésére. |
| Szerkesztés | T5 | Túraesemény szerkesztése | 1.0 | A hozzáadott esemény utólagos szerkesztésének lehetősége. |


## 6. Fizikai Környezet



## 7. Fejlesztői eszközök
- IntelliJ IDEA Ultimate
- Visual Studio Code
- XAMPP 



## 8. Architekturális terv



## 9. Adatbázis terv



## 10. Telepítési terv



## 11. Implementációs terv



## 12. Használt technológiák



## 13. Karbantartási terv


