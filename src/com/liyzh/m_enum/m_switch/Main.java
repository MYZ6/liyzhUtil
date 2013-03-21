package com.liyzh.m_enum.m_switch;

public class Main {
	public static void main(String[] args) {
		Main.setTimeStr("10");
	}

	public static void setTimeStr(String phase) {
		switch (Phase.getPhase("phase" + phase)) {
		case phase05:
			System.out.println("sdlkfjsldkf");
			break;
		case phase10:
			System.out.println("sdl24234234kfjsldkf");
			break;
		}
	}
}

enum Phase {
	phase05, phase10, phase20, phase30, phase40, phase50, phase60;

	public static Phase getPhase(String phase) {
		return valueOf(phase);
	}
}