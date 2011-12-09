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

package org.jboss.ceylon.test.modules.smoke.test;

import org.jboss.acme.module;
import org.jboss.ceylon.test.modules.ModulesTest;
import org.jboss.filtered.api.SomeAPI;
import org.jboss.filtered.impl.SomeImpl;
import org.jboss.filtered.spi.SomeSPI;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class SmokeTestCase extends ModulesTest {
    @Test
    public void singleModule() throws Throwable {
        JavaArchive module = ShrinkWrap.create(JavaArchive.class, "org.jboss.acme-1.0.0.CR1.car");
        module.addClass(module.class);
        testArchive(module);
    }

    @Test
    public void transitiveModule() throws Throwable {
        JavaArchive module = ShrinkWrap.create(JavaArchive.class, "com.foobar.qwert-1.0.0.GA.car");
        module.addClass(com.foobar.qwert.module.class);

        JavaArchive lib = ShrinkWrap.create(JavaArchive.class, "org.jboss.acme-1.0.0.CR1.car");
        lib.addClass(module.class);

        testArchive(module, lib);
    }

    @Test
    public void filteredModule() throws Throwable {
        JavaArchive module = ShrinkWrap.create(JavaArchive.class, "eu.cloud.clazz-1.0.0.GA.car");
        module.addClass(eu.cloud.clazz.module.class);

        JavaArchive lib = ShrinkWrap.create(JavaArchive.class, "org.jboss.filtered-1.0.0.Alpha1.car");
        lib.addClass(org.jboss.filtered.module.class);
        lib.addClass(SomeSPI.class);
        lib.addClass(SomeAPI.class);
        lib.addClass(SomeImpl.class);

        testArchive(module, lib);
    }
}
