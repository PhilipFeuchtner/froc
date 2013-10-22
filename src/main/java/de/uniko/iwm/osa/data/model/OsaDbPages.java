package de.uniko.iwm.osa.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="pages")
public class OsaDbPages {
//	DROP TABLE IF EXISTS `pages`;
//	CREATE TABLE IF NOT EXISTS `pages` (
//	  `id` int(10) unsigned NOT NULL auto_increment,
//	  `pid` varchar(5) NOT NULL,
//	  `md5key` varchar(32) NOT NULL,
//	  `display` int(11) unsigned NOT NULL default '1',
//	  `login` int(11) unsigned NOT NULL default '0',
//	  `sessionpos` int(11) NOT NULL default '1',
//	  `back` int(11) unsigned NOT NULL,
//	  `forward` int(11) unsigned NOT NULL,
//	  `forwardform` varchar(150) NOT NULL,
//	  `name` varchar(250) NOT NULL,
//	  `closed` int(11) NOT NULL,
//	  PRIMARY KEY  (`id`),
//	  UNIQUE KEY `md5key` (`md5key`)
//	) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=176 ;

	@Id
	@Column(name="id")
	@GeneratedValue
	private Integer id;
	
	private String pid = "Default";
	private String md5key = "Default";
	private Integer display = 1;
	private Integer login = 1;  // TODO!
	private Integer sessionpos = 1;
	private Integer back = 0;
	private Integer forward = 0;
	private String forwardform  = "Default";
	private String name = "Default";
	private Integer closed = 1;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getMd5key() {
		return md5key;
	}
	public void setMd5key(String md5key) {
		this.md5key = md5key;
	}
	public Integer getDisplay() {
		return display;
	}
	public void setDisplay(Integer display) {
		this.display = display;
	}
	public Integer getLogin() {
		return login;
	}
	public void setLogin(Integer login) {
		this.login = login;
	}
	public Integer getSessionpos() {
		return sessionpos;
	}
	public void setSessionpos(Integer sessionpos) {
		this.sessionpos = sessionpos;
	}
	public Integer getBack() {
		return back;
	}
	public void setBack(Integer back) {
		this.back = back;
	}
	public Integer getForward() {
		return forward;
	}
	public void setForward(Integer forward) {
		this.forward = forward;
	}
	public String getForwardform() {
		return forwardform;
	}
	public void setForwardform(String forwardform) {
		this.forwardform = forwardform;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getClosed() {
		return closed;
	}
	public void setClosed(Integer closed) {
		this.closed = closed;
	}
}
