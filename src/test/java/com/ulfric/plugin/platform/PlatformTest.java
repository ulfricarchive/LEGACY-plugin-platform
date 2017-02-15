package com.ulfric.plugin.platform;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.commons.spigot.testing.plugin.JavaPluginTestBase;
import com.ulfric.commons.spigot.testing.plugin.Plugin;
import com.ulfric.plugin.platform.Platform;

@RunWith(JUnitPlatform.class)
@Plugin(Platform.class)
class PlatformTest extends JavaPluginTestBase {

}