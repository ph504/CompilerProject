#!/usr/bin/env zsh

use_colors=true
[[ $1 == '-n' || $1 == '--no-tty' ]] && use_colors=false
[[ -t 1 ]] && stdout_tty=true
[[ -t 2 ]] && stderr_tty=true

[[ $use_colors == true ]] && red=$(tput setaf 1)
[[ $use_colors == true ]] && green=$(tput setaf 2)
[[ $use_colors == true ]] && orange=$(tput setaf 3)
[[ $use_colors == true ]] && teal=$(tput setaf 10)
[[ $use_colors == true ]] && normal=$(tput sgr0)


function report {
    msg=$2
    [[ -z ${msg} ]] && msg=$1
    [[ -n $2 ]] && color=$1

    if [[ "$stdout_tty" == true ]]; then
        echo -e "${color}${msg}${normal}"
    else
        echo -e "${msg}"
    fi

    [[ "$stderr_tty" == "$stdout_tty" ]] && return

    if [[ "$stderr_tty" == true ]]; then
        echo -e "${color}${msg}${normal}" >&2
    else
        echo -e "${msg}" >&2
    fi
}

function report_success {
    report "$green" "$1"
}

function report_failure {
    report "$red" "$1"
}

function set_python_env {
    echo "${teal}* Setting up python env *${normal}" >&2

    function loadconda {
        # >>> conda initialize >>>
        # !! Contents within this block are managed by 'conda init' !!
        __conda_setup="$('/opt/anaconda3/bin/conda' 'shell.bash' 'hook' 2> /dev/null)"
        if [[ $? -eq 0 ]]; then
            eval "$__conda_setup"
        else
            if [[ -f "/opt/anaconda3/etc/profile.d/conda.sh" ]]; then
            true
    # . "/opt/anaconda3/etc/profile.d/conda.sh"  # commented out by conda initialize
            else
            true
    # export PATH="/opt/anaconda3/bin:$PATH"  # commented out by conda initialize
            fi
        fi
        unset __conda_setup
        # <<< conda initialize <<<
    }

    loadconda >&2
    conda activate /home/saeid/Projects/Python/Decaf/.venv >&2
}

function bison_cpp_produce {
    ./main -i "$1" -o "$2"
}

function bison_cpp_build {
    echo "${orange}* Building Flex/Bison C++ project... *${normal}" >&2
    return 0

    ## LOGIC
    flex scanner.l
    bison parser.y -d -o parser.tab.c

    if [[ $? != 0 ]] ; then
        report_failure "*** Bison Parse Error! ***" && exit
    else
        ## LOGIC
        g++ *.cpp *.h parser.tab.c parser.tab.h -std=c++14 -o main  # you can change this line based on your input files

        if [[ $? != 0 ]]; then
            report_failure "*** Compiler code did not compile! ***" && exit
        else
            echo "${green}*** Compiler compiled successfully ***${normal}" >&2
        fi
    fi
}

function cup_java_produce {
    java -cp "java_cup.jar:java_cup_runtime.jar:jflex-full-1.8.2.jar:" Main -i "$1" -o "$2"
}

function cup_java_build {
    echo "${orange}* Building CUP Java project... *${normal}" >&2

    ## LOGIC
    javac -cp "java_cup.jar:java_cup_runtime.jar:jflex-full-1.8.2.jar:" Main.java Model/*.java

    if [[ $? != 0 ]]; then
        report_failure "*** Compiler code did not compile! ***" && exit
    else
        echo "${green}*** Compiler compiled successfully ***${normal}" >&2
    fi
}

function lark_python_produce {
    if command -v python3 >/dev/null; then
        python3 main.py -i $1 -o $2
    else
        python main.py -i $1 -o $2
    fi
}

function lark_python_build {
    echo "${teal}* Lark Python project, no build needed *${normal}" >&2
    set_python_env
}

function pgen_cpp_produce {
    ./main -i "$1" -o "$2"
}

function pgen_cpp_build {
    echo "${orange}* Building PGen C++ project... *${normal}" >&2

    ## LOGIC
    flex scanner.l

    if [[ $? != 0 ]] ; then
        report_failure "*** Flex Parse Error! ***" && exit
    else
        ## LOGIC
        g++ *.cpp *.h -std=c++14 -o main  # you can change this line based on your input files

        if [[ $? != 0 ]]; then
            report_failure "*** Compiler code did not compile! ***" && exit
        else
            echo "${green}*** Compiler compiled successfully ***${normal}" >&2
        fi
    fi
}

function pgen_java_produce {
    java main -i "$1" -o "$2"
}

function pgen_java_build {
    echo "${orange}* Building PGen Java project... *${normal}" >&2

    ## LOGIC
    javac main.java

    if [[ $? != 0 ]]; then
        report_failure "*** Compiler code did not compile! ***" && exit
    else
        echo "${green}*** Compiler compiled successfully ***${normal}" >&2
    fi
}

function pgen_python_produce {
    if command -v python3 >/dev/null; then
        python3 main.py -i "$1" -o "$2"
    else
        python main.py -i "$1" -o "$2"
    fi
}

function pgen_python_build {
    # no-op
    echo "${teal}* PGen Python project, no build needed *${normal}" >&2
}

# figure out the technology used
[[ -f ".bison-c++" ]]   && technology="bison-c++"   && bison_cpp_build
[[ -f ".cup-java" ]]    && technology="cup-java"    && cup_java_build
[[ -f ".lark-python" ]] && technology="lark-python" && lark_python_build
[[ -f ".pgen-c++" ]]    && technology="pgen-c++"    && pgen_cpp_build
[[ -f ".pgen-java" ]]   && technology="pgen-java"   && pgen_java_build
[[ -f ".pgen-python" ]] && technology="pgen-python" && pgen_python_build

[[ -z "$technology" ]] && echo "Unknown technology" >&2 && exit


mkdir -p out
mkdir -p report
cd ./tests || exit
prefix="t"
dirlist=($(ls ${prefix}*.d))
OUTPUT_DIRECTORY="out/"
TEST_DIRECTORY="tests/"
REPORT_DIRECTORY="report/"
NUMBER_OF_PASSED=0
NUMBER_OF_FAILED=0
cd ../


declare -A successes
declare -A failures
declare -A categories

current_cat_num=0

for source_filename in ${dirlist[*]}
do
    test_name=$(echo "$source_filename" | cut -d'.' -f1);
    output_filename="$test_name.out"
    input_filename="$test_name.in"
    output_asm="$test_name.s"
    report_filename="$test_name.report.txt"

    category=$(grep -oP '(?<=-).*?(?=-)' <<< "$test_name")

    [[ -z "${categories[$category]}" ]] && categories[$category]=$((++current_cat_num))
    [[ -z "${successes[$category]}" ]] && successes[$category]=0
    [[ -z "${failures[$category]}" ]] && failures[$category]=0


    report "\nRunning Test $test_name -------------------------------------"

    case $technology in
        'bison-c++')
            bison_cpp_produce "$TEST_DIRECTORY$source_filename" "$OUTPUT_DIRECTORY$output_asm"
            ;;
        'cup-java')
            cup_java_produce "$TEST_DIRECTORY$source_filename" "$OUTPUT_DIRECTORY$output_asm"
            ;;
        'lark-python')
            lark_python_produce "$TEST_DIRECTORY$source_filename" "$OUTPUT_DIRECTORY$output_asm"
            ;;
        'pgen-c++')
            pgen_cpp_produce "$TEST_DIRECTORY$source_filename" "$OUTPUT_DIRECTORY$output_asm"
            ;;
        'pgen-java')
            pgen_java_produce "$TEST_DIRECTORY$source_filename" "$OUTPUT_DIRECTORY$output_asm"
            ;;
        'pgen-python')
            pgen_python_produce "$TEST_DIRECTORY$source_filename" "$OUTPUT_DIRECTORY$output_asm"
            ;;
    esac

    if [[ $? == 0 ]]; then
        report_success "Test code compiled successfully"

        # the ultimate test, the real show...
        timeout 10 spim -nomapped_io -q -a -f "$OUTPUT_DIRECTORY$output_asm" < "$TEST_DIRECTORY$input_filename" > "$OUTPUT_DIRECTORY$output_filename" 2>&2

        if [[ $? == 0 ]]; then
            report_success "Test executed successfully"
            tail -n +6 "${OUTPUT_DIRECTORY}${output_filename}" > "${OUTPUT_DIRECTORY}${output_filename}.swp"
            mv "${OUTPUT_DIRECTORY}${output_filename}.swp" "${OUTPUT_DIRECTORY}${output_filename}"
            diff "${OUTPUT_DIRECTORY}${output_filename}" "${TEST_DIRECTORY}${output_filename}" > "${REPORT_DIRECTORY}${report_filename}"
#            if command -v python3 >/dev/null; then
#                python3 comp.py -a "${OUTPUT_DIRECTORY}${output_filename}" -b "${TEST_DIRECTORY}${output_filename}" -o "${REPORT_DIRECTORY}${report_filename}"
#            else
#                python comp.py -a "${OUTPUT_DIRECTORY}${output_filename}" -b "${TEST_DIRECTORY}${output_filename}" -o "${REPORT_DIRECTORY}${report_filename}"
#            fi
            if [[ $? == 0 ]]; then
                ((NUMBER_OF_PASSED++))
                successes[$category]=$((${successes[$category]} + 1))
                report_success "++++ test passed"
            else
                ((NUMBER_OF_FAILED++))
                failures[$category]=$((${failures[$category]} + 1))
                report_failure "---- test failed !"
            fi
        else
            ((NUMBER_OF_FAILED++))
            failures[$category]=$((${failures[$category]} + 1))
            report_failure "Execution failed!"
            report_failure "---- test failed !"
        fi
    else
        ((NUMBER_OF_FAILED++))
        failures[$category]=$((${failures[$category]} + 1))
        report_failure "Test did not compile successfully!"
        report_failure "---- test failed !"
    fi

done

declare -a sorted_categories
declare -A cat_rows
declare -A cat_weights
declare -A cat_pluses

while IFS= read -r line; do
    IFS=: read -r cat row weight plus <<<"$line"
    sorted_categories+=("$cat")
    cat_rows[$cat]="$row"
    cat_weights[$cat]="$weight"
    [[ -n $plus ]] && cat_pluses[$cat]="true"
done < categories

echo -e "\n\nGrades by category:"
for category in "${sorted_categories[@]}"; do
    row_str=$(printf "%3s" "#${cat_rows[$category]}")
    category_str=$(printf "%-10s" $category)
    all_c=$((successes[$category] + failures[$category]))
    success_c=${successes[$category]}
    plus_str=" "
    [[ ${cat_pluses[$category]} == "true" ]] && plus_str="+"
    ratio_label=$(printf "%5s" "${success_c}/${all_c}")
    ratio=$((${success_c}*1.0/${all_c}))
    ratio_str=$(printf "${plus_str}%7.2f" $((ratio*100.0)))
    weighted_ratio=$(printf "%.2f" $((cat_weights[$category]*ratio)))
    echo -e "  => ${row_str} ${category_str}:  ${ratio_label} = ${ratio_str}% (× ${cat_weights[$category]} = ${plus_str}${weighted_ratio})"
done

echo -e "\n================="
echo "Total Passed: $NUMBER_OF_PASSED"
echo "Total Failed: $NUMBER_OF_FAILED"
echo "Total Tests:  $((NUMBER_OF_FAILED + NUMBER_OF_PASSED))"
