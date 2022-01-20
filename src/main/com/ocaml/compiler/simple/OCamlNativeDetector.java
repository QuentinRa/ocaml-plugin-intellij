package com.ocaml.compiler.simple;

import com.intellij.execution.configurations.PathEnvironmentVariableUtil;
import com.intellij.openapi.diagnostic.Logger;
import com.ocaml.ide.sdk.providers.OCamlSdkProvider;
import com.ocaml.ide.sdk.providers.OCamlSdkProvidersManager;
import com.ocaml.ide.sdk.providers.utils.AssociatedBinaries;
import com.ocaml.utils.logs.OCamlLogger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Set;

public final class OCamlNativeDetector {

    private static final Logger LOG = OCamlLogger.getSdkProviderInstance();

    /**
     * Look for ocaml and ocamlc in the path.<br>
     * Look for the version of the SDK.<br>
     * Look for the sources.<br>
     * If we found everything, then we return the DetectionResult,
     * otherwise, we return null.
     * @see OCamlSdkProvider#getOCamlExecutablePathCommands()
     * @see OCamlSdkProvider#getOCamlCompilerExecutablePathCommands()
     * @see OCamlSdkProvider#getOCamlSourcesFolders()
     */
    public static @NotNull DetectionResult detectNativeSdk() {
        Set<String> ocamlExecutablesNames = OCamlSdkProvidersManager.INSTANCE.getOCamlExecutablePathCommands();
        // check every binary
        for (String executableName: ocamlExecutablesNames) {
            File f = PathEnvironmentVariableUtil.findInPath(executableName);
            if (f == null || !f.exists()) continue;
            LOG.debug("found binary: " + executableName);
            // look for the compiler
            DetectionResult res = detectNativeSdk(f.getAbsolutePath());
            LOG.debug("Result binary:"+res);
            if (!res.isError) return res;
        }
        return DetectionResult.NO_ASSOCIATED_BINARIES;
    }

    /**
     * Returns the natives associated to an ocaml binary.
     */
    public static DetectionResult detectNativeSdk(String ocamlBinary) {
        AssociatedBinaries associatedBinaries = OCamlSdkProvidersManager.INSTANCE.getAssociatedBinaries(ocamlBinary);
        if (associatedBinaries == null) {
            LOG.warn("detectNativeSdk: not found for "+ocamlBinary);
            return DetectionResult.NO_ASSOCIATED_BINARIES;
        }
        LOG.debug("detectNativeSdk: found '"+associatedBinaries+"' for '"+ocamlBinary+"'");
        return new DetectionResult(
                ocamlBinary,
                associatedBinaries.compilerPath,
                associatedBinaries.compilerVersion,
                associatedBinaries.sourcesPath
        );
    }

}
