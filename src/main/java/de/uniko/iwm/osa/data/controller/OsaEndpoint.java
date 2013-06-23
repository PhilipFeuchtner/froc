package de.uniko.iwm.osa.data.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.uniko.iwm.osa.data.model.OsaItem;

@Controller
public class OsaEndpoint {

	@RequestMapping("/upload")
	public @ResponseBody OsaItem getResponse() {

		OsaItem oi = new OsaItem("Everyone is happy!");

		return oi;
	}
}
