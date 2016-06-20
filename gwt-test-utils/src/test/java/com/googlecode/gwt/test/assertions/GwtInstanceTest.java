package com.googlecode.gwt.test.assertions;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.*;
import com.googlecode.gwt.test.GwtTestTest;
import com.googlecode.gwt.test.finder.GwtFinder;
import com.googlecode.gwt.test.finder.Node;
import com.googlecode.gwt.test.finder.NodeObjectFinder;
import org.junit.Test;

import static com.googlecode.gwt.test.assertions.GwtAssertions.assertThat;
import static com.googlecode.gwt.test.finder.GwtFinder.object;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.failBecauseExceptionWasNotThrown;

public class GwtInstanceTest extends GwtTestTest {

    @Test
    public void objectWithHTML() {
        // Arrange
        HTML html = new HTML();
        html.setHTML("<h2> my HTML</h2>");
        RootPanel.get().add(html);

        // Act & Assert
        assertThat(object("<h2> my HTML</h2>").ofType(HTML.class)).isSameAs(html);
    }

    @Test
    public void objectWithId() {
        // Arrange
        Anchor anchor = new Anchor();
        anchor.getElement().setAttribute("id", "my-anchor-id");
        RootPanel.get().add(anchor);

        // Act & Assert
        assertThat(object("my-anchor-id").ofType(Anchor.class)).isSameAs(anchor);
    }

    @Test
    public void objectWithNodeFinder() {
        // Arrange
        ComplexPanel panel = new FlowPanel();
        RootPanel.get().add(panel);

        // registers a NodeObjectFinder for "root" prefix
        GwtFinder.registerNodeFinder("root", new NodeObjectFinder() {

            public Object find(Node node) {
                return GwtFinder.find(RootPanel.get(), node);
            }
        });

        // Act & Assert
        assertThat(object("/root/widget(0)").ofType(ComplexPanel.class)).isSameAs(panel);
    }

    @Test
    public void objectWithText() {
        // Arrange
        Label label = new Label();
        label.setText("my label text");
        RootPanel.get().add(label);

        // Act & Assert
        assertThat(object("my label text").ofType(Label.class)).isSameAs(label);
    }

    @Test
    public void throwsClassCastExpectionWhenNotExpectedType() {
        // Arrange
        Anchor anchor = new Anchor();
        anchor.getElement().setAttribute("id", "my-anchor-id");
        RootPanel.get().add(anchor);

        try {
            // Act
            object("my-anchor-id").ofType(CellTable.class);
            failBecauseExceptionWasNotThrown(ClassCastException.class);
        } catch (ClassCastException e) {
            assertThat(e.getMessage()).isEqualTo(
                    "Object of type com.google.gwt.user.client.ui.Anchor with identifier 'my-anchor-id' cannot be cast to an instance of com.google.gwt.user.cellview.client.CellTable");
        }
    }

    @Test
    public void throwsNullPointerExceptionWhenNull() {
        try {
            // Act
            object("my-not-existing-anchor-id").ofType(Anchor.class);
            failBecauseExceptionWasNotThrown(NullPointerException.class);
        } catch (NullPointerException e) {
            assertThat(e.getMessage()).isEqualTo(
                    "Object with identifier 'my-not-existing-anchor-id' is null");
        }
    }
}
