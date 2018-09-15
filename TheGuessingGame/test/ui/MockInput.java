package ui;

import java.io.ByteArrayInputStream;

class MockInput extends ByteArrayInputStream {
    MockInput(String data) {
        super(data.getBytes());
    }

    MockInput(String[] data) {
        super(String.join("\r\n", data).getBytes());
    }
}
