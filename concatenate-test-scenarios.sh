#!/bin/bash
# This script concatenates the parser test scenarios,
# for inclusion in the documentation at
# https://sling.apache.org/documentation/bundles/repository-initialization.html
#

function generate() {
    find src/test/resources/testcases -name '*.txt' | grep -v output | sort -V | while read f
    do
        echo
        echo "# $(basename $f)"
        echo
        cat $f;
        echo
    done
    echo
}

# Format for the sling-site docs page
generate | cat -s | sed 's/^/    /'
