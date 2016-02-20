package com.xunlei.game.velocity.util;

import java.util.HashMap;
import java.util.Map;

public class CommandLineArgsUtil {

	public static Map<String, String> resolveArgs(String[] args) {
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				System.out.print(args[i] + " ");
			}
			System.out.println();
		}
		Map<String, String> params = new HashMap<String, String>();
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				if (args[i].startsWith("-")) { // 当前元素是参数名
					if ((i + 1) < args.length) {// 还不是最后一个元素，接着判断下一个元�?
						if (args[i + 1].startsWith("-")) {// 下一个元素是参数�?
							params.put(args[i], null);
						} else {// 下一个元素是参数�?
							params.put(args[i], args[i + 1]);
							i++;
						}
					} else { // 已经是最后一个元�?
						params.put(args[i], null);
					}
				} else {
					continue;
				}
			}
		}
		return params;
	}
}
