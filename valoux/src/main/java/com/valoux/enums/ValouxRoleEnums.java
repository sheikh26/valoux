/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.enums;

/**
 * This <java>class</java> ValouxRoleEnums class has all the methods related to
 * ValouxRole.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

public class ValouxRoleEnums {

	public enum UserRoleType {
		StoreOwner(1), StoreAdmin(2), StoreManager(3), StoreSalesperson(4), StoreAppraiser(5), Appraiser(6), ConsumerAdmin(
				7), PartnerAdmin(9), VenderAdmin(10), SuperAdmin(11);

		private final int roleId;

		private UserRoleType(int roleId) {
			this.roleId = roleId;
		}

		public int getRoleId() {
			return roleId;
		}

	}

}
