package net.thucydides.maven.plugins;

import java.io.File;
import java.io.IOException;

import net.thucydides.core.reports.html.HtmlUserStoryTestReporter;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Generate aggregate XML acceptance test reports.
 * 
 * @goal aggregate
 * @requiresReports true
 * @phase verify
 */
public class ThucydidesReporterMojo extends AbstractMojo {
    /**
     * Aggregate reports are generated here
     * 
     * @parameter expression="${project.build.directory}/thucydides"
     * @required
     */
    private File outputDirectory;

    /**
     * Thucydides test reports are read from here
     * 
     * @parameter expression="${project.build.directory}/thucydides"
     * @required
     */
    private File sourceDirectory;

    private HtmlUserStoryTestReporter reporter = new HtmlUserStoryTestReporter();

    protected void setOutputDirectory(final File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }


    protected void setSourceDirectory(final File sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    protected void setReporter(final HtmlUserStoryTestReporter reporter) {
        this.reporter = reporter;
    }

    public void execute() throws MojoExecutionException {
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        try {
            generateHtmlStoryReports();
        } catch (IOException e) {
            throw new MojoExecutionException("Error generating aggregate thucydides reports", e);
        }
    }

    private void generateHtmlStoryReports() throws IOException {
        reporter.setOutputDirectory(outputDirectory);        
        reporter.generateReportsForStoriesFrom(sourceDirectory);
    }

}
