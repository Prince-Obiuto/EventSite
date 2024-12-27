package com.cit306.EventSite.httpserver.core.io;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WebRootHandlerTest {

    private WebRootHandler webRootHandler;
    private Method CheckIfEndsWithSlashMethod;
    private Method checkIfProvidedRelativePathExistsMethod;

    @BeforeAll
    public void beforeClass() throws WebRootNotFoundException, NoSuchMethodException {
        webRootHandler = new WebRootHandler("WebRoot");
        Class<WebRootHandler> cls = WebRootHandler.class;
        CheckIfEndsWithSlashMethod = cls.getDeclaredMethod("CheckIfEndsWithSlash", String.class);
        CheckIfEndsWithSlashMethod.setAccessible(true);

        checkIfProvidedRelativePathExistsMethod = cls.getDeclaredMethod("checkIfProvidedRelativePathExists", String.class);
        checkIfProvidedRelativePathExistsMethod.setAccessible(true);
    }

    @Test
    void constructorGoodPath() {
        try {
            WebRootHandler webRootHandler = new WebRootHandler("C:\\Users\\HP\\Desktop\\Java Projects\\EventSite\\WebRoot");
        } catch (WebRootNotFoundException e) {
            fail(e);
        }
    }

    @Test
    void constructorGoodPath2() {
        try {
            WebRootHandler webRootHandler = new WebRootHandler("WebRoot");
        } catch (WebRootNotFoundException e) {
            fail(e);
        }
    }

    @Test
    void constructorBadPath() {
        try {
            WebRootHandler webRootHandler = new WebRootHandler("C:\\Users\\HP\\Desktop\\Java Projects\\EventSite\\WebRoot2");
            fail();
        } catch (WebRootNotFoundException e) {}
    }

    @Test
    void constructorBadPath2() {
        try {
            WebRootHandler webRootHandler = new WebRootHandler("WebRoot2");
            fail();
        } catch (WebRootNotFoundException e) {}
    }

    @Test
    void CheckIfEndsWithSlashMethodFalse() {
        try {
            boolean result = (Boolean) CheckIfEndsWithSlashMethod.invoke(webRootHandler,"index.html");
            assertFalse(result);
        } catch (IllegalAccessException e) {
            fail(e);
        } catch (InvocationTargetException e) {
            fail(e);
        }
    }

    @Test
    void CheckIfEndsWithSlashMethodFalse2() {
        try {
            boolean result = (Boolean) CheckIfEndsWithSlashMethod.invoke(webRootHandler,"/index.html");
            assertFalse(result);
        } catch (IllegalAccessException e) {
            fail(e);
        } catch (InvocationTargetException e) {
            fail(e);
        }
    }

    @Test
    void CheckIfEndsWithSlashMethodFalse3() {
        try {
            boolean result = (Boolean) CheckIfEndsWithSlashMethod.invoke(webRootHandler,"/private/index.html");
            assertFalse(result);
        } catch (IllegalAccessException e) {
            fail(e);
        } catch (InvocationTargetException e) {
            fail(e);
        }
    }

    @Test
    void CheckIfEndsWithSlashMethodTrue() {
        try {
            boolean result = (Boolean) CheckIfEndsWithSlashMethod.invoke(webRootHandler,"/");
            assertTrue(result);
        } catch (IllegalAccessException e) {
            fail(e);
        } catch (InvocationTargetException e) {
            fail(e);
        }
    }

    @Test
    void CheckIfEndsWithSlashMethodTrue2() {
        try {
            boolean result = (Boolean) CheckIfEndsWithSlashMethod.invoke(webRootHandler, "/private/");
            assertTrue(result);
        } catch (IllegalAccessException e) {
            fail(e);
        } catch (InvocationTargetException e) {
            fail(e);
        }
    }

    @Test
    void testWebRootFilePathExists() {
        try {
            boolean result = (Boolean) checkIfProvidedRelativePathExistsMethod.invoke(webRootHandler, "/index.html");
            assertTrue(result);
        } catch (IllegalAccessException e) {
            fail(e);
        } catch (InvocationTargetException e) {
            fail(e);
        }
    }

    @Test
    void testWebRootFilePathExistsGoodRelative() {
        try {
            boolean result = (Boolean) checkIfProvidedRelativePathExistsMethod.invoke(webRootHandler, "/./././index.html");
            assertTrue(result);
        } catch (IllegalAccessException e) {
            fail(e);
        } catch (InvocationTargetException e) {
            fail(e);
        }
    }

    @Test
    void testWebRootFilePathDoesNotExist() {
        try {
            boolean result = (Boolean) checkIfProvidedRelativePathExistsMethod.invoke(webRootHandler, "/indexNotHere.html");
            assertFalse(result);
        } catch (IllegalAccessException e) {
            fail(e);
        } catch (InvocationTargetException e) {
            fail(e);
        }
    }

    @Test
    void testWebRootFilePathInvalid() {
        try {
            boolean result = (Boolean) checkIfProvidedRelativePathExistsMethod.invoke(webRootHandler, "/.../LICENSE");
            assertFalse(result);
        } catch (IllegalAccessException e) {
            fail(e);
        } catch (InvocationTargetException e) {
            fail(e);
        }
    }
}
