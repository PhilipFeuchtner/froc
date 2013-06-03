package de.uniko.iwm.osa.data.assessmentItem;

import java.util.List;

import net.sf.saxon.s9api.XdmNode;

import de.uniko.iwm.osa.data.model.OsaDbQuests;
import de.uniko.iwm.osa.data.service.OsaDbQuestsService;
import de.uniko.iwm.osa.qtiinterpreter.Parse.ItemConigurator;

public class AssessmentItem_Type001 implements AssessmentItem {

	final String MAGIC_INTERESSEN_TYPEVALUES = "a:5:{i:0;i:1;i:1;i:2;i:2;i:3;i:3;i:4;i:4;i:5;}";

	private Integer id;
	private Integer questid;
	private int position;
	private String shownum;
	private String showdesc;
	private String typevalues = MAGIC_INTERESSEN_TYPEVALUES;
	
	ItemConigurator ic = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getQuestid()
	 */
	
	public AssessmentItem_Type001(ItemConigurator ic) {
		this.ic = ic;
		showdesc = ic.queryShowdescr();
	}
	
	public Integer getQuestid() {
		return questid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#setQuestid(int)
	 */
	public void setQuestid(Integer questid) {
		this.questid = questid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getPosition()
	 */
	
	public int getPosition() {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#setPosition(int)
	 */
	
	public void setPosition(int position) {
		this.position = position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getShownum()
	 */
	
	public String getShownum() {
		return shownum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uniko.iwm.osa.data.model.AssessmantItemI#setShownum(java.lang.String)
	 */

	public void setShownum(String shownum) {
		this.shownum = shownum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getShowdesc()
	 */
	
	public String getShowdesc() {
		return showdesc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uniko.iwm.osa.data.model.AssessmantItemI#setShowdesc(java.lang.String)
	 */
	
	public void setShowdesc(String showdesc) {
		this.showdesc = showdesc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getTypevalues()
	 */

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
	
	public void setTypevalues(String typevalues) {
		this.typevalues = typevalues;
	}
	
	//public ItemType getAssessmentType() {
	//	return ItemType.INTERESSEN;
	//}

	/* ----------------------------------------- */



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

	@Override
	public boolean init(String identifier, String cyquest_question_type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean create(XdmNode assecssmentItem) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setSequenceValues(int count, int cy_position) {
		// TODO Auto-generated method stub
		return false;
	}
}
