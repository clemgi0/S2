<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>

    <xsl:template match="LastName">
            <xsl:value-of select="preceding-sibling::FirstName"/>
            <xsl:text> </xsl:text>
            <xsl:value-of select="."/>
    </xsl:template>

    <xsl:template match="/">
        <table border="1">
            <tr>
                <th>Name</th>
                <th>Position</th>
                <th>E-mail</th>
            </tr>

            <xsl:apply-templates select="//Employee"/>
        </table>
        <p>Total Number Employees:
            <xsl:value-of select="count(//Employee)"/>
        </p>
    </xsl:template>

    <xsl:template match="Employee">
        <tr>
            <td>
                <xsl:apply-templates select="LastName"/>
            </td>
            <td>
                <xsl:apply-templates select="Position"/>
            </td>
            <td>
                <xsl:value-of select="Email"/>
            </td>
        </tr>
    </xsl:template>

</xsl:stylesheet>










