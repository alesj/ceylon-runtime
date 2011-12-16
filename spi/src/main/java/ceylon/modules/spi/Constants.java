/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package ceylon.modules.spi;


/**
 * Ceylon constants.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public enum Constants {
    IMPL_ARGUMENT_PREFIX("+"),
    CEYLON_ARGUMENT_PREFIX("-"),
    DEFAULT("default"),
    DEFAULT_VERSION("main"),
    ARGUMENTS("args"),
    SOURCES("sources"),
    CLASSES("classes"),
    MODULE_PATH("-mp"),
    CEYLON_RUNTIME_MODULE("ceylon.runtime"),
    MERGE_STRATEGY("com.redhat.ceylon.cmr.spi.MergeStrategy"),
    CONTENT_TRANSFORMER("com.redhat.ceylon.cmr.spi.ContentTransformer");
    
    private String value;

    Constants(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
