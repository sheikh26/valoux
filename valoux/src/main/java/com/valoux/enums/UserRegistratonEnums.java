/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.enums;

/**
 * This <java>class</java> UserRegistratonEnums class has all the methods
 * related to UserRegistraton.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

public class UserRegistratonEnums {

	public static enum UsertType {
		Customer(1), Agent(2), vender(3), partner(4), SuperAdmin(5);

		private final int userType;

		UsertType(Integer userType) {
			this.userType = userType;
		}

		public Integer getType() {
			return this.userType;
		}

	}

}
