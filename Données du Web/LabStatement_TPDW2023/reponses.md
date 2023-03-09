# STEP 1:

> 1. the official names (country_name/offic_name) of the countries

**XPath :** `//country/country_name/offic_name`
**Justification :** On récupère le nom officiel pour chaque pays
****Résultats (2) :**** *Aruba, Islamic Republic of Afghanistan*

> 2. the latitude of each country

**XPath :** `//country/coordinates/@lat`
**Justification :** On récupère la valeur de l'attribut "lat" des "coordinates" des "country"
****Résultats (2) :**** *12.5, 33*

> 4. the area of each country

**XPath :** `//country/@area`
**Justification :** On récupère l'attribut area de chaque pays
**Résultats (2) :** *180, 652230*

> 5. the official names of European countries (continent = Europe)

**XPath :** `//country/infosContinent[continent="Europe"]/../country_name/offic_name`
**Justification :** On récupère le nom officiel pour chaque pays ayant pour continent l'Europe
**Résultats (2) :** *Åland Islands, Republic of Albania*

> 6. the common names of countries that do not have any native name

**XPath :** `//country/country_name[count(native_name)=0]/common_name`
**Justification :** On récupère le nom commun de chaque pays s'il ne possède pas de nom natif
**Résultats (que 1) :** *Antarctica*

> 7. the official names of the countries expressed in French, for those who have such names

**XPath :** `//country/languages[count(fra)=1]/../country_name/offic_name`
**Justification :** On récupère le nom officiel des pays dont la langue française (fra) fait partie
**Résultats (2) :** *Territory of the French Southern and Antarctic Lands, Republic of Burundi*

> 8. elements with at least one attribute

**XPath :** `//@*/..`
**Justification :** Récupère les parents de chaque attribut (donc forcément ces parents ont au moins un élément)
**Résultats (2) :** *country, native_name*

> 9. official names of the second native name of countries (for those who have)

**XPath :** `//country/country_name[count(native_name)>=2]/native_name[2]/offic_name`
**Justification :** On récupère le nom officiel du 2e nom natif de chaque pays s'il possède au moins 2 noms natifs
**Résultats (2) :** *Aruba, د افغانستان اسلامي جمهوریت*

> 10. the sum of the surfaces (area) of the countries of Africa

**XPath :** `sum(//country[infosContinent/continent="Africa"]/@area)`
**Justification :** On récupère la somme de l'aire des pays ayant pour continent l'Afrique
**Résultat :** *3.0318**E7***

> 11. countries whose common name is not contained in their official name

**XPath :** `//country/country_name[not(contains(offic_name, common_name))]/offic_name`
**Justification :** On récupère le nom officiel des pays dont le nom commun n'est pas contenu dans le nom officiel
**Résultats (2) :** *Argentine Republic, Swiss Confederation*

> 12. France's last neighbor

**XPath :** `//country/country_name[common_name="France"]/../borders/neighbour[last()]`
**Justification :** On récupère le voisin en dernière position parmi les bordures de la France
**Résultat :** *CHE*

> 13. the position of France in the XML document

**XPath :** `count(//country/country_name[common_name="France"]/../preceding-sibling::*)`
**Justification :** On compte le nombre d'éléments "frère" précédent le pays ayant le nom commun "France" (on ne fait pas +1, car on ne compte pas l'élément metadonnees)
**Résultat :** *75*

---

# STEP 2 :

Pour chaque élément ayant une * dans le DTD pour country, on vérifie s'il y a des pays qui ne possèdent pas cet élément

**XPath :** `//country[count(nom_element)=0]`
**Résultat :** Seuls les éléments *tld, currency, callingCode, coordinates et borders* peuvent apparaître **0 fois**.

On modifie donc le fichier `countries.dtd` en conséquence : pour les éléments apparaissant systématiquement pour chaque pays, on remplace le * par un +.
