// This is a generated file. Not intended for manual editing.
package com.ocaml.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.ocaml.language.psi.OCamlTypes.*;
import com.ocaml.language.psi.*;

public class OCamlBraceFieldPatternImpl extends OCamlPatternImpl implements OCamlBraceFieldPattern {

  public OCamlBraceFieldPatternImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull OCamlVisitor visitor) {
    visitor.visitBraceFieldPattern(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof OCamlVisitor) accept((OCamlVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<OCamlField> getFieldList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, OCamlField.class);
  }

  @Override
  @NotNull
  public List<OCamlPattern> getPatternList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, OCamlPattern.class);
  }

  @Override
  @NotNull
  public List<OCamlTypexpr> getTypexprList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, OCamlTypexpr.class);
  }

}