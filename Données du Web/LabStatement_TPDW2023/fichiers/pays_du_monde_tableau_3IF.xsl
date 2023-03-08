<?xml version="1.0" encoding="UTF-8"?>

<!-- New XSLT document created with EditiX XML Editor (http://www.editix.com) at Tue Mar 07 20:52:15 CET 2023 -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>
                    Countries of the world
                </title>
            </head>

            <body style="background-color:white;">
                <h1>Information about the countries</h1>

                <xsl:apply-templates select="//metadonnees"/>

                Styled by: GIRAUDON Clément, MAILLARD Swan (B3423)
                <hr/>
                <hr/>

                <table border="3" width="100%" align="center">
                    <tr>
                        <th>N°</th>
                        <th>Name</th>
                        <th>Capital</th>
                        <th>Coordinates</th>
                        <th>Neighbours</th>
                        <th>Flag</th>
                        <th>Spoken Languages</th>
                    </tr>
                    <xsl:apply-templates select="//country"/>
                </table>

            </body>
        </html>
    </xsl:template>

    <xsl:template match="metadonnees">
        <p style="text-align:center; color:green;">
            Objectif :
            <xsl:value-of select="objectif"/>
        </p>
    </xsl:template>

    <xsl:template match="country">
        <tr>
            <td>
                <xsl:value-of select="count(./preceding-sibling::*)"/>
            </td>
            <td>
                <xsl:value-of select="country_name/offic_name"/>
            </td>
            <td>
                <xsl:value-of select="capital"/>
            </td>
            <td>
                Latitude : <xsl:value-of select="coordinates/@lat"/><br/>
                Longitude : <xsl:value-of select="coordinates/@long"/>
            </td>
            <td>
                <xsl:choose>
                    <xsl:when test="count(borders) = 0">Île</xsl:when>
                </xsl:choose>
                
            </td>
            <td>Hey</td>
            <td>Hey</td>
        </tr>
    </xsl:template>


</xsl:stylesheet>


