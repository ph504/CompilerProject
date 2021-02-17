#!/bin/bash

[[ -z $1 ]] && echo "No category" >&2 && exit 1

tests=($(ls -1 t*.d | sort))

for last_test in "${tests[@]}"; do

    last_test_name=$(echo "$last_test" | cut -d'.' -f1);
    last_output_filename="$last_test_name.out"
    last_input_filename="$last_test_name.in"
    last_cpp_filename="$last_test_name.cpp"

    category=$(grep -oP '(?<=-).*?(?=-)' <<< "${last_test_name}")
    t_number=$(grep -oP '(?<=^t).*?(?=-)' <<< "${last_test_name}")
    c_number=$(grep -oP '(?<=-)\d*?$' <<< "${last_test_name}")

    t_number=${t_number##+(0)}
    t_number=$(echo "$t_number" | sed 's/^0*//')

    category="$1"

    new_test_name=$(printf "t%03d-${category}-$((c_number))" $((t_number)))
    echo $new_test_name

    mv "$last_test" "${new_test_name}.d"
    mv "$last_input_filename" "${new_test_name}.in"
    mv "$last_output_filename" "${new_test_name}.out"

    new_cpp="${new_test_name}.cpp"
    mv "$last_cpp_filename" "$new_cpp"

done