//$Id$
package org.filter.stacktrace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import java.util.List;

/**
 * Contains default filter conditions that can be applied for stack trace filter.
 * Users can also create their own by extending this interface.
 * <br><br>
 * Example:<br>
 * List&lt;String[]&gt; SAMPLE = new ArrayList&lt;String[]&gt;
	(Arrays.asList(new String[]{&lt;FOR_EXCLUSION&gt;},new String[]{&lt;FOR_INCLUSION&gt;}));
	<br><br>
	where, SAMPLE.get(<b>0</b>) contains the array of strings that is considered for <b><code>exclusion</code></b> of traces; or <b><code>null</code></b> if nothing to exclude
	<br>SAMPLE.get(<b>1</b>) contains the array of strings that is considered for <b><code>inclusion</code></b> of traces; or <b><code>null</code></b> if nothing to include
 * @author mani-5328
 *
 */
interface SData {
	
	final String EX_PACKAGE = "exclude_package";

	final String IN_PACKAGE = "include_package";
	
	final String EX_CLASS = "exclude_class";
	
	final String IN_CLASS = "include_class";

	/**
	 * Used to return <b>all</b> the traces 
	 */
	List<String[]> ALL = new ArrayList<String[]>
	(Arrays.asList(new String[]{},new String[]{}));
	
	/**
	 * Used to return only the traces starting with <b>"com.zoho."</b> and <b>"com.adventnet."</b>
	 */
	List<String[]> ONLY_ZOHO = new ArrayList<String[]>
	(Arrays.asList(new String[]{},new String[]{"com.zoho.","com.adventnet."}));
	
	/**
	 * Used to return the traces other than those starting with <b>"java."</b>
	 */
	List<String[]> NO_JAVA = new ArrayList<String[]>
	(Arrays.asList(new String[]{"java."},new String[]{}));
	
	/**
	 * Used to return the traces other than those starting with <b>"java.util."</b>
	 */
	List<String[]> NO_JAVA_UTIL = new ArrayList<String[]>
	(Arrays.asList(new String[]{"java.util."},new String[]{}));
	
	/**
	 * Used to return the traces other than those starting with <b>"java.lang."</b>
	 */
	List<String[]> NO_JAVA_LANG = new ArrayList<String[]>
	(Arrays.asList(new String[]{"java.lang."},new String[]{}));
	
	/**
	 * Used to return the traces other than those starting with <b>"javax.xml."</b>
	 */
	List<String[]> NO_JAVAX_XML = new ArrayList<String[]>
	(Arrays.asList(new String[]{"javax.xml."},new String[]{}));
	
	/**
	 * Used to return the traces other than those starting with <b>"org.w3c."</b>
	 */
	List<String[]> NO_W3C = new ArrayList<String[]>
	(Arrays.asList(new String[]{"org.w3c."},new String[]{}));
	
	/**
	 * Used to return the traces other than those starting with <b>"org.xml."</b>
	 */
	List<String[]> NO_ORG_XML = new ArrayList<String[]>
	(Arrays.asList(new String[]{"org.xml."},new String[]{}));
	
	/**
	 * Used to return the traces other than those starting with <b>"org.apache."</b>
	 */
	List<String[]> NO_APACHE = new ArrayList<String[]>
	(Arrays.asList(new String[]{"org.apache."},new String[]{}));
	
	/**
	 * Used to return only the traces starting with <b>"java."</b>
	 */
	List<String[]> ONLY_JAVA = new ArrayList<String[]>
	(Arrays.asList(null,new String[]{"java."}));
	
	/**
	 * Used to return the traces other than those starting with <b>"javax.servlet."</b>
	 */
	List<String[]> NO_JAVAX_SERVLET = new ArrayList<String[]>
	(Arrays.asList(new String[]{"javax.servlet."},null));
	
	/**
	 * Used to return only the <b>first</b> trace 
	 */
	List<String[]> ONLY_MAIN = new ArrayList<String[]>
	(Arrays.asList(new String[]{""},null));
	
//	Map<String,String[]> MAP = new HashMap<String,String[]>(){{
//	    put(EX_PACKAGE,new String[]{"javax.servlet."}); put(IN_PACKAGE,new String[]{"javax.servlet."});
//	}};
	
}
