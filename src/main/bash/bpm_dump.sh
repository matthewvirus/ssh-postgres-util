#!/bin/bash

check_bpm_type () {
    if [[ "$1" == "p" ]]; then
        printf -v headers_a '%#X' "$((0x021000))"
        printf -v fshift_a '%#X' "$((0x021020))"
    elif [[ "$1" == "s" ]]; then
        printf -v headers_a '%#X' "$((0x084000))"
        printf -v fshift_a '%#X' "$((0x084020))"
    else
        echo "Please, enter bpm type (p - portable, s - stationary)"
    fi
}

read_headers() {
    echo "Headers ($headers_a):"
    ./lmcu -r -a$headers_a -s32
    echo "--------------------------------------"
    echo ""
}

read_shift_by_id() {
    printf -v fs_tmp_a '%d' $fshift_a
    if [[ -n "$1" ]]; then
        printf -v lenta_id_a '%X' $(( ($1 - 1) * 664 + $fs_tmp_a))
        echo "Choosen shift ($1):"
        ./lmcu -r -a$lenta_id_a -s664
    else
        echo "Please, enter shift num"
    fi
}

check_bpm_type $1
read_headers
read_fshift
read_shift_by_id $2
