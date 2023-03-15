const countriesXML = chargerHttpXML("../countriesTP.xml");
const codesCountries = countriesXML.getElementsByTagName("cca2");
const countryXSL = chargerHttpXML("../country.xsl");

let mouse = {
    x: 0,
    y: 0
}

let blueMode = false;
let bulle = document.getElementById("bulle");
let bulleDisplayed = false;

let selectElement = document.getElementById("countrySelect");
for (let i = 0; i < codesCountries.length; ++i) {
    let codeCountry = codesCountries[i].innerHTML;
    let optionElement = document.createElement('option');
    optionElement.value = codeCountry;
    optionElement.text = codeCountry;
    selectElement.add(optionElement);
}

function chargerHttpXML(xmlDocumentUrl) {
    let httpAjax;

    httpAjax = window.XMLHttpRequest ?
        new XMLHttpRequest() :
        new ActiveXObject('Microsoft.XMLHTTP');

    if (httpAjax.overrideMimeType) {
        httpAjax.overrideMimeType('text/xml');
    }

    //chargement du fichier XML à l'aide de XMLHttpRequest synchrone (le 3° paramètre est défini à false)
    httpAjax.open('GET', xmlDocumentUrl, false);
    httpAjax.send();

    return httpAjax.responseXML;
}

function button1_2(button) {
    blueMode = !blueMode;
    document.body.style.backgroundColor = (blueMode ? 'blue' : 'white');
    document.body.style.color = (blueMode ? 'white' : 'black');
    button.innerHTML = (blueMode ? "Mode Blanc" : "Mode Bleu");
}

function button3() {

}

function button4_5() {
    const serializer = new XMLSerializer();
    const svgXML = chargerHttpXML("exemple.svg");
    document.getElementById("exemple-svg").innerHTML = serializer.serializeToString(svgXML);

    document.getElementById("lesFormes").addEventListener("mousemove", (ev) => {
        if (ev.target.attributes.title !== undefined) {
            displayBulle(ev.target.attributes.title.value);
        }
        else {
            removeBulle();
        }
    })

    document.getElementById("lesFormes").addEventListener("mouseleave", (ev) => {
        removeBulle();
    })
}

function button13() {
    drawMap(() => {
        document.querySelector("#map g").addEventListener("mouseover", (ev) => {
            let land = ev.target;
            land.style.fill = "green";
            land.addEventListener("mouseleave", () => {
                land.style.fill = "#CCCCCC";
            })
        })
    });
}

function button12() {
    let rand = Math.floor(Math.random() * codesCountries.length);
    let countryCode = codesCountries[rand].innerHTML;

    let xsltProcessor = new XSLTProcessor();
    xsltProcessor.importStylesheet(countryXSL);
    xsltProcessor.setParameter("", "country_code", countryCode);
    let countryXML = xsltProcessor.transformToDocument(countriesXML);
    document.getElementById("countryToFind").innerHTML = getTagValue(countryXML, "country_name")

    drawMap(() => {
        document.querySelector("#map g").addEventListener("click", (ev) => {
            let land = ev.target;
            if (land.id === countryCode) {
                land.style.fill = "green";
                displayBulle("GAGNE !");
            }
            else {
                land.style.fill = "red";
                displayBulle("PERDU...");
            }

            land.addEventListener("mouseleave", () => {
                removeBulle();
            })
        })
    });
}

function displayBulle(text) {
    bulle.style.display = "block";
    bulle.innerText = text;
    bulle.style.top = (mouse.y + 10) + "px";
    bulle.style.left = (mouse.x + 30) + "px";
    bulleDisplayed = true;
}

function removeBulle() {
    bulleDisplayed = false;
    bulle.innerHTML = "";
    bulle.style.display = "none";
    bulle.style.top = "0";
    bulle.style.left = "0";
}

document.addEventListener("mousemove", (ev) => {
    mouse.x = ev.pageX;
    mouse.y = ev.pageY;

    if (bulleDisplayed) {
        bulle.style.top = (mouse.y + 10) + "px";
        bulle.style.left = (mouse.x + 30) + "px";
    }
})

function getTagValue(XML, tagName) {
    return XML.getElementsByTagName(tagName)[0].innerHTML;
}

function drawMap(callback) {
    const serializer = new XMLSerializer();
    const mapXML = chargerHttpXML("worldHigh.svg");
    document.getElementById("map").innerHTML = serializer.serializeToString(mapXML);

    callback();
}