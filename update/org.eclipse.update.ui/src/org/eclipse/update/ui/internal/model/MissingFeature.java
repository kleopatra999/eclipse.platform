package org.eclipse.update.ui.internal.model;

import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.update.core.INonPluginEntry;
import org.eclipse.update.core.IFeature;
import org.eclipse.update.core.IImport;
import org.eclipse.update.core.IURLEntry; 
import org.eclipse.update.core.IPluginEntry;
import org.eclipse.update.core.ISite;
import org.eclipse.update.core.VersionedIdentifier;
import org.eclipse.update.core.*;
import org.eclipse.update.core.*;

public class MissingFeature implements IFeature {
	private URL url;
	private ISite site;
	private VersionedIdentifier id = new VersionedIdentifier("unknown", "0.0.0");
	public MissingFeature(ISite site, URL url) {
		this.url = url;
	}

	/*
	 * @see IFeature#getIdentifier()
	 */
	public VersionedIdentifier getIdentifier() {
		return id;
	}

	/*
	 * @see IFeature#getSite()
	 */
	public ISite getSite() {
		return site;
	}

	/*
	 * @see IFeature#getLabel()
	 */
	public String getLabel() {
		return url.toString();
	}

	/*
	 * @see IFeature#getURL()
	 */
	public URL getURL() {
		return url;
	}

	/*
	 * @see IFeature#getUpdateInfo()
	 */
	public IURLEntry getUpdateSiteEntry() {
		return null;
	}

	/*
	 * @see IFeature#getDiscoveryInfos()
	 */
	public IURLEntry[] getDiscoverySiteEntries() {
		return null;
	}

	/*
	 * @see IFeature#getProvider()
	 */
	public String getProvider() {
		return "Unknown";
	}

	/*
	 * @see IFeature#getDescription()
	 */
	public IURLEntry getDescription() {
		return null;
	}

	/*
	 * @see IFeature#getCopyright()
	 */
	public IURLEntry getCopyright() {
		return null;
	}

	/*
	 * @see IFeature#getLicense()
	 */
	public IURLEntry getLicense() {
		return null;
	}

	/*
	 * @see IFeature#getOS()
	 */
	public String getOS() {
		return null;
	}

	/*
	 * @see IFeature#getWS()
	 */
	public String getWS() {
		return null;
	}

	/*
	 * @see IFeature#getNL()
	 */
	public String getNL() {
		return null;
	}

	/*
	 * @see IFeature#getImage()
	 */
	public URL getImage() {
		return null;
	}

	/*
	 * @see IFeature#getImports()
	 */
	public IImport[] getImports() {
		return null;
	}

	/*
	 * @see IFeature#isExecutable()
	 */
	public boolean isExecutable() {
		return false;
	}

	/*
	 * @see IFeature#isInstallable()
	 */
	public boolean isInstallable() {
		return false;
	}

	/*
	 * @see IFeature#getArchives()
	 */
	public String[] getArchives() {
		return null;
	}

	/*
	 * @see IFeature#getDataEntries()
	 */
	public INonPluginEntry[] getNonPluginEntries() {
		return null;
	}

	/*
	 * @see IFeature#addDataEntry(IDataEntry)
	 */
	public void addNonPluginEntry(INonPluginEntry dataEntry) {
	}

	/*
	 * @see IFeature#getDownloadSize(ISite)
	 */
	public long getDownloadSize(ISite site) throws CoreException {
		return 0;
	}

	/*
	 * @see IFeature#getInstallSize(ISite)
	 */
	public long getInstallSize(ISite site) throws CoreException {
		return 0;
	}

	/*
	 * @see IFeature#getApplication()
	 */
	public String getApplication() {
		return null;
	}

	/*
	 * @see IPluginContainer#getPluginEntries()
	 */
	public IPluginEntry[] getPluginEntries() {
		return null;
	}

	/*
	 * @see IPluginContainer#getPluginEntryCount()
	 */
	public int getPluginEntryCount() {
		return 0;
	}

	/*
	 * @see IPluginContainer#getDownloadSize(IPluginEntry)
	 */
	public long getDownloadSize(IPluginEntry entry) {
		return 0;
	}

	/*
	 * @see IPluginContainer#getInstallSize(IPluginEntry)
	 */
	public long getInstallSize(IPluginEntry entry) {
		return 0;
	}

	/*
	 * @see IPluginContainer#addPluginEntry(IPluginEntry)
	 */
	public void addPluginEntry(IPluginEntry pluginEntry) {
	}

	/*
	 * @see IPluginContainer#store(IPluginEntry, String, InputStream)
	 */
	public void store(IPluginEntry entry, String name, InputStream inStream)
		throws CoreException {
	}

	/*
	 * @see IAdaptable#getAdapter(Class)
	 */
	public Object getAdapter(Class adapter) {
		return null;
	}

	/*
	 * @see IPluginContainer#remove(IPluginEntry)
	 */
	public void remove(IPluginEntry entry) throws CoreException {
	}

	/*
	 * @see IFeature#setFeatureContentProvider(IFeatureContentProvider)
	 */
	public void setFeatureContentProvider(IFeatureContentProvider featureContentProvider) {
	}

	/*
	 * @see IFeature#setContentConsumer(IContentConsumer)
	 */
	public void setContentConsumer(IContentConsumer contentConsumer) {
	}

	/*
	 * @see IFeature#getContentConsumer()
	 */
	public IContentConsumer getContentConsumer() throws CoreException {
		return null;
	}

}

