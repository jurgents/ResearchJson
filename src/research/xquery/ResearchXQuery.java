/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package research.xquery;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
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
        
        String expressionReport = "/Employees/Employee[@emplid='3333']/email";
 
        NamespaceContext namespaceContextNgrMetadata = new NamespaceContext() {

            @Override
            public String getNamespaceURI(String prefix) {
                throw new UnsupportedOperationException("Not supported yet."); 
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
        //read a string value
        String report = xPath.compile(expressionReport).evaluate(xmlDocument);

        //read an xml node using xpath
        Node node = (Node) xPath.compile(expressionReport).evaluate(xmlDocument, XPathConstants.NODE);

        //read a nodelist using xpath
        NodeList nodeList = (NodeList) xPath.compile(expressionReport).evaluate(xmlDocument, XPathConstants.NODESET);
        
        return false;
    }
  
    
    
    
}
