package eu.planets_project.services.utils.cli;

import eu.planets_project.services.datatypes.MigrationPath;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * TODO abr forgot to document this class
 */
public class CliMigrationPaths {

    private List<CliMigrationPath> paths;

    private CliMigrationPaths() {
        paths = new ArrayList<CliMigrationPath>();
    }

    private boolean add(CliMigrationPath cliMigrationPath) {
        return paths.add(cliMigrationPath);
    }

    protected static File defaultPath = new File(".");

    public static CliMigrationPaths initialiseFromFile(String resourceName) throws ParserConfigurationException, IOException, SAXException, URISyntaxException {


        InputStream instream;


        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL resourceURL = loader.getResource(resourceName);
        if (new File(defaultPath, resourceName).isFile()) {
            instream = new FileInputStream(new File(defaultPath, resourceName));
        } else if (new File(resourceName).isFile()) {
            instream = new FileInputStream(new File(resourceName));
        } else if (resourceURL != null) {
            instream = resourceURL.openStream();
        } else {
            String msg = String.format("Could not locate resource %s",
                    resourceName);
            throw new FileNotFoundException(msg);
        }


        CliMigrationPaths paths = new CliMigrationPaths();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document doc = builder.parse(instream);


        Element fileformats = doc.getDocumentElement();
        if (fileformats != null){
            NodeList children = fileformats.getChildNodes();
            for (int i = 0; i<children.getLength();i++){
                Node child = children.item(i);
                if (child.getNodeType() == Node.ELEMENT_NODE){
                    if (child.getNodeName().equals("path")){
                        CliMigrationPath pathdef = decodePathNode(child);
                        paths.add(pathdef);
                    }                
                }
            }
        }
        return paths;
    }

    public String findMigrationCommand(URI in, URI out){
        for (CliMigrationPath path: paths){
            if (path.getIn().contains(in) && path.getOut().contains(out)){
                return path.getTool();
            }
        }
        return null;
    }

    private static CliMigrationPath decodePathNode(Node path) throws URISyntaxException {
        NodeList children = path.getChildNodes();
        Set<URI> froms = null;
        Set<URI> tos = null;
        String command = null;
        for (int i = 0; i<children.getLength();i++){
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE){

                if (child.getNodeName().equals("from")){
                    froms = decodeFromOrToNode(child);
                }
                if (child.getNodeName().equals("to")){
                    tos = decodeFromOrToNode(child);
                }
                if (child.getNodeName().equals("command")){
                    command = decodeCommandNode(child);
                }

            }
        }
        return new CliMigrationPath(froms,tos,command);

    }

    private static Set<URI> decodeFromOrToNode(Node urilist) throws URISyntaxException {
        NodeList children = urilist.getChildNodes();
        Set<URI> uris = new HashSet<URI>();
        for (int i = 0; i<children.getLength();i++){
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE){
                if (child.getNodeName().equals("uri")){
                    URI uri = decodeURI(child);
                    uris.add(uri);
                }
            }
        }
        return uris;
    }

    private static URI decodeURI(Node uri) throws URISyntaxException {
        NamedNodeMap attrs = uri.getAttributes();


        Node item = attrs.getNamedItem("value");
        String urivalue = item.getNodeValue();
        return new URI(urivalue);
    }


    private static String decodeCommandNode(Node command){
        Node commandtext = command.getFirstChild();
        if (commandtext.getNodeType() == Node.TEXT_NODE){
            return commandtext.getNodeValue();
        }
        return "";
    }

    public MigrationPath[] getAsPlanetsPaths(){


        List<MigrationPath> planetspaths = new ArrayList<MigrationPath>();
        int i=0;
        for (CliMigrationPath mypath: paths){
            planetspaths.addAll(
                    Arrays.asList(
                            MigrationPath.constructPaths(
                                    mypath.getIn(),
                                    mypath.getOut()
                            )
                    )
            );
        }
        return planetspaths.toArray(new MigrationPath[0]);

    }



}
