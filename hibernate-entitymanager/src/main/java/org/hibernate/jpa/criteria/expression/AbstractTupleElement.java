/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2009 by Red Hat Inc and/or its affiliates or by
 * third-party contributors as indicated by either @author tags or express
 * copyright attribution statements applied by the authors.  All
 * third-party contributions are distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.jpa.criteria.expression;

import org.hibernate.jpa.criteria.AbstractNode;
import org.hibernate.jpa.criteria.CriteriaBuilderImpl;
import org.hibernate.jpa.criteria.TupleElementImplementor;
import org.hibernate.jpa.criteria.ValueHandlerFactory;

import javax.persistence.metamodel.EntityType;
import java.io.Serializable;

/**
 * TODO : javadoc
 *
 * @author Steve Ebersole
 */
public abstract class AbstractTupleElement<X>
		extends AbstractNode
		implements TupleElementImplementor<X>, Serializable {
	private final Class originalJavaType;
	private Class<X> javaType;
    private EntityType<X> entityType;
	private String alias;
	private ValueHandlerFactory.ValueHandler<X> valueHandler;

	protected AbstractTupleElement(CriteriaBuilderImpl criteriaBuilder, Class<X> javaType) {
		super( criteriaBuilder );
		this.originalJavaType = javaType;
		this.javaType = javaType;
	}

    protected AbstractTupleElement(CriteriaBuilderImpl criteriaBuilder, EntityType<X> entityType) {
        super( criteriaBuilder );
        this.originalJavaType = this.javaType = (Class<X>) Object.class;
        this.entityType = entityType;
    }

    @Override
	public Class<X> getJavaType() {
		return javaType;
	}

    //@Override
    public EntityType<X> getEntityType() {
        return entityType;
    }

	@SuppressWarnings({ "unchecked" })
	protected void resetJavaType(Class targetType) {
		this.javaType = targetType;
//		this.valueHandler = javaType.equals( originalJavaType )
//				? null
//				: ValueHandlerFactory.determineAppropriateHandler( javaType );
		this.valueHandler = ValueHandlerFactory.determineAppropriateHandler( javaType );
	}

	protected void forceConversion(ValueHandlerFactory.ValueHandler<X> valueHandler) {
		this.valueHandler = valueHandler;
	}

	@Override
	public ValueHandlerFactory.ValueHandler<X> getValueHandler() {
		return valueHandler;
	}

	@Override
	public String getAlias() {
		return alias;
	}

	/**
	 * Protected access to define the alias.
	 *
	 * @param alias The alias to use.
	 */
	protected void setAlias(String alias) {
		this.alias = alias;
	}
}
