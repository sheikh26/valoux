/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.enums;

/**
 * This <java>Class</java> ItemComponents class has all the methods related to
 * Components.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

public class ItemComponents {

	public static enum Components {
		Diamonds(1), Gemstones(2), Pearls(3), Metals(4);
		private final int componentsId;

		Components(Integer componentsId) {
			this.componentsId = componentsId;
		}

		public Integer getId() {
			return this.componentsId;
		}
	}
}
