#!/bin/bash

output_dir=../../out/
output_prefix=$output_dir/parser/
serve_dir=serve/parser/
yes_dir=YES
no_dir=NO

for i in *.in; do
    filename=$(basename $i .in)
    outfilename=${output_prefix}/${filename}.out

    [[ ! -f $outfilename ]] && continue

    [[ $(cat $outfilename) =~ "YES" ]] && status=yes || status=no
    dest_dir=$no_dir && [[ $status == yes ]]  && dest_dir=$yes_dir

    dest_dir=$output_dir/$serve_dir/$dest_dir

    mkdir -p ${dest_dir}
    cp $i $outfilename $dest_dir
done
