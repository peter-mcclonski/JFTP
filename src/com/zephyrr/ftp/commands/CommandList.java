package com.zephyrr.ftp.commands;

public enum CommandList {
	USER(new UserCommand()),
	PASS(new PassCommand()),
	ACCT(new AcctCommand()),
	CWD (new CwdCommand()),
	CDUP(new CdupCommand()),
	SMNT(new SmntCommand()),
	REIN(new ReinCommand()),
	QUIT(new QuitCommand()),
	PORT(new PortCommand()),
	PASV(new PasvCommand()),
	MODE(new ModeCommand()),
	TYPE(new TypeCommand()),
	STRU(new StruCommand()),
	ALLO(new AlloCommand()),
	REST(new RestCommand()),
	STOR(new StorCommand()),
	STOU(new StouCommand()),
	RETR(new RetrCommand()),
	LIST(new ListCommand()),
	NLST(new NlstCommand()),
	APPE(new AppeCommand()),
	RNFR(new RnfrCommand()),
	RNTO(new RntoCommand()),
	DELE(new DeleCommand()),
	RMD (new RmdCommand()),
	MKD (new MkdCommand()),
	PWD (new PwdCommand()),
	ABOR(new AborCommand()),
	SYST(new SystCommand()),
	STAT(new StatCommand()),
	HELP(new HelpCommand()),
	SITE(new SiteCommand()),
	NOOP(new NoopCommand());

	private Command c;
	CommandList(Command c) {
		this.c = c;
	}
	public Command getCommand() {
		return c;
	}
}
