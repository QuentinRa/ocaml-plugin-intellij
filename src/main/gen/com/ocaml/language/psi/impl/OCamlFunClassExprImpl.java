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

public class OCamlFunClassExprImpl extends OCamlClassExprImpl implements OCamlFunClassExpr {

  public OCamlFunClassExprImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull OCamlVisitor visitor) {
    visitor.visitFunClassExpr(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof OCamlVisitor) accept((OCamlVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public OCamlClassExpr getClassExpr() {
    return findChildByClass(OCamlClassExpr.class);
  }

  @Override
  @NotNull
  public List<OCamlParameter> getParameterList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, OCamlParameter.class);
  }

}
