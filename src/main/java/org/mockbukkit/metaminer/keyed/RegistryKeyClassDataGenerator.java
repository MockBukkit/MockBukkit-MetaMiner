package org.mockbukkit.metaminer.keyed;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.papermc.paper.registry.RegistryKey;
import org.mockbukkit.metaminer.DataGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class RegistryKeyClassDataGenerator implements DataGenerator
{

	private final File dataFolder;

	public RegistryKeyClassDataGenerator(File pluginFolder)
	{
		this.dataFolder = new File(pluginFolder, "registries");
	}

	@Override
	public void generateData() throws IOException
	{
		if (!dataFolder.exists() && !dataFolder.mkdirs())
		{
			throw new IOException("Could not make directory: " + this.dataFolder);
		}
		File destinationFile = new File(dataFolder, "registry_key_class_relation.json");
		JsonObject rootObject = new JsonObject();

		for (Map.Entry<RegistryKey<?>, Class<?>> entry : KeyedClassTracker.CLASS_REGISTRY_KEY_RELATION.entrySet())
		{
			rootObject.add(entry.getKey().key().asString(), new JsonPrimitive(entry.getValue().getName()));
		}

		if (!destinationFile.exists() && !destinationFile.createNewFile())
		{
			throw new IOException("Could not create file: " + destinationFile);
		}
		try (Writer writer = new FileWriter(destinationFile))
		{
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(rootObject, writer);
		}
	}

}
