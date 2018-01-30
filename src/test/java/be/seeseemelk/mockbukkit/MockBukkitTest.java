package be.seeseemelk.mockbukkit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;

import org.bukkit.Bukkit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MockBukkitTest
{
	@Before
	public void setUp()
	{
		MockBukkit.setServerInstanceToNull();
	}
	
	@After
	public void tearDown()
	{
		if (MockBukkit.isMocked())
		{
			MockBukkit.unload();
		}
	}
	
	@Test
	public void setServerInstanceToNull()
	{
		ServerMock server = new ServerMock();
		Bukkit.setServer(server);
		assumeNotNull(Bukkit.getServer());
		MockBukkit.setServerInstanceToNull();
		assertNull(Bukkit.getServer());
	}
	
	@Test
	public void mock_ServerMocked()
	{
		ServerMock server = MockBukkit.mock();
		assertNotNull(server);
		assertEquals(server, MockBukkit.getMock());
		assertEquals(server, Bukkit.getServer());
	}
	
	@Test
	public void isMocked_ServerNotMocked_False()
	{
		assertFalse(MockBukkit.isMocked());
	}
	
	@Test
	public void isMocked_ServerMocked_True()
	{
		MockBukkit.mock();
		assertTrue(MockBukkit.isMocked());
	}
	
	@Test
	public void isMocked_ServerUnloaded_False()
	{
		MockBukkit.mock();
		MockBukkit.unload();
		assertFalse(MockBukkit.isMocked());
	}
	
	@Test
	public void load_MyPlugin()
	{
		MockBukkit.mock();
		TestPlugin plugin = (TestPlugin) MockBukkit.load(TestPlugin.class);
		assertTrue("Plugin not enabled", plugin.isEnabled());
		assertTrue("Plugin's onEnable method not executed", plugin.onEnableExecuted);
	}
	
	@Test
	public void load_TestPluginWithExtraParameter_ExtraParameterPassedOn()
	{
		MockBukkit.mock();
		TestPlugin plugin = (TestPlugin) MockBukkit.load(TestPlugin.class, new Integer(5));
		assertEquals(new Integer(5), plugin.extra);
	}
	
	@Test(expected = RuntimeException.class)
	public void load_TestPluginWithExtraIncorrectParameter_ExceptionThrown()
	{
		MockBukkit.mock();
		MockBukkit.load(TestPlugin.class, "Hello");
	}
	
	@Test
	public void createMockPlugin_CreatesMockPlugin()
	{
		MockBukkit.mock();
		MockPlugin plugin = MockBukkit.createMockPlugin();
		assertEquals("MockPlugin", plugin.getName());
		assertEquals("1.0.0", plugin.getDescription().getVersion());
		assertTrue(plugin.isEnabled());
	}
}



















