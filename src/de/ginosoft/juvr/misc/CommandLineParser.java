package de.ginosoft.juvr.misc;

import java.util.ArrayList;

public class CommandLineParser {
	
	private ArrayList<String> m_args= new ArrayList<String>();
	
	public CommandLineParser(String[] args){
		for (int i=0; i<args.length;i++) {
			m_args.add(args[i]);
		}
	}
	
	public boolean containsCommand(String cmd) {
		return m_args.contains(cmd);
	}

}
