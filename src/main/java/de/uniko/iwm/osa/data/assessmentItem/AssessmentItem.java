package de.uniko.iwm.osa.data.assessmentItem;

/**
 * @author user
 * 
 *         interface connection assessmentitem to osadbquest
 * 
 *         most work done in constructor, intentionally left undefined
 * 
 *         constructor take an itemconfigurator as parameter
 * 
 */
public interface AssessmentItem {
	
	/**
	 * question or page?
	 * 
	 * unused
	 */
	static enum CYQUEST_QUESTION_TYPE {
		QESTION, EXTRASEITE
	};
	
	String[] quest_ans_def = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L" };
	
	/**
	 * @return identifier
	 */
	// public int getIdentifier();

	/**
	 * get cyquest-question-id
	 * 
	 * unused
	 * 
	 * @return cyquest-cuestion-id
	 */
//	public String getCqt();

	/**
	 * get corresponding osadbquest
	 * 
	 * unused
	 * 
	 * @return osadbquest
	 */
	// public OsaDbQuests getOsaDbQuest();
	
	public int getItemPerPage();
	
	// public void setTitle(String title);
	// public String getTitle();
}
