package com.zephyrr.ftp.commands;

public enum CommandList {
	USER(new UserCommand()),
	PASS(new PassCommand()),
	ACCT(new AcctCommand()),
	CWD (new CwdCommand()),
	CDUP(new CdupCommand()), // TODO
	SMNT(new SmntCommand()), // TODO
	REIN(new ReinCommand()), // TODO
	QUIT(new QuitCommand()),
	PORT(new PortCommand()), // TODO
	PASV(new PasvCommand()),
	MODE(new ModeCommand()),
	TYPE(new TypeCommand()),
	STRU(new StruCommand()), // TODO
	ALLO(new AlloCommand()), // TODO
	REST(new RestCommand()), // TODO
	STOR(new StorCommand()),
	STOU(new StouCommand()), // TODO
	RETR(new RetrCommand()),
	LIST(new ListCommand()), // TODO: Switch current implementation to nlst
	NLST(new NlstCommand()),
	APPE(new AppeCommand()), // TODO
	RNFR(new RnfrCommand()), // TODO 
	RNTO(new RntoCommand()), // TODO
	DELE(new DeleCommand()), // TODO
	RMD (new RmdCommand()),  // TODO
	MKD (new MkdCommand()),  // TODO
	PWD (new PwdCommand()),
	ABOR(new AborCommand()), // TODO
	SYST(new SystCommand()),
	STAT(new StatCommand()), // TODO
	HELP(new HelpCommand()), // TODO
	SITE(new SiteCommand()), // TODO
	NOOP(new NoopCommand()); // TODO

	private Command c;
	CommandList(Command c) {
		this.c = c;
	}
	public Command getCommand() {
		return c;
	}
}
