package nl.pdok.datamanagement.executor;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import nl.pdok.datamanagement.factory.NgrRequestFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.XPath;

public class NgrRequestExecutor {

    private static final Logger LOGGER = Logger.getLogger(NgrRequestExecutor.class);

    private HttpClient client;

    private NgrRequestFactory requestBuilder;

    public NgrRequestExecutor(String url, String username, String password) {

        this.client = HttpClientBuilder.create().useSystemProperties().build();
        this.requestBuilder = new NgrRequestFactory(url, username, password);
    }

    public boolean setTemporalExtentValidToDate(String stringid, Date date) {
        if (!this.login()) {
            LOGGER.trace("Ngr - login failed: stringid=" + stringid
                + " date=" + (date == null ? "" : date.toString()));
            return false;
        }

        long id = this.getIdForUUID(stringid);

        HttpPost post = null;

        try {
            Document metadata = this.getMetadata(stringid);
            // update date

            Map<String, String> uris = new HashMap<String, String>();
            uris.put("gmd", "http://www.isotc211.org/2005/gmd");
            uris.put("gmx", "http://www.isotc211.org/2005/gmx");
            uris.put("srv", "http://www.isotc211.org/2005/srv");
            uris.put("gml", "http://www.opengis.net/gml");
            uris.put("xs", "http://www.w3.org/2001/XMLSchema");
            uris.put("xsi", "http://www.w3.org/2001/XMLSchema-instance");
            uris.put("csw", "http://www.opengis.net/cat/csw/2.0.2");
            uris.put("gts", "http://www.isotc211.org/2005/gts");
            uris.put("gco", "http://www.isotc211.org/2005/gco");
            uris.put("gsr", "http://www.isotc211.org/2005/gsr");
            uris.put("xlink", "http://www.w3.org/1999/xlink");
            uris.put("geonet", "http://www.fao.org/geonetwork");

            Node timepositionEndNode = getTimepositionEndNode(metadata, uris);

            if (timepositionEndNode == null) {
                LOGGER.error("No node found for metadata");
                return false;
            }

            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            timepositionEndNode.setText(formatter.format(date));
            
            // post metadata
            post = this.requestBuilder.createUpdateMetadataRequest(id, metadata);

            HttpResponse response = this.client.execute(post);

            int status = response.getStatusLine().getStatusCode();
            LOGGER.trace("HttpStatus update-request Metadata: " + status);

            return (status == HttpStatus.SC_OK);

        } catch (Exception e) {
            LOGGER.error("TemporalExtentValidToDate cannot be set for (uuid, id) = (" + stringid + ", " + id + ")", e);
            return false;
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }
    }

	private Node getTimepositionEndNode(Document metadata,
			Map<String, String> uris) {
		XPath query = metadata.createXPath("//gmd:MD_Metadata/gmd:identificationInfo/srv:SV_ServiceIdentification/srv:extent/gmd:EX_Extent/gmd:temporalElement/gmd:EX_TemporalExtent/gmd:extent/gml:TimePeriod/gml:end/gml:TimeInstant/gml:timePosition");
		query.setNamespaceURIs(uris);
		Node node = query.selectSingleNode(metadata);
		return node;
	}
	
	

    private Document getMetadata(String stringid) throws ClientProtocolException, IOException, DocumentException {
        HttpGet get = this.requestBuilder.createMetadataRequest(stringid);
        LOGGER.trace("Metadata request (get): " + get);

        try {
            ResponseHandler<String> parser = new BasicResponseHandler();
            String response = this.client.execute(get, parser);
            Document document = DocumentHelper.parseText(response);

            return document;

        } finally {
            // Release current connection to the connection pool
            // once you are done
            get.releaseConnection();
        }
    }

    private long getIdForUUID(String stringid) {
        HttpGet get = this.requestBuilder.createSearchRequest(stringid);

        try {
            ResponseHandler<String> parser = new BasicResponseHandler();

            String response = this.client.execute(get, parser);

            Document document = DocumentHelper.parseText(response);

            Map<String, String> uris = new HashMap<String, String>();
            uris.put("geonet", "http://www.fao.org/geonetwork");

            XPath query = document.createXPath("//response/metadata/geonet:info/id");
            query.setNamespaceURIs(uris);
            Node node = query.selectSingleNode(document);

            if (node == null) {
                LOGGER.error("Node in metadata not found");
                LOGGER.error("HttpGet statement: " + get == null ? null : get.toString());
                LOGGER.error("Response: " + response);

                return -1;
            }

            return Long.parseLong(node.getText());

        } catch (Exception e) {
            LOGGER.error("Id for UUID could not be determined, HttpGet statement: " + (get == null ? null : get.toString()), e);
            return -1;

        } finally {
            // Release current connection to the connection pool
            // once you are done
            get.releaseConnection();
        }
    }

    private boolean login() {
        HttpPost post = null;
        try {
            post = this.requestBuilder.createLoginRequest();

            // Send login request**
            HttpResponse response = this.client.execute(post);

            int status = response.getStatusLine().getStatusCode();
            LOGGER.trace("Status login-request: " + status);

            return (status == HttpStatus.SC_OK);

        } catch (Exception ex) {
            LOGGER.error("Problem with login NGR: ", ex);
            return false;

        } finally {
            // Release current connection to the connection pool
            // once you are done
            if (post != null) {
                post.releaseConnection();
            }
        }

    }

}
