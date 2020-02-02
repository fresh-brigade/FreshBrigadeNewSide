/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.play.internal.run;

import org.gradle.internal.UncheckedException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class PlayRunAdapterV22X extends DefaultVersionedPlayRunAdapter {
    @Override
    protected Class<?> getBuildLinkClass(ClassLoader classLoader) throws ClassNotFoundException {
        return classLoader.loadClass("play.core.SBTLink");
    }

    @Override
    protected Class<?> getBuildDocHandlerClass(ClassLoader classLoader) throws ClassNotFoundException {
        return classLoader.loadClass("play.core.SBTDocHandler");
    }

    @Override
    protected Class<?> getDocHandlerFactoryClass(ClassLoader docsClassLoader) throws ClassNotFoundException {
        return docsClassLoader.loadClass("play.docs.SBTDocHandlerFactory");
    }

    protected ClassLoader createAssetsClassLoader(File assetsJar, Iterable<File> assetsDirs, ClassLoader classLoader) {
        try {
            return new URLClassLoader(new URL[]{assetsJar.toURI().toURL()}, classLoader);
        } catch (MalformedURLException e) {
            throw UncheckedException.throwAsUncheckedException(e);
        }
    }
}
