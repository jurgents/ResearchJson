/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package research.xquery;

import javax.xml.xpath.XPathExpressionException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jurgen
 */
public class ResearchXQueryTest {
    
    public ResearchXQueryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testExistsAReport() throws XPathExpressionException {
        ResearchXQuery researchXQuery = new ResearchXQuery();
        // TODO review the generated test code and remove the default call to fail.
        assertEquals(2, researchXQuery.numberOfReports());
    }
    
}
