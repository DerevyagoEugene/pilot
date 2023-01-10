package allure;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;

public class AllureReport {

    private AllureReport() {
        throw new IllegalStateException("Utility class");
    }

    public static void addText(String name,
                               byte[] data) {
        addAttachment(name, "text/plain", "txt", data);
    }

    public static void addImage(String name,
                                byte[] data) {
        addAttachment(name, "image/png", "png", data);
    }

    public static void updateTestCaseName(String name) {
        AllureLifecycle lifecycle = Allure.getLifecycle();
        lifecycle.updateTestCase(testResult -> testResult.setName(name));
    }

    public static void updateTestStepResultStatus(Status status) {
        AllureLifecycle lifecycle = Allure.getLifecycle();
        lifecycle.updateStep(stepResult -> stepResult.setStatus(status));
    }

    private static void addAttachment(String name,
                                      String type,
                                      String extension,
                                      byte[] data) {
        Allure.getLifecycle().addAttachment(name, type, extension, data);
    }
}
