// This is a generated file. Not intended for manual editing.
package com.ocaml.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface OCamlClassBody extends PsiElement {

  @NotNull
  List<OCamlClassField> getClassFieldList();

  @Nullable
  OCamlPattern getPattern();

  @Nullable
  OCamlTypexpr getTypexpr();

}
