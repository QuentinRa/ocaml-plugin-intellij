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

public class OCamlDotFieldLeftArrowBaseExprImpl extends OCamlExprImpl implements OCamlDotFieldLeftArrowBaseExpr {

  public OCamlDotFieldLeftArrowBaseExprImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull OCamlVisitor visitor) {
    visitor.visitDotFieldLeftArrowBaseExpr(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof OCamlVisitor) accept((OCamlVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<OCamlExpr> getExprList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, OCamlExpr.class);
  }

  @Override
  @NotNull
  public OCamlField getField() {
    return findNotNullChildByClass(OCamlField.class);
  }

}