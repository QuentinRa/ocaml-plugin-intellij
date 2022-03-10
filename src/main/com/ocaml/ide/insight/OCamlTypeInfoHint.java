package com.ocaml.ide.insight;

import com.intellij.lang.ExpressionTypeProvider;
import com.intellij.psi.PsiElement;
import com.ocaml.OCamlBundle;
import com.ocaml.sdk.annot.OCamlInferredSignature;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// CTRL + SHIFT + P
// expression -> type
// _ -> getErrorHint()
public class OCamlTypeInfoHint extends ExpressionTypeProvider<PsiElement> {

    public static final String UNKNOWN_TYPE = OCamlBundle.message("ocaml.insight.unknown.type");

    @Override public @NotNull String getInformationHint(@NotNull PsiElement element) {
        OCamlAnnotResultsService annot = element.getProject().getService(OCamlAnnotResultsService.class);
        OCamlInferredSignature annotation = annot.findAnnotationFor(element);
        return annotation == null || annotation.type.isEmpty() ? UNKNOWN_TYPE : annotation.type;
    }

    @Override public @NotNull String getErrorHint() {
        return OCamlBundle.message("editor.no.expression.found");
    }

    @Override public @NotNull List<PsiElement> getExpressionsAt(@NotNull final PsiElement elementAt) {
        // small optimisation: skip
        if (OCamlInsightFilter.isWhiteSpace(elementAt)) return List.of();

        // if we got some info for this element, then this is a valid element
        // note: we should check list made of multiples elements
        OCamlAnnotResultsService annot = elementAt.getProject().getService(OCamlAnnotResultsService.class);
        if(annot.hasInfoForElement(elementAt))
            return List.of(elementAt);
        return List.of();
    }
}
