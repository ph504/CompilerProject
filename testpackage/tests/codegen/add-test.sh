#!/bin/bash

[[ $1 == "--new" || $1 == "-n" ]] && new_cat=true
[[ $1 == "--reuse" || $1 == "-r" ]] && reuse_cat=$(zenity --title="add-test" --text "Category name to reuse?" --entry)

if [[ -n "$reuse_cat" ]]; then
    tests=($(ls -1 t*-${reuse_cat}-*.d | sort))
    all_tests=($(ls -1 t*.d | sort))
    last_test=${all_tests[-1]}
    last_test_name=$(echo "$last_test" | cut -d'.' -f1);
    last_output_filename="$last_test_name.out"
    last_input_filename="$last_test_name.in"
    last_cpp_filename="$last_test_name.cpp"

    category=$(grep -oP '(?<=-).*?(?=-)' <<< "${last_test_name}")
    t_number=$(grep -oP '(?<=^t).*?(?=-)' <<< "${last_test_name}")
    c_number=$(grep -oP '(?<=-)\d*?$' <<< "${last_test_name}")

    t_number=${t_number##+(0)}
    t_number_reuse=$(echo "$t_number" | sed 's/^0*//')
else
    tests=($(ls -1 t*.d | sort))
fi

last_test=${tests[-1]}
last_test_name=$(echo "$last_test" | cut -d'.' -f1);
last_output_filename="$last_test_name.out"
last_input_filename="$last_test_name.in"
last_cpp_filename="$last_test_name.cpp"

category=$(grep -oP '(?<=-).*?(?=-)' <<< "${last_test_name}")
[[ -n ${reuse_cat} && ${category} != "${reuse_cat}" ]] && echo "Assertion failed: discovered category differs from reuse category" && exit 1
t_number=$(grep -oP '(?<=^t).*?(?=-)' <<< "${last_test_name}")
c_number=$(grep -oP '(?<=-)\d*?$' <<< "${last_test_name}")

t_number=${t_number##+(0)}
t_number=$(echo "$t_number" | sed 's/^0*//')

[[ $new_cat == "true" ]] && category=$(zenity --title="add-test" --text "New category name?" --entry) && c_number=0
[[ -z "$category" ]] && echo "No category. Aborting..." && exit

[[ -n $reuse_cat ]] && t_number=t_number_reuse

new_test_name=$(printf "t%03d-${category}-$((c_number + 1))" $((t_number + 1)))
echo $new_test_name created

active="true"

ACTIVE_COMMAND="echo"
[[ $active == "true" ]] && ACTIVE_COMMAND="cp"

$ACTIVE_COMMAND "$last_test" "${new_test_name}.d"
$ACTIVE_COMMAND "$last_input_filename" "${new_test_name}.in"
$ACTIVE_COMMAND "$last_output_filename" "${new_test_name}.out"

new_cpp="${new_test_name}.cpp"
$ACTIVE_COMMAND "$last_cpp_filename" "$new_cpp"
[[ $active != "true" ]] && exit 0

cd ..
rm test.cpp; ln -s "codegen/${new_cpp}" test.cpp
rm test.in;  ln -s "codegen/${new_test_name}.in" test.in
rm test.out; ln -s "codegen/${new_test_name}.out" test.out
