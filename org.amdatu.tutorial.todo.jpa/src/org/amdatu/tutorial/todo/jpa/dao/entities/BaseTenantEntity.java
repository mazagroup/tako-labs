package org.amdatu.tutorial.todo.jpa.dao.entities;

import javax.persistence.MappedSuperclass;

import org.amdatu.tutorial.todo.api.TenantSupport;
import org.amdatu.tutorial.todo.api.TenantSupportDTO;

@MappedSuperclass
public class BaseTenantEntity implements TenantSupport {
	private String tenantId;
	
	static void fromDTO(TenantSupportDTO dto, BaseTenantEntity entity) {
		entity.tenantId = dto.tenantId;
	}
	
	static void toDTO(BaseTenantEntity entity, TenantSupportDTO dto) {
		dto.tenantId = entity.tenantId;
	}	

	@Override
	public String getTenantId() {
		return tenantId;
	}

	@Override
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
