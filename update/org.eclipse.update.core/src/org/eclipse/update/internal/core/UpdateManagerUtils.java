package org.eclipse.update.internal.core;
/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.*;
import org.eclipse.update.core.SiteManager;

public class UpdateManagerUtils {

	/**
	 * return the urlString if it is a absolute URL
	 * otherwise, return the default URL if the urlString is null
	 * The defaultURL may point ot a file, create a file URL then
	 * if teh urlString or the default URL are relatives, prepend the rootURL to it
	 */
	public static URL getURL(URL rootURL, String urlString, String defaultURL) throws MalformedURLException {
		URL url = null;

		// if no URL , provide Default
		if (urlString == null || urlString.trim().equals("")) {

			// no URL, no default, return right now...
			if (defaultURL == null || defaultURL.trim().equals(""))
				return null;
			else
				urlString = defaultURL;
		}

		// URL can be relative or absolute	
		if (urlString.startsWith("/") && urlString.length() > 1)
			urlString = urlString.substring(1);
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			// the url is not an absolute URL
			// try relative
			String path = UpdateManagerUtils.getPath(rootURL);
			path = path + ((path.endsWith("/")|| path.endsWith(File.separator))?"":"/");			
			url = new URL(rootURL.getProtocol(), rootURL.getHost(), path + urlString);
		}
		return url;
	}

	/**
	 * returns a translated String
	 */
	public static String getResourceString(String infoURL, ResourceBundle bundle) {
		String result = null;
		if (infoURL != null) {
			result = UpdateManagerPlugin.getPlugin().getDescriptor().getResourceString(infoURL, bundle);
		}
		return result;
	};

	/**
	 * Resolve a URL as a local file URL
	 * if the URL is not a file URL, transfer the stream to teh temp directory 
	 * and return the new URL
	 */
	public static URL resolveAsLocal(URL remoteURL) throws MalformedURLException, IOException, CoreException {
		return resolveAsLocal(remoteURL, null);
	}

	/**
	 * Resolve a URL as a local file URL
	 * if the URL is not a file URL, transfer the stream to teh temp directory 
	 * and return the new URL
	 */
	public static URL resolveAsLocal(URL remoteURL, String localName) throws MalformedURLException, IOException, CoreException {
		URL result = remoteURL;

		if (!(remoteURL == null || remoteURL.getProtocol().equals("file"))) {
			InputStream sourceContentReferenceStream = remoteURL.openStream();
			if (sourceContentReferenceStream != null) {

				Site tempSite = (Site) SiteManager.getTempSite();
				String newFile = UpdateManagerUtils.getPath(tempSite.getURL());							
				if (localName == null || localName.trim().equals("")) {
					newFile = newFile + getLocalRandomIdentifier("");
				} else {
					newFile = newFile + localName;

				}

				result = copyToLocal(sourceContentReferenceStream, newFile);
			} else {
				throw new IOException("Couldn\'t find the file: " + remoteURL.toExternalForm());
			}
			
			// DEBUG:
			if (UpdateManagerPlugin.DEBUG && UpdateManagerPlugin.DEBUG_SHOW_INSTALL) {
				UpdateManagerPlugin.getPlugin().debug("Transfered URL:" + remoteURL.toExternalForm() + " to:" + result.toExternalForm());
			}
		}
		return result;
	}

	/**
	 * 
	 */
	public static URL copyToLocal(InputStream sourceContentReferenceStream, String localName) throws MalformedURLException, IOException {
		URL result = null;

		// create the Dir is they do not exist
		// get the path from the File to resolve File.separator..
		// do not use the String as it may contain URL like separator
		File localFile = new File(localName);
		int index = localFile.getPath().lastIndexOf(File.separator);
		if (index != -1) {
			File dir = new File(localFile.getPath().substring(0, index));
			if (!dir.exists())
				dir.mkdirs();
		}

		// transfer teh content of the File
		if (!localFile.isDirectory()) {
			FileOutputStream localContentReferenceStream = new FileOutputStream(localFile);
			transferStreams(sourceContentReferenceStream, localContentReferenceStream);
		}
		result = new URL("file", null, localFile.getPath());

		return result;
	}

	/**
	 * Returns a random fiel name for teh local system
	 */
	private static String getLocalRandomIdentifier(String remotePath) {
		int dotIndex = remotePath.lastIndexOf(".");
		int fileIndex = remotePath.lastIndexOf(File.separator);

		// if there is a separator after the dot
		// do not consider it as an extension
		// FIXME: LINUX ???
		String ext = (dotIndex != -1 && fileIndex < dotIndex) ? "." + remotePath.substring(dotIndex) : "";
		String name = (fileIndex != -1 && fileIndex < dotIndex) ? remotePath.substring(fileIndex, dotIndex) : "Eclipse_Update_TMP_";

		Date date = new Date();
		String result = name + date.getTime() + ext;

		return result;
	}

	/**
	 * This method also closes both streams.
	 * Taken from FileSystemStore
	 */
	private static void transferStreams(InputStream source, OutputStream destination) throws IOException {

		Assert.isNotNull(source);
		Assert.isNotNull(destination);

		try {
			byte[] buffer = new byte[8192];
			while (true) {
				int bytesRead = source.read(buffer);
				if (bytesRead == -1)
					break;
				destination.write(buffer, 0, bytesRead);
			}
		} finally {
			try {
				source.close();
			} catch (IOException e) {}
			try {
				destination.close();
			} catch (IOException e) {}
		}
	}

	/**
	 * remove a file or directory from the file system.
	 * used to clean up install
	 */
	public static void removeFromFileSystem(File file) {
		if (!file.exists())
			return;
		if (file.isDirectory()) {
			String[] files = file.list();
			if (files != null) // be careful since file.list() can return null
				for (int i = 0; i < files.length; ++i)
					removeFromFileSystem(new File(file, files[i]));
		}
		if (!file.delete()) {
			String id = UpdateManagerPlugin.getPlugin().getDescriptor().getUniqueIdentifier();
			IStatus status = new Status(IStatus.WARNING,id,IStatus.OK,"cannot remove: " + file.getPath()+" from the filesystem",new Exception());
			UpdateManagerPlugin.getPlugin().getLog().log(status);
		}
	}
	
	/**
	 * Method to return the PATH of the URL.
	 * The path is the file of a URL before any <code>#</code> or <code>?</code>
	 * This code removes the fragment or the query of the URL file
	 * A URL is of teh form: <code>protocol://host/path#ref</code> or <code> protocol://host/path?query</code>
	 * 
	 * @return the path of the URL
	 */
	public static String getPath(URL url){
		String result = null;
		if (url!=null){
			String file = url.getFile();
			int index;
			if ((index = (file.indexOf("#")))!=-1) file = file.substring(0,index);
			if ((index = (file.indexOf("?")))!=-1) file = file.substring(0,index);
			result = file;
		}
		return result;
	}
	

}