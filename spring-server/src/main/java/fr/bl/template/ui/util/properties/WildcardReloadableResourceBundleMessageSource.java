package fr.bl.template.ui.util.properties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;

public class WildcardReloadableResourceBundleMessageSource extends org.springframework.context.support.ReloadableResourceBundleMessageSource {

	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	@Override 
	public void setBasenames(String... basenames) {
		if (basenames != null) {
			List<String> baseNames = new ArrayList<String>();
			for (int i = 0; i < basenames.length; i++) {
				String basename = basenames[i];

				// BL : start
				String[] basenameSplitted = basename.split("/");
				String preffix = basenameSplitted[basenameSplitted.length - 1];
				preffix = preffix.substring(0, preffix.length() - 1);
				// BL : end

				Assert.hasText(basename, "Basename must not be empty");
				try {
					Resource[] resources = resourcePatternResolver.getResources(basename.trim());

					for (int j = 0; j < resources.length; j++) {
						Resource resource = resources[j];
						String uri = resource.getURI().toString();

						// BL : start
						String[] uriSplitted = uri.split("/");
						String suffix = uriSplitted[uriSplitted.length - 1];
						uri = uri.replace(suffix, preffix + ".properties");
						// BL : end

						String baseName = null;

						// differ between file (contains the complete file
						// path) and class path resources
						if (resource instanceof FileSystemResource) {
							baseName = "classpath:" + StringUtils.substringBetween(uri, "/classes", ".properties");
						} else if (resource instanceof ClassPathResource) {
							baseName = StringUtils.substringBefore(uri,
									".properties");
						}

						// BL : start
						if (!baseNames.contains(baseName)) {
							// BL : end
							baseNames.add(baseName);
							// BL : start
						}
						// BL : end
					}
				} catch (IOException e) {
					logger.debug("No message source files found for basename "
							+ basename + ".");
				}
			}
			super.setBasenames(baseNames.toArray(new String[baseNames.size()]));
		}
	}
}
