package com.ocaml.ide.console.debug;

import com.intellij.ide.util.treeView.smartTree.*;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.tree.AsyncTreeModel;
import com.intellij.ui.tree.StructureTreeModel;
import com.intellij.ui.treeStructure.Tree;
import com.ocaml.ide.console.OCamlConsoleView;
import com.ocaml.ide.console.debug.groups.TreeElementGroup;
import com.ocaml.ide.console.debug.groups.elements.OCamlFunctionElement;
import com.ocaml.ide.console.debug.groups.elements.OCamlTreeElement;
import com.ocaml.ide.console.debug.groups.elements.OCamlVariableElement;
import com.ocaml.utils.logs.OCamlLogger;
import org.jetbrains.annotations.NotNull;

/**
 * Panel with the variables
 */
public class OCamlVariablesView extends SimpleToolWindowPanel implements Disposable {
    private static final Logger LOG = OCamlLogger.getREPLInstance("variables_view");

    private final StructureTreeModel<?> structureTreeModel;
    private final SmartTreeStructure treeStructure;
    private final OCamlStupidTreeModel treeModel;

    public OCamlVariablesView(@NotNull OCamlConsoleView consoleView) {
        super(true, true);
        Project project = consoleView.getProject();

        treeModel = new OCamlStupidTreeModel();
        treeStructure = new SmartTreeStructure(project, treeModel);

        structureTreeModel = new StructureTreeModel<>(treeStructure, this);
        var asyncTreeModel = new AsyncTreeModel(structureTreeModel, this);

        var tree = new Tree(asyncTreeModel);
        tree.setRootVisible(false);

        setContent(ScrollPaneFactory.createScrollPane(tree));
    }

    /**
     * Add functions and variables in the tree.
     * @param newEntry lines of the new item (ex: val x : int = 5)
     */
    public void rebuild(@NotNull String newEntry) {
        newEntry = newEntry.trim();
        // todo: log
        if (newEntry.endsWith("<fun>")) LOG.debug("adding a function:"+newEntry);
        else LOG.debug("adding a variable:"+newEntry);

        // todo: const + parser

        // find name
        int val = newEntry.indexOf("val");
        int colon = newEntry.indexOf(":");
        int equals = newEntry.indexOf("=");
        String name = newEntry.substring(val+3+1, colon-1);

        // find type
        String type = newEntry.substring(colon+1, equals-1);

        OCamlTreeElement element;
        TreeElementGroup group;
        if (!newEntry.endsWith("<fun>")) {
            // find value
            String value = newEntry.substring(equals+1);
            element = new OCamlVariableElement(name, value, type);
            group = treeModel.variables;
        } else {
            element = new OCamlFunctionElement(name, type);
            group = treeModel.functions;
        }

        // remove
        treeModel.remove(element);
        // add
        group.elements.add(element);

        ApplicationManager.getApplication().invokeLater(() -> ApplicationManager.getApplication().runWriteAction(() -> {
            structureTreeModel.getInvoker().invokeLater(() -> {
                treeStructure.rebuildTree();
                structureTreeModel.invalidate();
            });
        }));
    }

    @Override public void dispose() {
    }
}