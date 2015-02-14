/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package research.xquery;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Jurgen
 */
public class ResearchXQuery {
    
    private Document xmlDocument;
    private XPath xPath;
    
    public ResearchXQuery(){ 
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        DocumentBuilder builder = null;
  
        try {
            builder = builderFactory.newDocumentBuilder();
            xmlDocument = builder.parse(
                        new FileInputStream
                                ("/Users/Jurgen/GitRepos/ResearchJurgen/resources/top10nlv2_WMS.xml")
                            );
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();  
        }
        
        xPath =  XPathFactory.newInstance().newXPath();
   }
    
    public boolean existsAReport() throws XPathExpressionException{
        
        
 
        NamespaceContext namespaceContextNgrMetadata = new NamespaceContext() {

            @Override
            public String getNamespaceURI(String prefix) {
              if (prefix == null) throw new NullPointerException("Null prefix");
              else if ("gmd".equals(prefix)) return "http://www.isotc211.org/2005/gmd";
              else if ("gmx".equals(prefix)) return "http://www.isotc211.org/2005/gmx";
              else if ("srv".equals(prefix)) return "http://www.isotc211.org/2005/srv";
              else if ("gml".equals(prefix)) return "http://www.opengis.net/gml";
              else if ("xs".equals(prefix)) return XMLConstants.W3C_XML_SCHEMA_NS_URI;
              else if ("xsi".equals(prefix)) return XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI;
              else if ("csw".equals(prefix)) return "http://www.opengis.net/cat/csw/2.0.2";
              else if ("gts".equals(prefix)) return "http://www.isotc211.org/2005/gts";
              else if ("gco".equals(prefix)) return "http://www.isotc211.org/2005/gco";
              else if ("gsr".equals(prefix)) return "http://www.isotc211.org/2005/gsr";
              else if ("xlink".equals(prefix)) return "http://www.w3.org/1999/xlink";
              else if ("geonet".equals(prefix)) return "http://www.fao.org/geonetwork";
              else if ("xml".equals(prefix)) return XMLConstants.XML_NS_URI;
              return XMLConstants.NULL_NS_URI; 
            }

            @Override
            public String getPrefix(String namespaceURI) {
                throw new UnsupportedOperationException("Not supported"); 
            }

            @Override
            public Iterator getPrefixes(String namespaceURI) {
                throw new UnsupportedOperationException("Not supported"); 
            }
        };
        
        // xPath.s
        xPath.setNamespaceContext(namespaceContextNgrMetadata);
  
 
        String expressionReport = "//gmd:MD_Metadata/gmd:identificationInfo/srv:SV_ServiceIdentification/srv:extent/gmd:EX_Extent/gmd:temporalElement/gmd:EX_TemporalExtent/gmd:extent/gml:TimePeriod/gml:end/gml:TimeInstant/gml:timePosition";
        //read a string value 

        String report = xPath.compile(expressionReport).evaluate(xmlDocument);

       
        
        //read an xml node using xpath
        Node node = (Node) xPath.compile(expressionReport).evaluate(xmlDocument, XPathConstants.NODE);

        //read a nodelist using xpath
        NodeList nodeList = (NodeList) xPath.compile(expressionReport).evaluate(xmlDocument, XPathConstants.NODESET);
        
        return false;
    }
  
    
    
    
}
