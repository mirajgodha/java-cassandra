package com.dp.cassandra.java.client;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

/**
 * 
 * This is an implementation of a simple Java client.
 *
 */
public class CassandraConnector {

    private Cluster cluster;

    private Session session;

    public void connect(final String node, final Integer port) {

        Builder b = Cluster.builder().addContactPoint(node);

        if (port != null) {
            b.withPort(port);
        }
        cluster = b.build();

        Metadata metadata = cluster.getMetadata();
        System.out.println("Cluster name: " + metadata.getClusterName());

        for (Host host : metadata.getAllHosts()) {
        	 System.out.println("Datacenter: " + host.getDatacenter() + " Host: " + host.getAddress() + " Rack: " + host.getRack());
        }

        session = cluster.connect();
    }

    public Session getSession() {
        return this.session;
    }

    public void close() {
        session.close();
        cluster.close();
    }
}
