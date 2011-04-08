package net.thucydides.core.reports.xml;

import com.thoughtworks.xstream.mapper.CannotResolveClassException;

/**
 * Exception thrown if we attempt to process a file that is not a valid Thucydides XML report.
 * @author johnsmart
 *
 */
public class NotAThucydidesReportException extends Exception {

    private static final long serialVersionUID = 1L;

    public NotAThucydidesReportException(final String message, CannotResolveClassException e) {
        super(message, e);
    }

}
