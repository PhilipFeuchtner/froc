package de.uniko.iwm.osa.data.assessmentItem;

import java.util.List;

import de.uniko.iwm.osa.data.model.OsaDbQuests;
import de.uniko.iwm.osa.data.service.OsaDbQuestsService;

public class AssessmentItemType01 implements AssessmantItem {

	final String MAGIC_INTERESSEN_TYPEVALUES = "a:5:{i:0;i:1;i:1;i:2;i:2;i:3;i:3;i:4;i:4;i:5;}";

	private int id;
	private int questid;
	private int position;
	private String shownum;
	private String showdesc;
	private String typevalues = MAGIC_INTERESSEN_TYPEVALUES;

	private AssessmentItemType assessmentType;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getId()
	 */
	@Override
	public int getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#setId(int)
	 */
	@Override
	public void setId(int id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getQuestid()
	 */
	@Override
	public int getQuestid() {
		return questid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#setQuestid(int)
	 */
	@Override
	public void setQuestid(int questid) {
		this.questid = questid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getPosition()
	 */
	@Override
	public int getPosition() {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#setPosition(int)
	 */
	@Override
	public void setPosition(int position) {
		this.position = position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getShownum()
	 */
	@Override
	public String getShownum() {
		return shownum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uniko.iwm.osa.data.model.AssessmantItemI#setShownum(java.lang.String)
	 */
	@Override
	public void setShownum(String shownum) {
		this.shownum = shownum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getShowdesc()
	 */
	@Override
	public String getShowdesc() {
		return showdesc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uniko.iwm.osa.data.model.AssessmantItemI#setShowdesc(java.lang.String)
	 */
	@Override
	public void setShowdesc(String showdesc) {
		this.showdesc = showdesc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getTypevalues()
	 */
	@Override
	public String getTypevalues() {
		return typevalues;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uniko.iwm.osa.data.model.AssessmantItemI#setTypevalues(java.lang.String
	 * )
	 */
	@Override
	public void setTypevalues(String typevalues) {
		this.typevalues = typevalues;
	}

	/* ----------------------------------------- */

	public AssessmentItemType getAssessmentType() {
		return assessmentType;
	}

//	public void setAssessmentType(AssessmentItemType assessmentType) {
//		this.assessmentType = assessmentType;
//
//		setTypevalues(MAGIC_INTERESSEN_TYPEVALUES);
//	}

	/* ----------------------------------------- */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uniko.iwm.osa.data.model.AssessmantItemI#toOsaDbQuests(de.uniko.iwm
	 * .osa.data.service.OsaDbQuestsService)
	 */
	@Override
	public int toOsaDbQuests(OsaDbQuestsService questsService) {

		List<OsaDbQuests> questById = questsService
				.getOsaDbQuestsById(new Integer(id));
		OsaDbQuests q;

		if (questById.size() == 1) {
			q = questById.get(0);
			q.setShowdesc(showdesc);

			questsService.storeOsaDbQuests(q);
			// questsService.addOsaDbQuests(q);

			return q.getId();
		} else {
			System.out.println("--> ERROR incorrect questsdb:" + id);
		}
		return -1;
	}

	/* ----------------------------------------- */

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#toString()
	 */
	@Override
	public String toString() {
		return String.format("%s-%s-%s [%s][%s][%s]", id, questid, position,
				shownum, showdesc, typevalues);
		// return String.format("%s-%s-%s [%s]", id, questid, position,
		// showdesc);
	}
}
