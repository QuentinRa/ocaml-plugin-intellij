package com.ocaml.ide

import com.intellij.psi.PsiFile
import com.intellij.psi.impl.DebugUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.ocaml.ide.colors.OCamlColor
import com.ocaml.language.base.OCamlFileBase
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

// Source: https://github.com/giraud/reasonml-idea-plugin/blob/master/src/test/java/com/reason/ide/ORBasePlatformTestCase.java
// Revision: 00cc6a98d1086923a7dcd2436bdb3d61db0753ae
@RunWith(JUnit4::class)
abstract class OCamlBasePlatformTestCase : BasePlatformTestCase() {

    protected fun configureCode(fileName: String, code: String): OCamlFileBase {
        val file: PsiFile = myFixture.configureByText(fileName, code)
        println("» " + fileName + " " + this.javaClass)
        println(DebugUtil.psiToString(file, false, true))
        return file as OCamlFileBase
    }

    protected fun configureHighlight(fileName: String, code: String, color: OCamlColor) {
        myFixture.configureByText(fileName, code.replace(
            "<info>",
            "<info textAttributesKey=\"${color.textAttributesKey.externalName}\""
        ))
        myFixture.checkHighlighting(false, true, false, true)
    }
}