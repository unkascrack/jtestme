package es.jtestme.test.viewers;

import java.util.List;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.viewers.Viewer;

public class MyCustomViewer implements Viewer {

	public String getContentType() {
		return "text/plain";
	}

	public String getContentViewer(List<VerificatorResult> results) {
		boolean error = false;
		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append("ERROR").append(NEW_LINE);
		for (VerificatorResult result : results) {
			if (!result.isSuscess()) {
				error = true;
				errorMessage.append("\t - ").append(result.getName()).append(": ").append(result.getMessage()).append(NEW_LINE);
			}
		}
		return !error ? "OK" : errorMessage.toString();
	}

}
