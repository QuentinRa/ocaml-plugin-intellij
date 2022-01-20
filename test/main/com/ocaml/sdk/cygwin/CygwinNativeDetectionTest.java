package com.ocaml.sdk.cygwin;

import com.intellij.util.SystemProperties;
import com.ocaml.compiler.simple.DetectionResult;
import com.ocaml.compiler.simple.OCamlNativeDetector;
import org.junit.Test;

@SuppressWarnings("JUnit4AnnotatedMethodInJUnit3TestCase")
public class CygwinNativeDetectionTest extends CygwinBaseTest {

    // This test is only possible because
    // this one is in the PATH

    @Test
    public void testPath() {
        DetectionResult detectionResult = OCamlNativeDetector.detectNativeSdk();
        assertEquals("C:\\cygwin64\\bin\\ocaml.exe", detectionResult.ocaml);
        assertEquals("C:\\cygwin64\\bin\\ocamlc.opt.exe", detectionResult.ocamlCompiler);
        assertEquals("C:\\cygwin64\\lib\\ocaml", detectionResult.sources);
        assertEquals("4.10.0", detectionResult.version);
        assertFalse(detectionResult.isError);
    }

    //
    // Testing
    //

    @Test
    public void testEmpty() {
        assertCygwinInvalid("");
    }

    @Test
    public void testPathInvalid() {
        assertCygwinInvalid("C:\\cygwin64\\invalid\\bin\\ocaml.exe");
    }

    @Test
    public void testNoExe() {
        assertCygwinInvalid("C:\\cygwin64\\invalid\\bin\\ocaml");
    }

    @Test
    public void testNotOCaml() {
        assertCygwinInvalid("C:\\cygwin64\\bin\\find.exe");
    }

    @Test
    public void testBin() {
        assertCygwinValid(
                "C:\\cygwin64\\bin\\ocaml.exe",
                "ocamlc.opt.exe",
                "4.10.0"
        );
    }

    @Test
    public void testOpamBinValid() {
        assertCygwinValid(
                "C:\\cygwin64\\home\\"+ SystemProperties.getUserName() + "\\.opam\\4.08.0\\bin\\ocaml.exe",
                "ocamlc.opt.exe",
                "4.08.0"
        );
    }

    @Test
    public void testOpamBinInvalid() {
        assertCygwinInvalid(
                "C:\\cygwin64\\home\\"+ SystemProperties.getUserName() + "\\.opam\\0.0.0\\bin\\ocaml.exe"
        );
    }
}
