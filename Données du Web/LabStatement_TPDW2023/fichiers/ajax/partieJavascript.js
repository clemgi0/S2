class Page {
    static blueMode;
    static mouse;
    static tooltip;
    static countriesCodes;

    static init() {
        Page.blueMode = false;
        Page.mouse = {
            x: 0,
            y: 0
        };
        Page.tooltip = {
            element: document.getElementById("tooltip"),
            displayed: false
        };
        Page.countriesCodes = XML.countriesXML.getElementsByTagName("cca2");
        Page.generateCountrySelect();

        Map.init();
        Map.initCountriesData();
    }

    static switchColor() {
        const defaultColor = "#121212";
        const blueColor = "#244A66FF";

        Page.blueMode = !Page.blueMode;

        document.body.style.backgroundColor = (Page.blueMode ? blueColor : defaultColor);
        if (Page.blueMode) {
            document.getElementById("toggleInside").classList.add("blue");
            Page.displayTooltip("Desactiver l'affichage de la mer");
        } else {
            document.getElementById("toggleInside").classList.remove("blue");
            Page.displayTooltip("Activer l'affichage de la mer");
        }
    }

    static generateCountrySelect() {
        const selectElement = document.getElementById("countrySelect");
        for (let i = 0; i < Page.countriesCodes.length; ++i) {
            const codeCountry = Page.countriesCodes[i].innerHTML;
            const optionElement = document.createElement('option');
            optionElement.value = codeCountry;
            optionElement.text = codeCountry;
            selectElement.add(optionElement);
        }
    }

    static selectCountryCode() {
        const countryCode = document.getElementById("countrySelect").value;

        if (Map.focusedCountry !== null)
            Map.unfocusCountry();

        Map.focusedCountry = countryCode;

        const countryDOM = document.querySelector("#map path[id=" + countryCode + "]");
        countryDOM.style.fill = "#494949";

        Page.displayTooltip(Map.getCountryTooltipContent(countryCode));
    }

    static displayTooltip(content) {
        const tooltipDOM = Page.tooltip.element;
        tooltipDOM.style.display = "block";
        tooltipDOM.innerHTML = content;
        Page.tooltip.displayed = true;
        Page.updateTooltip();
    }

    static removeTooltip() {
        Page.tooltip.displayed = false;

        const tooltipDOM = Page.tooltip.element;
        tooltipDOM.innerHTML = "";
        tooltipDOM.style.display = "none";
        tooltipDOM.style.top = "0";
        tooltipDOM.style.left = "0";
    }

    static updateTooltip() {
        if (Page.tooltip.displayed) {
            const tooltipDOM = Page.tooltip.element;
            const offsetTop = (Page.mouse.y > window.innerHeight / 2 ? -60 - tooltipDOM.offsetHeight : 0);
            const offsetLeft = (Page.mouse.x > 3 * window.innerWidth / 4 ? -20 - tooltipDOM.offsetWidth : 0);
            tooltipDOM.style.top = (Page.mouse.y + 10 + offsetTop) + "px";
            tooltipDOM.style.left = (Page.mouse.x + 30 + offsetLeft) + "px";
        }
    }
}

class Map {
    static focusedCountry;
    static temperatureVisual;
    static memoCountries = {};

    static init() {
        Map.focusedCountry = null;
        Map.temperatureVisual = false;
        Map.draw();
        document.querySelector("#map g").addEventListener("mouseover", Map.hoverCountryEvent);
    }

    static draw() {
        const serializer = new XMLSerializer();
        const mapXML = XML.load("worldHigh.svg");
        document.getElementById("map").innerHTML = serializer.serializeToString(mapXML);
    }

    static initCountriesData() {
        document.querySelectorAll("#map path").forEach((countryDOM) => {
            const countryCode = countryDOM.id;
            const countryXML = XML.getCountryXML(countryCode);
            const countryJSON = JSON.load("https://restcountries.com/v2/alpha/" + countryCode);
            const currency = (!countryJSON['currencies'] ? "" : countryJSON['currencies'][0])

            let temperature = NaN;
            const latitude = XML.getTagValue(countryXML, "latitude");
            const longitude = XML.getTagValue(countryXML, "longitude");
            if (latitude && longitude) {
                const url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&hourly=temperature_2m&forecast_days=1";
                const temperatureJSON = JSON.load(url);
                temperature = (!temperatureJSON['hourly'] ? NaN : Math.max(...temperatureJSON['hourly']['temperature_2m']));
            }

            Map.memoCountries[countryCode] = {
                name: XML.getTagValue(countryXML, 'name'),
                capital: XML.getTagValue(countryXML, 'capital'),
                languages: XML.getTagValue(countryXML, 'languages'),
                flag: XML.getTagValue(countryXML, 'flag').trim(),
                currency: currency,
                temperature: temperature
            };
        });
        document.getElementById("loadingPage").style.display = "none";
    }

    static hoverCountryEvent(e) {
        const countryDOM = e.target;
        const countryCode = countryDOM.id;

        if (Map.focusedCountry !== countryCode) {
            if (Map.focusedCountry !== null) {
                Map.unfocusCountry();
            }
            Map.focusCountry(countryDOM);
        }

        countryDOM.addEventListener('click', Map.clickCountryEvent);

        countryDOM.addEventListener("mouseleave", Map.unfocusCountry);
    }

    static clickCountryEvent(e) {
        const countryDOM = e.target;
        const countryCode = countryDOM.id;

        if (Game.started && !Game.win && !Game.guesses.includes(countryCode))
            Game.guessCountry(countryDOM);
    }

    static focusCountry(countryDOM) {
        const countryCode = countryDOM.id;
        Map.focusedCountry = countryCode;

        if (!Game.started || !Game.guesses.includes(countryCode))
            countryDOM.style.fill = "#494949";

        if (Game.started && !Game.win && !Game.guesses.includes(countryCode))
            Page.displayTooltip("???");
        else
            Page.displayTooltip(Map.getCountryTooltipContent(countryCode));
    }

    static unfocusCountry() {
        if (Map.focusedCountry !== null) {
            const countryDOM = document.querySelector("#map path[id=" + Map.focusedCountry + "]");
            Map.focusedCountry = null;
            Page.removeTooltip();

            // On remet la couleur à l'origine, sauf si le pays a été coloré pendant une partie de GeoGuessr
            if (!Game.started || !Game.guesses.includes(countryDOM.id)) {
                if (Map.temperatureVisual) {
                    console.log(Map.memoCountries[countryDOM.id].temperature)
                    countryDOM.style.fill = Map.getColorFromTemperature(Map.memoCountries[countryDOM.id].temperature);
                }
                else
                    countryDOM.style.fill = "#CCCCCC";
            }
        }

    }

    static getCountryTooltipContent(countryCode) {
        const data = Map.memoCountries[countryCode];

        let content = "<h2>" + data.name + " " + data.flag + "</h2>";
        content += "<h3>" + data.capital + "</h3>";
        content += "<b>Langues :</b> " + data.languages;
        content += "<br/><b>Monnaie :</b> " + data.currency['name'] + " (" + data.currency['symbol'] + ")";
        content += "<br><b>Temperature maximale aujourd'hui :</b> " + data.temperature + "&#8451;";

        return content;
    }

    static colorWithTemperatureGradient() {
        Map.temperatureVisual = true;

        document.querySelectorAll("#map path").forEach((countryDOM) => {
            let temperature = Map.memoCountries[countryDOM.id].temperature;
            countryDOM.style.fill = Map.getColorFromTemperature(temperature);
        });
    }

    static getColorFromTemperature(temperature) {
        const tempMin = -30;
        const tempMax = 40;
        const gradient = ["#ff6000", "#ff6a0b", "#ff7315", "#ff7d20", "#ff862a", "#ff9035", "#ff9a3f", "#ffa34a", "#ffad55", "#ffb65f", "#ffc06a", "#ffc974", "#ffd37f", "#ffd585", "#ffd78c", "#ffda92", "#ffdc99", "#ffde9f", "#ffe1a5", "#ffe3ac", "#ffe5b2", "#ffe7b9", "#ffeabf", "#ffecc6", "#ffeecc", "#ffefd0", "#fff1d4", "#fff2d9", "#fff4dd", "#fff5e1", "#fff7e6", "#fff8ea", "#fff9ee", "#fffbf2", "#fffcf7", "#fffefb", "#ffffff", "#fbfcff", "#f7f9ff", "#f2f7ff", "#eef4ff", "#eaf1ff", "#e6eeff", "#e1ebff", "#dde8ff", "#d9e6ff", "#d4e3ff", "#d0e0ff", "#ccddff", "#c6d9ff", "#bfd5ff", "#b9d1ff", "#b2ccff", "#acc8ff", "#a5c4ff", "#9fc0ff", "#99bcff", "#92b7ff", "#8cb3ff", "#85afff", "#7fabff", "#74aaff", "#6aa9ff", "#5fa8ff", "#55a7ff", "#4aa6ff", "#3fa5ff", "#35a4ff", "#2aa3ff", "#20a2ff", "#15a1ff", "#0ba0ff", "#009fff"];

        if (isNaN(temperature))
            return "#ccc";

        temperature = Math.max(Math.min(temperature, tempMax), tempMin);
        const index = gradient.length - Math.floor((temperature-tempMin)*gradient.length/(Math.abs(tempMin)+Math.abs(tempMax))) - 1;
        return gradient[index];
    }

}

class XML {
    static countriesXML = XML.load("../countriesTP.xml");
    static countryXSL = XML.load("../country.xsl");

    static load(url) {
        let httpAjax;

        httpAjax = window.XMLHttpRequest ?
            new XMLHttpRequest() :
            new ActiveXObject('Microsoft.XMLHTTP');

        if (httpAjax.overrideMimeType) {
            httpAjax.overrideMimeType('text/xml');
        }

        httpAjax.open('GET', url, false);
        httpAjax.send();

        return httpAjax.responseXML;
    }

    static getCountryXML(countryCode) {
        const xsltProcessor = new XSLTProcessor();
        xsltProcessor.importStylesheet(XML.countryXSL);
        xsltProcessor.setParameter("", "country_code", countryCode);
        return xsltProcessor.transformToDocument(XML.countriesXML);
    }

    static getTagValue(XML, tagName) {
        return XML.getElementsByTagName(tagName)[0].innerHTML;
    }
}

class JSON {
    static load(url) {
        let httpAjax;

        httpAjax = window.XMLHttpRequest ?
            new XMLHttpRequest() :
            new ActiveXObject('Microsoft.XMLHTTP');

        if (httpAjax.overrideMimeType) {
            httpAjax.overrideMimeType('text/json');
        }

        httpAjax.open('GET', url, false);
        httpAjax.send();

        return eval("(" + httpAjax.responseText + ")");
    }
}

class Game {
    static started;
    static country;
    static guesses;
    static win;

    static init() {
        Game.started = false;
        Game.country = null;
        Game.guesses = [];
        Game.win = false;
    }

    static start() {
        Game.init();
        Game.started = true;
        Game.country = Page.countriesCodes[Math.floor(Math.random() * Page.countriesCodes.length)].innerHTML;

        const countryXML = XML.getCountryXML(Game.country);
        document.getElementById("map").classList.add("game")
        document.getElementById("gameExplanation").innerText = "Vous devez trouver le pays";
        document.getElementById("countryToFind").innerHTML = XML.getTagValue(countryXML, "name")
        document.getElementById("nbTries").innerText = "0";
        document.getElementById("gameText").style.display = "block";

        Map.init();
    }

    static stop() {
        Game.init();
        document.getElementById("map").classList.remove("game")
        document.getElementById("gameText").style.display = "none";
        Map.init();
    }

    static guessCountry(countryDOM) {
        const countryCode = countryDOM.id;

        Game.guesses.push(countryCode);
        document.getElementById("nbTries").innerText = Game.guesses.length;

        if (countryCode === Game.country) {
            Game.win = true;
            countryDOM.style.fill = "green";
            document.getElementById("gameExplanation").innerText = "Vous avez decouvert le pays";
            document.getElementById("countryToFind").style.color = "green";
            Page.displayTooltip("Bravo ! Vous l'avez eu en " + Game.guesses.length + " tentative(s) !" + Map.getCountryTooltipContent(countryCode));
        } else {
            countryDOM.style.fill = "red";
            Page.displayTooltip(Map.getCountryTooltipContent(countryCode))
        }
    }
}

document.addEventListener("mousemove", (ev) => {
    Page.mouse.x = ev.pageX;
    Page.mouse.y = ev.pageY;
    Page.updateTooltip();
});

document.getElementById("temperatureButton").addEventListener("mouseenter", (ev) => {
    Page.displayTooltip("Visualisation des temperatures maximales atteintes aujourd'hui");

    ev.target.addEventListener("mouseleave", () => {
        Page.removeTooltip();
    })
});

document.getElementById("toggle").addEventListener("mouseenter", (ev) => {
    if (Page.blueMode)
        Page.displayTooltip("Desactiver l'affichage de la mer");
    else
        Page.displayTooltip("Activer l'affichage de la mer");

    ev.target.addEventListener("mouseleave", () => {
        Page.removeTooltip();
    })
});


Page.init();