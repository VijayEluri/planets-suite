package eu.planets_project.ifr.core.wee.gui;


import org.jboss.logging.Logger;
import org.jboss.wsf.spi.management.EndpointRegistryFactory;
import org.jboss.wsf.stack.jbws.EndpointRegistryFactoryImpl;


/**
 * The servlet that is associated with context /wee-monitor
 *
 * @author rainer.schuster@researchstudio.at
 * @since 11-Jun-2007
 */
public class WeeContextServlet extends MonitoringContextServlet
{
   // provide logging
   protected final Logger log = Logger.getLogger(WeeContextServlet.class);

   protected void initServiceEndpointManager()
   {
//      ServiceEndpointManagerFactory factory = ServiceEndpointManagerFactory.getInstance();
//      epManager = factory.getServiceEndpointManager();
	   EndpointRegistryFactory factory = new EndpointRegistryFactoryImpl();
	   epManager = factory.getEndpointRegistry();
   }
}