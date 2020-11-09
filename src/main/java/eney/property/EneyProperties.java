package eney.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="eney")
public class EneyProperties {
	
	private PortalProperties portal;

	public PortalProperties getPortal() {
		return portal;
	}

	public void setPortal(PortalProperties portal) {
		this.portal = portal;
	}
	
}
