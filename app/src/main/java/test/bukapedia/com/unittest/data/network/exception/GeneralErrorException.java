package test.bukapedia.com.unittest.data.network.exception;

import java.io.IOException;

public class GeneralErrorException extends IOException {
    public static final String MESSAGE = "Terjadi Kesalahan, Silakan Coba Lagi";

    public GeneralErrorException() {
        super(MESSAGE);
    }
}
