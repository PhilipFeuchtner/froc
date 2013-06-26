package de.uniko.iwm.osa.data.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.uniko.iwm.osa.data.model.OsaItem;

@Controller
public class OsaEndpoint {

	@RequestMapping("/upload")
	public @ResponseBody OsaItem getResponse(@RequestHeader Map<String,Object> headers) {

		OsaItem oi = new OsaItem("Everyone is happy!");
		
		for (String key : headers.keySet()) {
			oi.addHeader(key, headers.get(key).toString());
		}
		
		return oi;
	}
}
