package de.uniko.iwm.osa.data.assessmentItem;

import java.util.List;

import de.uniko.iwm.osa.data.model.OsaDbQuests;
import de.uniko.iwm.osa.data.service.OsaDbQuestsService;

public class AssessmentItem extends Item {
	
//	public abstract int getQuestid();
//	public abstract void setQuestid(int questid);
//
//	public abstract int getPosition();
//	public abstract void setPosition(int position);
//
//	public abstract String getShownum();
//	public abstract void setShownum(String shownum);
//
//	public abstract String getShowdesc();
//	public abstract void setShowdesc(String showdesc);
//
//	public abstract String getTypevalues();
//	public abstract void setTypevalues(String typevalues);
//	
//	public AssessmentItemType getAssessmentType();
	
	/* -------------------------- */

	public int toOsaDbQuests(OsaDbQuestsService questsService) {

		List<OsaDbQuests> questById = questsService
				.getOsaDbQuestsById(new Integer(id));
		OsaDbQuests q;

		if (questById.size() == 1) {
			q = questById.get(0);

			questsService.storeOsaDbQuests(q);

			return q.getId();
		} else {
			System.out.println("--> ERROR incorrect questsdb:" + id);
		}
		return -1;
	}

}