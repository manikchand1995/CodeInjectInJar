//$Id$
package org.filter.stacktrace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Default extension of {@link SData} <br>
 * User-defined filter configurations are defined here.
 * @author mani-5328
 *
 */
public interface DefaultSData extends SData{

	/**
	 * Used to return the traces other than those containing "java." 
	 * and among them, only those containing "filter"
	 */
	List<String[]> ONLY_FILTER_NO_JAVA = new ArrayList<String[]>
	(Arrays.asList(new String[]{"java"},new String[]{"filter"}));
	
}
	