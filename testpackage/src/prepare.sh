#!/usr/bin/env zsh

mv run.sh run.sh~ || rm run.sh 2>&1 >/dev/null
mv tests tests~ || rm tests 2>&1 >/dev/null

[[ ! -d out ]] && mv out out~
rm -rf out 2>&1 >/dev/null
[[ ! -d report ]] && mv report report~
rm -rf report 2>&1 >/dev/null

mkdir -p out
mkdir -p report

ln -s ../utils/remove-oks.sh out/
rm run.sh 2>&1 >/dev/null;     ln -s ../utils/run.sh
rm comp.py 2>&1 >/dev/null;    ln -s ../utils/comp.py
rm categories 2>&1 >/dev/null; ln -s ../utils/categories
rm codegen 2>&1 >/dev/null;    ln -s ../tests/codegen tests

read "technology?Please specify the technology (bison, lark, cup, pgenc, pgenj, pgenpy): "

case $technology in
    'bison')
        techfile='.bison-c++'
        ;;
    'cup')
        techfile='.cup-java'
        ;;
    'lark')
        techfile='.lark-python'
        ;;
    'pgenc')
        techfile='.pgen-c++'
        ;;
    'pgenj')
        techfile='.pgen-java'
        ;;
    'pgenpy')
        techfile='.pgen-python'
        ;;
esac

touch "$techfile"
