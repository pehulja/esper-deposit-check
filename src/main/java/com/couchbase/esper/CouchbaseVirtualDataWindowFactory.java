package com.couchbase.esper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowContext;
import com.espertech.esper.client.hook.VirtualDataWindowFactory;
import com.espertech.esper.client.hook.VirtualDataWindowFactoryContext;

/**
 * Created by David on 03/02/2015.
 */
public class CouchbaseVirtualDataWindowFactory implements VirtualDataWindowFactory {

	private static final Log log = LogFactory.getLog(CouchbaseVirtualDataWindowFactory.class);
	private Cluster cluster;
	private Bucket bucket;
	
	private String host;
	private String backetName;
	private String backetPassword;

	/**
	 * @param host
	 * @param backetName
	 * @param backetPassword
	 */
	public CouchbaseVirtualDataWindowFactory() {
		super();
		this.host = "127.0.0.1";
		this.backetName = "esper";
		this.backetPassword = "Xig2eogh";
	}

	@Override
	public void initialize(VirtualDataWindowFactoryContext virtualDataWindowFactoryContext) {
		log.debug(virtualDataWindowFactoryContext);
		cluster = CouchbaseCluster.create(this.host);
		if(this.backetPassword == null){
			bucket = cluster.openBucket(this.backetName);
		}else{
			bucket = cluster.openBucket(this.backetName, this.backetPassword);
		}
	}

	@Override
	public VirtualDataWindow create(VirtualDataWindowContext context) {
		return new CouchbaseVirtualDataWindow(bucket, context);
	}

	@Override
	public void destroyAllContextPartitions() {
		log.debug("destroyAllContextPartitions()");
	}

	@Override
	public Set<String> getUniqueKeyPropertyNames() {
		log.debug("getUniqueKeyPropertyNames()");
		return Collections.singleton("couchbaseId");
	}
}