<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>
    <xsl:param name="country_code"/>

    <xsl:template match="/">
        <country_name>
            <xsl:apply-templates select="//cca2[text() = $country_code]/../../country_name/common_name"/>
        </country_name>
    </xsl:template>
</xsl:stylesheet>
