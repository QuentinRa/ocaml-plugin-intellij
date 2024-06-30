package com.ocaml.language.psi.mixin

import com.ocaml.language.OCamlParsingTestCase
import com.ocaml.language.psi.OCamlLetBinding
import com.ocaml.language.psi.mixin.utils.expandLetBindingStructuredName
import com.ocaml.language.psi.mixin.utils.getNestedLetBindings
import com.ocaml.language.psi.mixin.utils.handleStructuredLetBinding
import org.junit.Test

class OCamlLetBindingMixinTest : OCamlParsingTestCase() {
    private var letSimple: OCamlLetBinding? = null
    private var letDeconstruction: OCamlLetBinding? = null
    private var letDeconstructionComplex: OCamlLetBinding? = null
    private var letDeconstructionPipe: OCamlLetBinding? = null
    private var letOperatorName: OCamlLetBinding? = null
    private var letOperatorNameNoSpace: OCamlLetBinding? = null
    private var letOperatorDeconstruction: OCamlLetBinding? = null
    private var letAnonymous: OCamlLetBinding? = null
    private var letAnonymousDeconstruction: OCamlLetBinding? = null
    private var letWithNested: OCamlLetBinding? = null

    override fun setUp() {
        super.setUp()
        val letBindings = initWith<OCamlLetBinding>("""
            let a = ()
            let b,c = ()
            let ((d,e), f) = ()
            let g|h = ()
            let ( + ) = ()
            let (+) = ()
            let ((a), (+)) = ()
            let _ = ()
            let (a,(_)) = ()
            let a =
                let b = 5 in
                let c = 6 in
                b * c
        """)
        letSimple = letBindings[0]
        letDeconstruction = letBindings[1]
        letDeconstructionComplex = letBindings[2]
        letDeconstructionPipe = letBindings[3]
        letOperatorName = letBindings[4]
        letOperatorNameNoSpace = letBindings[5]
        letOperatorDeconstruction = letBindings[6]
        letAnonymous = letBindings[7]
        letAnonymousDeconstruction = letBindings[8]
        letWithNested = letBindings[9]
    }

    override fun tearDown() {
        super.tearDown()
        letSimple = null
        letDeconstruction = null
        letDeconstructionComplex = null
        letDeconstructionPipe = null
        letOperatorName = null
        letOperatorNameNoSpace = null
        letOperatorDeconstruction = null
        letAnonymous = null
        letAnonymousDeconstruction = null
        letWithNested = null
    }

    @Test
    fun test_name_identifier_is_leaf() {
        assertIsNameIdentifierALeaf(letSimple?.nameIdentifier)
        assertIsNameIdentifierALeaf(letOperatorName?.nameIdentifier)
        assertIsNameIdentifierALeaf(letOperatorNameNoSpace?.nameIdentifier)

        assertNull(letDeconstruction?.nameIdentifier)
        assertNull(letDeconstructionComplex?.nameIdentifier)
        assertNull(letDeconstructionPipe?.nameIdentifier)
        assertNull(letOperatorDeconstruction?.nameIdentifier)

        assertNull(letAnonymous?.nameIdentifier)
        assertNull(letAnonymousDeconstruction?.nameIdentifier)
    }

    @Test
    fun test_name() {
        assertEquals("a", letSimple?.name)
        assertEquals("b,c", letDeconstruction?.name)
        assertEquals("d,e,f", letDeconstructionComplex?.name)
        assertEquals("g,h", letDeconstructionPipe?.name)
        assertEquals("( + )", letOperatorName?.name)
        assertEquals("( + )", letOperatorNameNoSpace?.name)
        assertEquals("a,( + )", letOperatorDeconstruction?.name)

        assertNull(letAnonymous?.name)
        assertEquals("a", letAnonymousDeconstruction?.name)
    }

    @Test
    fun test_qualified_name() {
        // named
        assertEquals(OCAML_FILE_QUALIFIED_NAME_DOT + "a", letSimple?.qualifiedName)
        assertEquals(OCAML_FILE_QUALIFIED_NAME_DOT + "b,c", letDeconstruction?.qualifiedName)
        assertEquals(OCAML_FILE_QUALIFIED_NAME_DOT + "d,e,f", letDeconstructionComplex?.qualifiedName)
        assertEquals(OCAML_FILE_QUALIFIED_NAME_DOT + "g,h", letDeconstructionPipe?.qualifiedName)
        assertEquals("$OCAML_FILE_QUALIFIED_NAME_DOT( + )", letOperatorName?.qualifiedName)
        assertEquals("$OCAML_FILE_QUALIFIED_NAME_DOT( + )", letOperatorNameNoSpace?.qualifiedName)
        assertEquals(OCAML_FILE_QUALIFIED_NAME_DOT+"a,( + )", letOperatorDeconstruction?.qualifiedName)
        // anonymous
        assertNull(letAnonymous?.qualifiedName)
        assertEquals(OCAML_FILE_QUALIFIED_NAME_DOT+"a", letAnonymousDeconstruction?.qualifiedName)
    }

    @Test
    fun test_expand_structured_fqn_name() {
        fun assertExpanded (name: String, count: Int) {
            assertSize(
                count,
                expandLetBindingStructuredName(name, true)
            )
        }

        assertExpanded("Dummy.a", 1)
        assertExpanded("Dummy.a,b", 2)
        assertExpanded("Dummy.c,d,e", 3)
        assertExpanded("Dummy.( + )", 1)
        assertExpanded("Dummy.a,b,( + )", 3)
    }

    @Test
    fun test_expand_structured_name() {
        fun assertExpandedEquals (name: String, expected: List<String>) {
            val expanded = expandLetBindingStructuredName(name, false)
            assertSize(expected.size, expanded)
            assertEquals(expected, expanded)
        }

        assertExpandedEquals("a", listOf("a"))
        assertExpandedEquals("a,b", listOf("a", "b"))
        assertExpandedEquals("c,d,e", listOf("c", "d", "e"))
        assertExpandedEquals("( + )", listOf("( + )"))
        assertExpandedEquals("a,b,( + )", listOf("a", "b", "( + )"))
    }

    @Test
    fun test_handle_structured_let_binding() {
        // Split in PSI elements and returns
        fun assertStructured (letBinding: OCamlLetBinding?, count: Int) {
            assertNotNull(letBinding) ; letBinding!!
            assertSize(
                count,
                handleStructuredLetBinding(letBinding)
            )
        }

        assertStructured(letSimple, 1)
        assertStructured(letDeconstruction, 2)
        assertStructured(letDeconstructionComplex, 3)
        assertStructured(letDeconstructionPipe, 2)
        assertStructured(letOperatorName, 1)
        assertStructured(letOperatorNameNoSpace, 1)
        assertStructured(letOperatorDeconstruction, 2)
        assertStructured(letAnonymous, 0)
        assertStructured(letAnonymousDeconstruction, 1)
    }

    @Test
    fun test_operator_spacing() {
        initWith<OCamlLetBinding>("""
            let ( === ) = ()
            let (===) = ()
            let ( ===) = ()
            let ( \n   ===\n) = ()
        """).forEach {
            assertEquals(it.name, "( === )")
        }
    }

    @Test
    fun test_nested() {
        assertNotNull(letWithNested)
        val letA = letWithNested!!
        val children = letA.getNestedLetBindings()
        assertSize(2, children)
        val letB = children[0]
        val letC = children[1]
        assertEquals("let b = 5", letB.text)
        assertEquals("let c = 6", letC.text)
    }
}