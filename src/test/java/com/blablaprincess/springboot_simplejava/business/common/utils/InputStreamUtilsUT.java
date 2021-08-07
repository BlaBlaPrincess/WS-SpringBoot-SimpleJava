package com.blablaprincess.springboot_simplejava.business.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class InputStreamUtilsUT {

    @Test
    @DisplayName("static toString")
    void staticToString() {
        // Arrange
        String source = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed placerat lectus nec dui lacinia, "   +
                "id suscipit erat pellentesque. Donec mollis a sapien vitae tincidunt. Aliquam feugiat orci sed lorem "    +
                "hendrerit scelerisque. Aenean pharetra efficitur sem sit amet porta. Vivamus metus tellus, sollicitudin " +
                "at risus ac, aliquam dignissim velit. Ut consectetur dignissim libero eu consequat. Orci varius natoque " +
                "penatibus et magnis dis parturient montes, nascetur ridiculus mus. Sed in sem sem. Nulla posuere ligula " +
                "purus, eget consectetur orci efficitur nec. Morbi pretium est ac eros dapibus posuere.\n" +
                "\n" +
                "Aenean maximus purus non augue fermentum luctus. Etiam quam justo, rhoncus at metus non, volutpat "       +
                "sagittis mauris. Nulla ac mauris sed leo mollis porta interdum quis urna. Aliquam magna mi, elementum "   +
                "eu arcu in, posuere feugiat lorem. Donec interdum, orci vitae suscipit vestibulum, turpis nulla posuere " +
                "magna, sit amet finibus eros nisi sodales neque. Nunc in felis eget ante suscipit consequat at sit amet " +
                "lorem. Donec rutrum aliquet dictum. Proin orci tellus, cursus in enim vel, luctus fermentum elit. "       +
                "Maecenas sollicitudin semper aliquet. Ut sit amet maximus diam. Donec pharetra sapien porttitor ipsum "   +
                "efficitur, sit amet varius sapien consectetur. Duis malesuada vel sem at placerat. Proin ultrices quis "  +
                "lacus nec vulputate. Suspendisse convallis mauris justo, ut pellentesque dolor elementum eu. Cras quis "  +
                "risus diam. Integer purus urna, commodo sit amet quam nec, maximus dignissim urna.";

        InputStream stream = new ByteArrayInputStream(source.getBytes());

        // Act
        String result = InputStreamUtils.toString(stream);

        // Assert
        assertEquals(source, result);
    }

}