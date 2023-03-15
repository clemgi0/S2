
const countriesXML = chargerHttpXML("../countriesTP.xml");

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function recupererPremierEnfantDeTypeElement(n) {
    let x = n.firstChild;
    while (x.nodeType !== 1) { // Test if x is an element node (and not a text node or other)
        x = x.nextSibling;
    }
    return x;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//change le contenu de l'�lement avec l'id "nom" avec la chaine de caract�res en param�tre	  
function setNom(nom) {
    let elementHtmlARemplir = window.document.getElementById("id_nom_a_remplacer");
    elementHtmlARemplir.innerHTML = nom;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//charge le fichier XML se trouvant � l'URL relative donn� dans le param�treet le retourne
function chargerHttpXML(xmlDocumentUrl) {

    let httpAjax;

    httpAjax = window.XMLHttpRequest ?
        new XMLHttpRequest() :
        new ActiveXObject('Microsoft.XMLHTTP');

    if (httpAjax.overrideMimeType) {
        httpAjax.overrideMimeType('text/xml');
    }

    //chargement du fichier XML � l'aide de XMLHttpRequest synchrone (le 3� param�tre est d�fini � false)
    httpAjax.open('GET', xmlDocumentUrl, false);
    httpAjax.send();

    return httpAjax.responseXML;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
// Charge le fichier JSON se trouvant � l'URL donn�e en param�tre et le retourne
function chargerHttpJSON(jsonDocumentUrl) {

    let httpAjax;

    httpAjax = window.XMLHttpRequest ?
        new XMLHttpRequest() :
        new ActiveXObject('Microsoft.XMLHTTP');

    if (httpAjax.overrideMimeType) {
        httpAjax.overrideMimeType('text/xml');
    }

    // chargement du fichier JSON � l'aide de XMLHttpRequest synchrone (le 3� param�tre est d�fini � false)
    httpAjax.open('GET', jsonDocumentUrl, false);
    httpAjax.send();

    return eval("(" + httpAjax.responseText + ")");
}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function Bouton2_ajaxEmployees(xmlDocumentUrl) {


    let xmlDocument = chargerHttpXML(xmlDocumentUrl);

    //extraction des noms � partir du document XML (avec une feuille de style ou en javascript)
    let lesNoms = xmlDocument.getElementsByTagName("LastName");

    // Parcours de la liste des noms avec une boucle for et 
    // construction d'une chaine de charact�res contenant les noms s�par�s par des espaces
    // Pour avoir la longueur d'une liste : attribut 'length'
    // Acc�s au texte d'un n�ud "LastName" : NOM_NOEUD.firstChild.nodeValue
    let chaineDesNoms = "";
    for (i = 0; i < lesNoms.length; i++) {
        if (i > 0) {
            chaineDesNoms = chaineDesNoms + ", ";
        }
        chaineDesNoms = chaineDesNoms + lesNoms[i].firstChild.nodeValue + " ";
    }


    // Appel (ou recopie) de la fonction setNom(...) ou bien autre fa�on de modifier le texte de l'�l�ment "span"
    setNom(chaineDesNoms);


}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function Bouton3_ajaxBibliographie(xmlDocumentUrl, xslDocumentUrl, baliseElementARecuperer) {

    // Chargement du fichier XSL � l'aide de XMLHttpRequest synchrone 
    let xslDocument = chargerHttpXML(xslDocumentUrl);

    //cr�ation d'un processuer XSL
    let xsltProcessor = new XSLTProcessor();

    // Importation du .xsl
    xsltProcessor.importStylesheet(xslDocument);

    // Chargement du fichier XML � l'aide de XMLHttpRequest synchrone 
    let xmlDocument = chargerHttpXML(xmlDocumentUrl);

    // Cr�ation du document XML transform� par le XSL
    let newXmlDocument = xsltProcessor.transformToDocument(xmlDocument);

    // Recherche du parent (dont l'id est "here") de l'�l�ment � remplacer dans le document HTML courant
    let elementHtmlParent = window.document.getElementById("id_element_a_remplacer");

    // ins�rer l'�lement transform� dans la page html
    elementHtmlParent.innerHTML = newXmlDocument.getElementsByTagName(baliseElementARecuperer)[0].innerHTML;


}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function Bouton4_ajaxBibliographieAvecParametres(xmlDocumentUrl, xslDocumentUrl, baliseElementARecuperer, paramXSL_type_reference, id) {

    // Chargement du fichier XSL � l'aide de XMLHttpRequest synchrone 
    let xslDocument = chargerHttpXML(xslDocumentUrl);

    //cr�ation d'un processuer XSL
    let xsltProcessor = new XSLTProcessor();

    // Importation du .xsl
    xsltProcessor.importStylesheet(xslDocument);

    //passage du param�tre � la feuille de style
    xsltProcessor.setParameter("", "param_ref_type", paramXSL_type_reference);

    // Chargement du fichier XML � l'aide de XMLHttpRequest synchrone 
    let xmlDocument = chargerHttpXML(xmlDocumentUrl);

    // Cr�ation du document XML transform� par le XSL
    let newXmlDocument = xsltProcessor.transformToDocument(xmlDocument);

    // Recherche du parent (dont l'id est "here") de l'�l�ment � remplacer dans le document HTML courant
    let elementHtmlParent = window.document.getElementById(id);

    // ins�rer l'�lement transform� dans la page html
    elementHtmlParent.innerHTML = newXmlDocument.getElementsByTagName(baliseElementARecuperer)[0].innerHTML;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function Bouton4_ajaxEmployeesTableau(xmlDocumentUrl, xslDocumentUrl) {
    //commenter la ligne suivante qui affiche la bo�te de dialogue!
    alert("Fonction � compl�ter...");
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
let blueMode = false;
document.getElementById("switchMode").addEventListener("click", (ev) => {
    blueMode = !blueMode;
    let bg = (blueMode ? 'blue' : 'white');
    let color = (blueMode ? 'white' : 'black');
    let text = (blueMode ? "Mode Blanc" : "Mode Bleu")
    document.body.style.backgroundColor = bg;
    document.body.style.color = color;
    ev.target.innerHTML = text;
});

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//button 3

let codes = countriesXML.getElementsByTagName("cca2");

for (let i = 0; i < codes.length; i++) {
    option = document.createElement("option")
    option.text = codes[i].innerHTML
    option.value = codes[i].innerHTML;
    document.getElementById("countrySelect").appendChild(option)
}

function button3() {
    Bouton4_ajaxBibliographieAvecParametres("../countriesTP.xml", "../cherchePays.xsl", "pays", document.getElementById("countrySelect").value, "countrySelectName")
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//button 6

function button6() {
    const worldMap = chargerHttpXML("./worldHigh.svg");
    const serializedWorldMap = new XMLSerializer();
    document.getElementById("worldMap").innerHTML = serializedWorldMap.serializeToString(worldMap);
    document.getElementById("worldMap").addEventListener("mouseover", (ev) => {
        if (ev.target.attributes.countryname !== undefined) {
            let cca2 = ev.target.attributes.id.value;
            document.getElementById("Name").innerText = ev.target.attributes.countryname.value;
            Bouton4_ajaxBibliographieAvecParametres("../countriesTP.xml", "../cherchePays.xsl", "capital", cca2, "Capital")
            Bouton4_ajaxBibliographieAvecParametres("../countriesTP.xml", "../cherchePays.xsl", "languages", cca2, "LanguagesSpoken")
            Bouton4_ajaxBibliographieAvecParametres("../countriesTP.xml", "../cherchePays.xsl", "flag", cca2, "Flag")
            let jsonurl = "https://restcountries.com/v2/alpha/" + cca2
            let json = chargerHttpJSON(jsonurl)
            document.getElementById("Currency").innerText = json.currencies[0].name
        }
    })
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//part 11


let code = document.getElementById("countrySelect").value;

let xslDocument = chargerHttpXML("../cherchePays.xsl");
let xsltProcessor = new XSLTProcessor();
xsltProcessor.importStylesheet(xslDocument);
xsltProcessor.setParameter("", "param_ref_type", code);
let newXmlDocument = xsltProcessor.transformToDocument(countriesXML);
newXmlDocument.getElementsByTagName(baliseElementARecuperer)