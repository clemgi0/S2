@echo EditiX is running...
@SET MAIN_CLASS=com.japisoft.xflows.RunScenario
@SET EDITIX_LIB=../lib/xml-apis.jar;../lib/batik/batik-anim.jar;../lib/batik/batik-awt-util.jar;../lib/batik/batik-bridge.jar;../lib/batik/batik-codec.jar;../lib/batik/batik-css.jar;../lib/batik/batik-dom.jar;../lib/batik/batik-ext.jar;../lib/batik/batik-extension.jar;../lib/batik/batik-gui-util.jar;../lib/batik/batik-gvt.jar;../lib/batik/batik-parser.jar;../lib/batik/batik-script.jar;../lib/batik/batik-svg-dom.jar;../lib/batik/batik-svggen.jar;../lib/batik/batik-swing.jar;../lib/batik/batik-transcoder.jar;../lib/batik/batik-util.jar;../lib/batik/batik-xml.jar;../lib/batik/js.jar;../lib/berkley/db.jar;../lib/berkley/dbxml.jar;../lib/commons-collections-3.2.jar;../lib/commons-collections-testframework-3.2.jar;../lib/commons-httpclient-3.1.jar;../lib/commons-logging-1.1.1-javadoc.jar;../lib/commons-logging-1.1.1-sources.jar;../lib/commons-logging-1.1.1.jar;../lib/commons-logging-adapters-1.1.1.jar;../lib/commons-logging-api-1.1.1.jar;../lib/commons-logging-tests.jar;../lib/commons-net-3.3.jar;../lib/cssparser-0.9.5.jar;../lib/editix.jar;../lib/edtftpj-1.4.2.jar;../lib/fop/avalon-framework-impl-4.3.1.jar;../lib/fop/avalon-framework-api-4.3.1.jar;../lib/fop/batik-all-1.10.jar;../lib/fop/commons-io-1.3.1.jar;../lib/fop/commons-logging-1.0.4.jar;../lib/fop/fontbox-2.0.7.jar;../lib/fop/fop.jar;../lib/fop/serializer-2.7.2.jar;../lib/fop/xalan-2.7.2.jar;../lib/fop/xml-apis-ext-1.3.04.jar;../lib/fop/xmlgraphics-commons-2.3.jar;../lib/htmlparser-1.4.jar;../lib/isorelax.jar;../lib/jakarta-oro-2.0.8.jar;../lib/jakarta-regexp-1.2.jar;../lib/jdom.jar;../lib/json.jar;../lib/msv.jar;../lib/poi-3.12-20150511.jar;../lib/poi-ooxml-3.12-20150511.jar;../lib/poi-ooxml-schemas-3.12-20150511.jar;../lib/poi-scratchpad-3.12-20150511.jar;../lib/relaxngDatatype.jar;../lib/resolver.jar;../lib/saxon/saxon-license.lic;../lib/saxon/saxon9-sql.jar;../lib/saxon/saxon9-unpack.jar;../lib/saxon/saxon9pe.jar;../lib/saxon.jar;../lib/serializer.jar;../lib/swing-layout-1.0.jar;../lib/Tidy.jar;../lib/trang.jar;../lib/xalan.jar;../lib/xercesImpl.jar;../lib/xmlbeans-2.6.0.jar;../lib/xmldb.jar;../lib/xsdlib.jar

@java -classpath ../res;%EDITIX_LIB% -Xms64m -Xmx1024m %MAIN_CLASS% %1