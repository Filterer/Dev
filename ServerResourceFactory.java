package com.remainsoftware.td.tracker.ui;

import java.util.List;

import com.remainsoftware.td.tracker.model.IServer;
import com.remainsoftware.td.tracker.model.IServerFactory;

import nl.remain.td.core.ui.provider.ITDResource;
import nl.remain.td.core.ui.provider.ITDResourceFactory;

/**
 * This class serves as a center staging point for all the services that
 * implement the {@link IServerFactory} interface which represents servers that
 * are also object management systems outside of TD/OMS. The servers that are
 * returned from each factory are being wrapped up under the {@link ITDResource}
 * interface in order to be displayed in the TD/OMS tree.
 * 
 * @author gkine
 * 
 */
public class ServerResourceFactory implements ITDResourceFactory {

	@Override
	public void addResources(ITDResource pParent, List<ITDResource> pChildren) {

		for (IServerFactory serverFactory : TrackerUtils.getServerFactories()) {

			List<IServer> servers = serverFactory.getServers();
			if (servers.size() == 1) {
				IServer server = servers.get(0);
				server.setFilterDirectory(TrackerUtils.getFilterDirectory(pParent, serverFactory));

				pChildren.add(TrackerUtils.createServerNode(server, pParent, true));
			} else if (servers.size() > 1) {
				ITDResource serverFactoryFolder = TrackerUtils.createServerFactoryNode(serverFactory, pParent,
						true);
				serverFactoryFolder.addChildProvider(ServerFactoryChildProvider.INSTANCE);
				serverFactoryFolder.setWeight(ITDResource.W_VERY_LIGHT - 10);

				pChildren.add(serverFactoryFolder);
			}
		}
	}

}
