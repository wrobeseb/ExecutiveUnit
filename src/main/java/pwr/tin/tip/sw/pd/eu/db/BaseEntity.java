package pwr.tin.tip.sw.pd.eu.db;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import pwr.tin.tip.sw.pd.eu.db.model.Algorithm;

public abstract class BaseEntity {

	protected Integer id;

	public abstract Integer getId();
	
	public void setId(Integer id) {
		this.id = id;
	}

	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (obj.getClass() != getClass()) return false;
		
		Algorithm rhs = (Algorithm)obj;
		
		return new EqualsBuilder().append(id, rhs.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(3, 5).append(id).toHashCode();
	}
}
