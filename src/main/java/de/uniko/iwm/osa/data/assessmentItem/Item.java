package de.uniko.iwm.osa.data.assessmentItem;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import de.uniko.iwm.osa.data.model.OsaDbQuests;
import de.uniko.iwm.osa.data.service.OsaDbQuestsService;

public class Item {
	public enum ItemType {
		INTERESSEN, EXTRASEITE
	}

	
	@Resource(name="keyword2cyquest")
	private HashMap<String, String> questTypMap;
	
	private Integer id;
	
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}

	public Integer getQuestid() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setQuestid(Integer questid) {
		// TODO Auto-generated method stub
		
	}

	public ItemType getAssessmentType() {
		// TODO Auto-generated method stub
		return null;
	}
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