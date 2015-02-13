/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package research.xquery;

import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Jurgen
 */
public class ResearchXQuery {
    
    private Document xmlDocument;
    
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
   }
    
    
    
}
