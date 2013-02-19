<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:imsqti="http://www.imsglobal.org/xsd/imsqti_v2p1">
	<xsl:output omit-xml-declaration="yes" indent="yes" />

	<xsl:param name="pNewType" select="'myNewType'" />

	<xsl:template match="node()|@*">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>
