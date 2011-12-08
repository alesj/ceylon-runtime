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

package ceylon.modules.jboss.runtime;

import ceylon.lang.modules.Import;
import org.jboss.modules.DependencySpec;
import org.jboss.modules.LocalLoader;
import org.jboss.modules.Module;
import org.jboss.modules.ModuleIdentifier;
import org.jboss.modules.ModuleLoadException;
import org.jboss.modules.Resource;

import java.util.Collections;
import java.util.List;

/**
 * Load modules on demand.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
class OnDemandLocalLoader implements LocalLoader {
    private ModuleIdentifier target;
    private CeylonModuleLoader loader;
    private Node<Import> root;

    OnDemandLocalLoader(ModuleIdentifier target, CeylonModuleLoader loader, Node<Import> root) {
        this.target = target;
        this.loader = loader;
        this.root = root;
    }

    protected LocalLoader doUpdate(String[] tokens) {
        Node<Import> current = root;
        for (String token : tokens) {
            current = current.getChild(token);
            if (current == null)
                return null;

            //noinspection SynchronizationOnLocalVariableOrMethodParameter
            synchronized (current) {
                Import i = current.getValue();
                if (i != null) {
                    current.remove(); // remove, so we don't loop; should not happen though

                    DependencySpec mds = loader.createModuleDependency(i);
                    try {
                        Module owner = loader.preloadModule(target);
                        loader.updateModule(owner, mds); // update / add lazy dep

                        Module module = loader.loadModule(CeylonModuleLoader.createModuleIdentifier(i));
                        return new ModuleLocalLoader(module);
                    } catch (ModuleLoadException ignored) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public Class<?> loadClassLocal(String name, boolean resolve) {
        String[] tokens = name.split("\\.");
        LocalLoader ll = doUpdate(tokens);
        return (ll != null ? ll.loadClassLocal(name, resolve) : null);
    }

    public List<Resource> loadResourceLocal(String name) {
        String[] tokens = name.split("/");
        LocalLoader ll = doUpdate(tokens);
        return (ll != null ? ll.loadResourceLocal(name) : Collections.<Resource>emptyList());
    }

    public Package loadPackageLocal(String name) {
        String[] tokens = name.split("\\.");
        LocalLoader ll = doUpdate(tokens);
        return (ll != null ? ll.loadPackageLocal(name) : null);
    }
}
