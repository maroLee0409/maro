package com.global.action;

import java.lang.reflect.Constructor;

public class ActionFactory {
	public static Action createActionInstance(String className) {
		try {
			Class<?> clazz = Class.forName(className.trim());
			Constructor<?> constructor = clazz.getConstructor();
			return (Action) constructor.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
