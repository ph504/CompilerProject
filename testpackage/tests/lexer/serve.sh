#!/bin/bash

output_dir=../../out/
output_prefix=$output_dir/lexer/
serve_dir=serve/lexer/

for i in *.in; do
    filename=$(basename $i .in)
    outfilename=${output_prefix}/${filename}.out

    [[ ! -f $outfilename ]] && continue

    dest_dir=$output_dir/$serve_dir/

    mkdir -p ${dest_dir}
    cp $i $outfilename $dest_dir
done
