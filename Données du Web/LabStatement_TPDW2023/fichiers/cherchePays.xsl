<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>
    <xsl:param name="param_ref_type"/>

    <xsl:template match="/">
        <pays>
            <xsl:apply-templates select="//cca2[text() = $param_ref_type]/../../country_name/common_name"/>
        </pays>
        <capital>
            <xsl:apply-templates select="//cca2[text() = $param_ref_type]/../../capital"/>
        </capital>
        <languages>
            <xsl:for-each select="//cca2[text() = $param_ref_type]/../../languages/*">
                <xsl:value-of select="current()"/>
                <xsl:if test="position() != last()">, </xsl:if>
            </xsl:for-each>
        </languages>
        <flag>
            <img src="https://www.geonames.org/flags/x/{translate($param_ref_type, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')}.gif"
                     height="40" width="60"/>
        </flag>
        <sameLanguages>
            <xsl:apply-templates select="//languages[contains(.,$param_ref_type)]/../country_name/common_name">
        </sameLanguages>
    </xsl:template>
</xsl:stylesheet>
