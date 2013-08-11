package de.uniko.iwm.osa.data.service;

import java.util.List;

import de.uniko.iwm.osa.data.model.Cy_PageItem;
import de.uniko.iwm.osa.data.model.osaitem.OsaItem;

public interface QtiBuilderServive {

	/**
	 * writes pages to ds
	 * 
	 * @param oi
	 *            collect reports
	 * @param generatedPages
	 *            list of pages, quest & questitem
	 * @return true
	 */
	public boolean build(OsaItem oi, List<Cy_PageItem> generatedPages);

	/**
	 * links pages with forward-navigation
	 * 
	 * @param generatedPages
	 * @param jumpToPage
	 *            id where the last question directs to
	 * @return true
	 */
	public boolean setNavigation(List<Cy_PageItem> generatedPages,
			int jumpToPage, String firstMd5, String firstPagesId);

}