# intellij-ocaml

[![Checks](https://badgen.net/github/checks/QuentinRa/intellij-ocaml/)](https://github.com/QuentinRa/intellij-ocaml/actions)
[![Version](https://img.shields.io/jetbrains/plugin/v/18531-ocaml.svg)](https://plugins.jetbrains.com/plugin/18531-ocaml)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/18531-ocaml.svg)](https://plugins.jetbrains.com/plugin/18531-ocaml)
[![License: MIT](https://badgen.net/github/license/quentinRa/intellij-ocaml?color=yellow)](LICENSE)
![Dependabot: Active](https://badgen.net/github/dependabot/QuentinRa/intellij-ocaml/)
[![0.2.0](https://badgen.net/github/milestones/quentinra/intellij-ocaml/1)](https://github.com/QuentinRa/intellij-ocaml/milestone/1)
[![0.3.0](https://badgen.net/github/milestones/quentinra/intellij-ocaml/2)](https://github.com/QuentinRa/intellij-ocaml/milestone/2)
[![0.4.0](https://badgen.net/github/milestones/quentinra/intellij-ocaml/3)](https://github.com/QuentinRa/intellij-ocaml/milestone/3)
[![0.5.0](https://badgen.net/github/milestones/quentinra/intellij-ocaml/4)](https://github.com/QuentinRa/intellij-ocaml/milestone/4)
[![0.6.0](https://badgen.net/github/milestones/quentinra/intellij-ocaml/5)](https://github.com/QuentinRa/intellij-ocaml/milestone/8)
[![0.7.0](https://badgen.net/github/milestones/quentinra/intellij-ocaml/6)](https://github.com/QuentinRa/intellij-ocaml/milestone/6)


At least for now, the repository is **unmaintained**. [![No Maintenance Intended](http://unmaintained.tech/badge.svg)](http://unmaintained.tech/)

This plugin is providing support for OCaml for JetBrains IDEs. It's based on both ReasonML and intellij-rust. If you are looking for a complete and maintained solution, please check out the [ReasonML](https://github.com/giraud/reasonml-idea-plugin) plugin (that is based on [ocaml-ide](https://github.com/sidharthkuruvila/ocaml-ide) btw). I'm a student, and I'm lacking the knowledge to do something great.

[Getting Started 🗺️](https://plugins.jetbrains.com/plugin/18531-ocaml/documentation/getting-started) | [Features 🚀](https://plugins.jetbrains.com/plugin/18531-ocaml/documentation/features)

## Current Progress Of The Rework

**Goal**: implement the least feature to have a working plugin using the new parser. This is PoC to test if we can use JetBrains Grammar File.

Internal

* [x] Parser implemented
  * [x] Add some pins
    * [ ] Add more pins
    * [ ] Verify pins
  * [ ] Parser Errors are not handled
  * [ ] Parser recovery is not handled
  * [ ] Parser tests were not implemented
  * [ ] Handle visibility (Has MLI? Has VAL? etc. ==> related: icons and structure view)
* [ ] Stubs
  * [x] Add Stubs For Variables
  * [ ] Use stubs in structure view
  * [x] Do not store anonymous variables
* [ ] Indexes
  * [x] Add indexes for variables
  * [ ] Indexes include nested variables (Scopes?)
  * [ ] Use an index of integers?
* [ ] GitHub CI

User features

* [x] Highlighter
  * [x] Add Lexer Highlighter
  * [x] Add Annotator Highlighter
  * [ ] ~~Highlight pattern variables~~
* [x] Spell-checker
  * [x] Comments
  * [x] Strings
  * [x] Identifiers
* [ ] Typing
  * [x] Braces matching (hover the other)
  * [ ] Quotes handler
  * [ ] Paste processor
  * [ ] Smart enter processor
  * [x] Single Line Comments Handler (CTRL+/)
  * [x] Multi Lines Comments Handler (CTRL+SHIFT+/)
  * [ ] Comments: space not removed after uncommenting ("(* ")
  * [ ] Other handlers
* [ ] Smart Features
  * [ ] Warning/Error Annotations
  * [X] Structure View (ALT+7)
    * [x] Variables
    * [x] Handle Visibility (public/private)
    * [ ] Filters (nested variables?)
  * [ ] Surround With
  * [x] Live Templates
    * [x] Basic implementation
    * [ ] Add scopes (no for in MLI?)
    * [x] Not available in comments
    * [x] Not available in other files
  * [X] Line Markers
    * [X] Declaration
    * [x] Implementation
    * [x] Show a nice presentation
    * [x] Works with pattern variables (ex: "a,b,c")
* [x] Settings
  * [X] Color Settings Page For Highlighter
  * [x] Add Highlighter Annotator to Color Settings Page

Dune

* [x] Highlight
* [x] Annotator Highlight
* [x] Structure View
* [x] Braces matching
* [x] Commenter

**Roadmap I**

* Check references
* Check Documentation
* Test DunePsiFactory
  * Cannot rename ":xxx"
  * Cannot rename "-xxx"
  * Cannot rename "a/b/c"
* Test Dune Structure View
  * Do not show empty lists
  * Do not show values (strings, atoms)
* Modules (files but not just files) indexes
* Handle Include and Open
  * [ ] Line Markers?
* Check completion
* Check folding
  * OCaml Folding
  * Dune Folding
* Find usages
* Go to
* Quotes Handler
  * OCaml
  * Dune
* Smart Enter
* Qualified path
  * [ ] Include Modules/Classes/...
  * [ ] Stub parsers are not ignoring valid parents
  * [ ] Add them to indexes
  * [ ] Ensure Line Marker is still working

```none
FilenameIndex.getVirtualFilesByName(
  "filename.extension",
  false,
  GlobalSearchScope.projectScope(element.project)
)
```

**Roadmap I#Testing**

* Add dune tests (annotator, parser)
* Add reference/include tests
* Add presentation tests
* Add template tests
* Extract StructureView#getChildren and test it
* Add OCamlLanguageUtils tests

**Roadmap II**

* Can create files
* Add SDK
* Handle SDK
* Add REPL
* Handle Module Creation
* Handle Run Configurations
* Can compile using dune (.gitignore? etc.)
* Can display warnings/errors
* Can generate annot file
* Can use annot for type inference
* Space allowed (spaceExistenceTypeBetweenTokens)
* Better stubs (skipChildProcessingWhenBuildingStubs)

**Roadmap III**

* Ensure parser works on 0Caml 4.14 Code
* Ensure parser works on 0Caml 4.13 Code
* Ensure parser works on 0Caml 4.12 Code
* Ensure parser works on 0Caml 4.11 Code
* Ensure parser works on 0Caml 4.10 Code

**Roadmap III#Testing**

* Add parser tests

Random

* Let#isFunction: use type inference?

Generalization List

* [x] Let
* [x] Val
* [ ] Exception
* [ ] Type
* [ ] Module
* [ ] Module Type

Generalize steps

* [ ] Add stubs for others
* [ ] Add structure view for others
* [ ] Add indexes for others
* [ ] Add colors (settings+annotator) for others
* [ ] Ensure everything anonymous is correctly handled (stubs, structure view, etc.)

## Plugin Features Overview

**Base features** (✅ = YES, ❌ = No/Not yet, 🆗 = Must compile first)

|                         | OCaml | ReasonML | VSCode        |
|-------------------------|-------|----------|---------------|
| Highlighter             | ✅     | ✅        | ✅             |
| Find Usages             | ❌     | ✅        | ❌             |
| Type inference (hint)   | ❌     | ❌        | ❌<sup>1</sup> |
| Type checking           | ❌     | ❌        | ❌<sup>1</sup> |
| Autocompletion          | ❌     | ✅        | ✅<sup>2</sup> |
| Live Templates/Snippets | ✅     | ✅        | ✅             |
| Debugging               | ❌     | ?        | ?             |

<sup>1</sup> It was supposed to be 🆗, but it doesn't work on basic projects.

<sup>2</sup> Autocompletion is not context sensitive

**Build Systems** (✅ = Triggered from the editor, ❌ = Manually, 🆗 = Using An External Plugin, 😓 = Syntax Highlight Only)

|                      | OCaml | ReasonML | VSCode        |
|----------------------|-------|----------|---------------|
| Native Build         | ❌     | ❌        | ❌             |
| Dune integration     | 😓    | 😓       | ❌<sup>3</sup> |
| Makefile integration | ?     | ?        | ?             |
| REPL integration     | ❌     | ❌        | ✅             |

<sup>3</sup> It was supposed to be ✅, but command palette doesn't any dune command.

**IntelliJ Features**

|                              | OCaml | ReasonML | VSCode        |
|------------------------------|-------|----------|---------------|
| Structure/Outline View       | ✅     | ✅        | ❌             |
| Spell-checker                | ✅     | ✅        | ❌             |
| Braces Matching              | ✅     | ✅        | ✅             |
| Comment Handler (line/block) | ✅     | ✅        | ✅             |
| Navigate to declaration      | ✅     | ✅        | ❌<sup>4</sup> |
| Navigate to implementation   | ✅     | ✅        | ❌<sup>4</sup> |
| Refactoring                  | ❌     | ?        | ❌             |
| Parser Recovery <sup>3</sup> | ✅     | ❌        | ❌             |

<sup>4</sup> It was supposed to be ✅, but navigation doesn't work.

<sup>5</sup> Ability of the plugin to work on a file that doesn't compile

**External Dependencies**

|                  | OCaml | ReasonML | VSCode |
|------------------|-------|----------|--------|
| OCaml LSP Server | ❌     | ❌        | ?      |
| Merlin           | ❌     | ❌        | ❌      |
| Opam             | ❌     | ✅        | ✅      |

**External Tools Support**

|             | OCaml | ReasonML | VSCode |
|-------------|-------|----------|--------|
| OCamlFormat | ❌     | ✅        | ✅      |

## 🎯 What's my goal?

* [ ] Can run/compile ocaml files (without having to install some external build system)
* [ ] Support for opam (can install / update packages, ...)
* [ ] Support for Makefile
* [ ] Support for Dune
* [x] Any installation must be simple, and effortless
* [ ] **Real-time checks for errors/warnings**, including quick fixes. Can suppress warnings.
* [x] **Real-time type inference**
* [ ] **Smart auto-completion**
* [x] **REPL support**: we must be able to send commands from our file, to a REPL console
* [x] **Using a bundle** (possible support for Localization later)
* [x] Working in minor IDEs (CLion, PHPStorm, etc.)

## 📄 License

This project is [MIT licensed](LICENSE).