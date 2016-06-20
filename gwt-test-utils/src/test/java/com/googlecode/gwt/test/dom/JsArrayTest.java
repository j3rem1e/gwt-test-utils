package com.googlecode.gwt.test.dom;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.googlecode.gwt.test.GwtTestTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsArrayTest extends GwtTestTest {

    @Test
    public void push() {
        // Arrange
        JavaScriptObject jso = JavaScriptObject.createObject();
        JsArray<JavaScriptObject> array = JavaScriptObject.createArray().cast();

        // Act
        array.push(jso);

        // Assert
        assertThat(array.length()).isEqualTo(1);
        assertThat(array.get(0)).isEqualTo(jso);
    }

}
