<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html"/>


  <xsl:template match="/">
    <html>
      <head>
        <title>XSLT Excercises</title>
      </head>
       <body bgcolor="beige">
	   
        <H1>Company Information for Infoteria</H1>
        <H2>Employee Listing</H2>
        <table border="1">
          <tr>
            <th>Name</th>
            <th>Position</th>
            <th>E-mail</th>
          </tr>
	      <xsl:for-each select="//Employee">
			<xsl:sort select="substring-after(Email,'@')"/>
			
			<tr>
				<td>
					<xsl:value-of select="child::LastName"/>
					<xsl:text> </xsl:text>
					<xsl:value-of select="FirstName"/>
				</td>
				<td><xsl:value-of select="Position"/></td>
				<td><xsl:value-of select="Email"/></td>
			</tr>
			
		  </xsl:for-each>
		  
        </table>
        <p>Total Number Employees: <xsl:value-of select="count(//Employee)"/> </p>
      </body>
    </html>
  </xsl:template>

  
 
  
  
  </xsl:stylesheet>










