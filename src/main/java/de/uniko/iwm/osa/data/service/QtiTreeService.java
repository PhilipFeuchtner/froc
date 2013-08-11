package de.uniko.iwm.osa.data.service;

import java.util.List;

import de.uniko.iwm.osa.data.model.osaitem.OsaItem;

public interface QtiTreeService {

	/**
	 * scan database: - scan pages to remove - quest - questitems
	 * 
	 * and removes them all
	 * 
	 * @param startPage
	 *            pid of the first page to remove
	 * @param oi
	 *            report item
	 * @return returns success/failure
	 */
	public int scanDatabase(int startPage, OsaItem oi);

	public List<Integer> getPages2remove();

	public List<Integer> getQuests2remove();

	public List<Integer> getQuestitems2remove();

	public String getFirstMd5();

}