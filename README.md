# untree

Print trees in the terminal

Like Unix's standard tree(1), untree prints a directory tree. Unlike tree, however, it reads the list of files and directories to show from stdin.

## Installation

```
npm install -g untree
```

## Examples

```
$ git ls-files | untree
.editorconfig
.gitignore
bin
└── untree
dev
└── cljs
    └── user.cljs
package.json
scripts
├── check
├── dev
└── run
src
└── untree
    └── core.cljs
tree.in
tree.out
```

Other examples

```
find /etc -follow | untree
```
