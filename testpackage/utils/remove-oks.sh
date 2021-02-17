#!/usr/bin/env bash

filelist=($(ls *.report.txt))

echo -e "fails:\n"

for report_file in "${filelist[@]}"; do
    [[ $(cat "$report_file") == "" ]] && rm $report_file && continue
    [[ $(cat "$report_file") =~ ^OK.* ]] && rm $report_file && continue
    echo $report_file
done
