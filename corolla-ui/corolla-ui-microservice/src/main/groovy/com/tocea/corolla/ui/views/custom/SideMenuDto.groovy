package com.tocea.corolla.ui.views.custom

import groovy.transform.Canonical

import com.tocea.corolla.core.plugins.model.SideMenuAction
import com.tocea.corolla.core.plugins.model.SideMenuItem

@Canonical
class SideMenuDto {
	def SideMenuItem item
	def SideMenuAction action
}
