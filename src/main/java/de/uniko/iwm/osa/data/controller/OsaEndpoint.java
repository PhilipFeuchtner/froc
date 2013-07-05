package de.uniko.iwm.osa.data.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.uniko.iwm.osa.data.model.OsaItem;

@Controller
public class OsaEndpoint {
	static Logger log = Logger.getLogger(OsaEndpoint.class.getName());
	
	@RequestMapping("/upload")
	public @ResponseBody OsaItem getResponse(@RequestHeader Map<String,Object> headers) {

		// OsaItem oi = new OsaItem("Everyone is happy!");
		OsaItem oi = new OsaItem();
		
		for (String key : headers.keySet()) {
			log.info(key + " -> " +  headers.get(key));
		}
		
		return oi;
	}
}
