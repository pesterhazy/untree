# vtree

Print trees in the terminal

Like Unix's standard tree(1), vtree prints a directory tree. Unlike tree, however, it reads the list of files and directories to show from stdin.

## Examples

```
$ git ls-files | vtree
.editorconfig
.gitignore
bin
└── vtree
dev
└── cljs
    └── user.cljs
package.json
scripts
├── check
├── dev
└── run
src
└── vtree
    └── core.cljs
tree.in
tree.out
```

Other examples

```
find /etc -follow | vtree
```
