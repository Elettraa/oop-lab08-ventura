package it.unibo.mvc;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;

/**
 * Application entry-point.
 */
public final class LaunchApp {
    private static final String PACKAGE = "it.unibo.mvc.";
    private static final String VIEW_SWING = "view.DrawNumberSwingView";
    private static final String VIEW_STDOUT = "view.DrawNumberViewImpl";
    private static final int NUMBER_OF_VIEWS = 3;

    private LaunchApp() {
    }

    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws SecurityException
     * @throws ClassNotFoundException    if the fetches class does not exist
     * @throws NoSuchMethodException     if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException    if the constructor throws exceptions
     * @throws IllegalAccessException    in case of reflection issues
     * @throws IllegalArgumentException  in case of reflection issues
     */
    public static void main(final String... args)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException, ClassNotFoundException {

        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);
        List<String> allViewNames = List.of(VIEW_STDOUT, VIEW_SWING);
        for (String name : allViewNames) {
            for (int i = 0; i < NUMBER_OF_VIEWS; i++) {
                final DrawNumberView viewToAdd = (DrawNumberView) Class.forName(PACKAGE + name).getConstructor()
                        .newInstance();
                app.addView(viewToAdd);
            }
        }
    }
}
