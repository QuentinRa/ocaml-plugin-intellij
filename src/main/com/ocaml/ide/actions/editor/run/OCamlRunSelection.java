package com.ocaml.ide.actions.editor.run;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.ocaml.ide.actions.editor.OCamlEditorActionBase;
import com.ocaml.ide.console.OCamlConsoleRunner;
import com.ocaml.utils.editor.ExtendedEditorActionUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Allow the user to run
 * the selected code
 */
public class OCamlRunSelection extends OCamlEditorActionBase {

    public static final String ACTION_ID = "editor.repl.run.selection.action";

    @Override protected void doActionPerformed(@NotNull AnActionEvent e, OCamlConsoleRunner runner) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor == null) return;
        String code = ExtendedEditorActionUtil.getSelectedCode(editor);
        if (code == null) return;
        runner.processCommand(code);
    }
}