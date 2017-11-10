//$Id$
package org.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class StackFilterData {

	private Properties fontProps;
	private List<String[]> DEFAULT;
	private String defaultFilter;
	private List<String> filters;

	public StackFilterData() 
	{
		try
		{
			fontProps = new Properties();
			fontProps.load(new FileInputStream(System.getProperty("server.home")+ File.separator+"conf" +File.separator+"stackfilter.properties"));
			loadFilterNames();
			setDefault();
		}
		catch(Exception e)
		{
			System.err.println("Error while loading StackFilter.properties");
			e.printStackTrace();
		}
	}
	private void loadFilterNames()
	{
		if(!fontProps.containsKey("available_filters"))
		{throw new RuntimeException("no filters available in stacktrace.properties");}
		filters=Arrays.asList(fontProps.getProperty("available_filters").split(","));
	}
	private void setDefault()
	{
		if(!fontProps.containsKey("default_filter"))
		{throw new RuntimeException("no default filter is configured in stacktrace.properties");}
		defaultFilter = fontProps.getProperty("default_filter").trim();
		if(!filters.contains(defaultFilter))
		{throw new RuntimeException("please configure the default filter as available_filters in stacktrace.properties");}
		DEFAULT = getFilterData(defaultFilter);
	}
	public void setAsDefault(String filterKey)
	{
		if(!filters.contains(filterKey))
		{throw new RuntimeException("the given filter "+filterKey+" is not available in stacktrace.properties");}
		DEFAULT = getFilterData(filterKey);
	}
	public List<String[]> getDefault()
	{
		return DEFAULT;
	}
	public void resetDefaultFilter()
	{
		DEFAULT = getFilterData(defaultFilter);
	}	
	public String getDefaultName()
	{
		return defaultFilter;
	}
	private List<String[]> getFilterData(String filterKey)
	{
		String excludeString = fontProps.containsKey(filterKey+".exclude")?fontProps.getProperty(filterKey+".exclude").trim() : null;
		String includeString = fontProps.containsKey(filterKey+".include")?fontProps.getProperty(filterKey+".include").trim() : "*";
		String[] exclude = splitArray(excludeString,"exclude");
		String[] include = splitArray(includeString,"include");
		return new ArrayList<String[]>(Arrays.asList(exclude,include));
	}
	private String[] splitArray(String str,String type)
	{
		if(str == null)
		{ return null;}
		else if(str.equalsIgnoreCase("*"))
		{
			return new String[]{""};
		}
		else
		{
			return str.split(",");
		}
	}
	public List<String> getFilterNames()
	{
		return filters;
	}
}
