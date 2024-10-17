package baloncesto.patrondao;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class EquipoOutputStream extends ObjectOutputStream {
    public EquipoOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        // No escribe la cabecera
    }
}