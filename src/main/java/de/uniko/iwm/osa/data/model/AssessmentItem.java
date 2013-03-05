package de.uniko.iwm.osa.data.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.uniko.iwm.osa.data.service.OsaDbQuestsService;

public class AssessmentItem {

	public enum AssessmentItemType {
		INTERESSEN
	}

	final String MAGIC_INTERESSEN_TYPEVALUES = "a:5:{i:0;i:1;i:1;i:2;i:2;i:3;i:3;i:4;i:4;i:5;}";

	private int id;
	private int questid;
	private int position;
	private String shownum;
	private String showdesc;
	private String typevalues;

	private AssessmentItemType assessmentType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestid() {
		return questid;
	}

	public void setQuestid(int questid) {
		this.questid = questid;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getShownum() {
		return shownum;
	}

	public void setShownum(String shownum) {
		this.shownum = shownum;
	}

	public String getShowdesc() {
		return showdesc;
	}

	public void setShowdesc(String showdesc) {
		this.showdesc = showdesc;
	}

	public String getTypevalues() {
		return typevalues;
	}

	public void setTypevalues(String typevalues) {
		this.typevalues = typevalues;
	}

	/* ----------------------------------------- */

	public AssessmentItemType getAssessmentType() {
		return assessmentType;
	}

	public void setAssessmentType(AssessmentItemType assessmentType) {
		this.assessmentType = assessmentType;

		switch (assessmentType) {
		case INTERESSEN:
			setTypevalues(MAGIC_INTERESSEN_TYPEVALUES);
			break;

		default:
			System.out.println("ERROR unsupported assesment type: "
					+ assessmentType);
		}
	}

	/* ----------------------------------------- */

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

	@Override
	public String toString() {
		return String.format("%s-%s-%s [%s][%s][%s]", id, questid, position,
				shownum, showdesc, typevalues);
		// return String.format("%s-%s-%s [%s]", id, questid, position,
		// showdesc);
	}
}
