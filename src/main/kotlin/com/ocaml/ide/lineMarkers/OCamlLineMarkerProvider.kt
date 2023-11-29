package com.ocaml.ide.lineMarkers

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.search.GlobalSearchScope
import com.ocaml.icons.OCamlIcons
import com.ocaml.language.psi.OCamlLetBinding
import com.ocaml.language.psi.OCamlValueDescription
import com.ocaml.language.psi.api.OCamlLetDeclaration
import com.ocaml.language.psi.api.OCamlNameIdentifierOwner
import com.ocaml.language.psi.api.OCamlNamedElement
import com.ocaml.language.psi.stubs.index.OCamlNamedElementIndex

// For tests:
// - In ML, can go to ML or MLI for VAL
// - In MLI, can go to ML for LET
class OCamlLineMarkerProvider : RelatedItemLineMarkerProvider() {
    override fun collectNavigationMarkers(
        element: PsiElement, result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        val project = element.project
        val scope = GlobalSearchScope.allScope(project)
        (element as? OCamlLetBinding)?.let {
            // From LET, Resolve VAL
            collectNamedElementNavigationMarkers<OCamlValueDescription>(it, "let/val", false, project, scope, result)
        }
        (element as? OCamlValueDescription)?.let {
            // From VAL, Resolve LET
            collectNamedElementNavigationMarkers<OCamlLetBinding>(it, "let/val", true, project, scope, result)
        }
    }

    private inline fun <reified T : OCamlNameIdentifierOwner> collectNamedElementNavigationMarkers(
        element: OCamlNameIdentifierOwner,
        text: String,
        isInterface: Boolean,
        project: Project,
        scope: GlobalSearchScope,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        if (element !is OCamlLetDeclaration || !element.isGlobal()) return
        val letName = element.qualifiedName ?: return
        val elements = OCamlNamedElementIndex.Utils.findElementsByName(project, letName, scope)
        val filtered: Collection<OCamlNameIdentifierOwner> = elements.filterIsInstance<T>()
        if (filtered.isEmpty()) return
        val marker: RelatedItemLineMarkerInfo<PsiElement>? = createMarkerInfo(
            element.nameIdentifier, isInterface, text, filtered
        )
        if (marker != null) result.add(marker)
    }

    private fun <T : OCamlNamedElement?> createMarkerInfo(
        nameIdentifier: PsiElement?,
        isInterface: Boolean,
        method: String,
        relatedElements: Collection<T>?
    ): RelatedItemLineMarkerInfo<PsiElement>? {
        return if (nameIdentifier != null && !relatedElements.isNullOrEmpty()) {
            NavigationGutterIconBuilder.create(if (isInterface) OCamlIcons.Gutter.IMPLEMENTED else OCamlIcons.Gutter.IMPLEMENTING)
                .setTooltipText((if (isInterface) "Implements " else "Declare ") + method)
                .setAlignment(GutterIconRenderer.Alignment.RIGHT)
                .setTargets(relatedElements)
                .createLineMarkerInfo(nameIdentifier)
        } else null
    }
}