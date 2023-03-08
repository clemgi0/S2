#!/bin/sh
TOPDIR=`dirname $0`;
EDITIX_MAIN="com.japisoft.editix.main.Main";
EDITIX_LIB=${TOPDIR}/../lib/xml-apis.jar:${TOPDIR}/../lib/batik/batik-anim.jar:${TOPDIR}/../lib/batik/batik-awt-util.jar:${TOPDIR}/../lib/batik/batik-bridge.jar:${TOPDIR}/../lib/batik/batik-codec.jar:${TOPDIR}/../lib/batik/batik-css.jar:${TOPDIR}/../lib/batik/batik-dom.jar:${TOPDIR}/../lib/batik/batik-ext.jar:${TOPDIR}/../lib/batik/batik-extension.jar:${TOPDIR}/../lib/batik/batik-gui-util.jar:${TOPDIR}/../lib/batik/batik-gvt.jar:${TOPDIR}/../lib/batik/batik-parser.jar:${TOPDIR}/../lib/batik/batik-script.jar:${TOPDIR}/../lib/batik/batik-svg-dom.jar:${TOPDIR}/../lib/batik/batik-svggen.jar:${TOPDIR}/../lib/batik/batik-swing.jar:${TOPDIR}/../lib/batik/batik-transcoder.jar:${TOPDIR}/../lib/batik/batik-util.jar:${TOPDIR}/../lib/batik/batik-xml.jar:${TOPDIR}/../lib/batik/js.jar:${TOPDIR}/../lib/berkley/db.jar:${TOPDIR}/../lib/berkley/dbxml.jar:${TOPDIR}/../lib/commons-collections-3.2.jar:${TOPDIR}/../lib/commons-collections-testframework-3.2.jar:${TOPDIR}/../lib/commons-httpclient-3.1.jar:${TOPDIR}/../lib/commons-logging-1.1.1-javadoc.jar:${TOPDIR}/../lib/commons-logging-1.1.1-sources.jar:${TOPDIR}/../lib/commons-logging-1.1.1.jar:${TOPDIR}/../lib/commons-logging-adapters-1.1.1.jar:${TOPDIR}/../lib/commons-logging-api-1.1.1.jar:${TOPDIR}/../lib/commons-logging-tests.jar:${TOPDIR}/../lib/commons-net-3.3.jar:${TOPDIR}/../lib/cssparser-0.9.5.jar:${TOPDIR}/../lib/editix.jar:${TOPDIR}/../lib/edtftpj-1.4.2.jar:${TOPDIR}/../lib/exist/exist.jar:${TOPDIR}/../lib/exist/log4j-1.2.15.jar:${TOPDIR}/../lib/exist/xmlrpc-1.2-patched.jar:${TOPDIR}/../lib/fop/avalon-framework-impl-4.3.1.jar:${TOPDIR}/../lib/fop/avalon-framework-api-4.3.1.jar:${TOPDIR}/../lib/fop/batik-all-1.10.jar:${TOPDIR}/../lib/fop/commons-io-1.3.1.jar:${TOPDIR}/../lib/fop/commons-logging-1.0.4.jar:${TOPDIR}/../lib/fop/fontbox-2.0.7.jar:${TOPDIR}/../lib/fop/fop.jar:${TOPDIR}/../lib/fop/serializer-2.7.2.jar:${TOPDIR}/../lib/fop/xalan-2.7.2.jar:${TOPDIR}/../lib/fop/xml-apis-1.3.04.jar:${TOPDIR}/../lib/fop/xml-apis-ext-1.3.04.jar:${TOPDIR}/../lib/fop/xmlgraphics-commons-2.3.jar:${TOPDIR}/../lib/htmlparser-1.4.jar:${TOPDIR}/../lib/isorelax.jar:${TOPDIR}/../lib/jakarta-oro-2.0.8.jar:${TOPDIR}/../lib/jakarta-regexp-1.2.jar:${TOPDIR}/../lib/jdom.jar:${TOPDIR}/../lib/json.jar:${TOPDIR}/../lib/msv.jar:${TOPDIR}/../lib/poi-3.12-20150511.jar:${TOPDIR}/../lib/poi-ooxml-3.12-20150511.jar:${TOPDIR}/../lib/poi-ooxml-schemas-3.12-20150511.jar:${TOPDIR}/../lib/poi-scratchpad-3.12-20150511.jar:${TOPDIR}/../lib/relaxngDatatype.jar:${TOPDIR}/../lib/resolver.jar:${TOPDIR}/../lib/saxon/saxon-license.lic:${TOPDIR}/../lib/saxon/saxon9-sql.jar:${TOPDIR}/../lib/saxon/saxon9-unpack.jar:${TOPDIR}/../lib/saxon/saxon9pe.jar:${TOPDIR}/../lib/saxon.jar:${TOPDIR}/../lib/serializer.jar:${TOPDIR}/../lib/spellchecker/activation-1.1.jar:${TOPDIR}/../lib/spellchecker/google-api-spelling-java-1.1.jar:${TOPDIR}/../lib/spellchecker/jaxb-api-2.1.jar:${TOPDIR}/../lib/spellchecker/jaxb-impl-2.1.5.jar:${TOPDIR}/../lib/spellchecker/log4j-1.2.14.jar:${TOPDIR}/../lib/spellchecker/stax-api-1.0-2.jar:${TOPDIR}/../lib/swing-layout-1.0.jar:${TOPDIR}/../lib/Tidy.jar:${TOPDIR}/../lib/trang.jar:${TOPDIR}/../lib/xalan.jar:${TOPDIR}/../lib/xercesImpl.jar:${TOPDIR}/../lib/xmlbeans-2.6.0.jar:${TOPDIR}/../lib/xmldb.jar:${TOPDIR}/../lib/xsdlib.jar
java -classpath ${TOPDIR}/../res:${EDITIX_LIB} -Xms64m -Xmx512m ${EDITIX_MAIN} $1