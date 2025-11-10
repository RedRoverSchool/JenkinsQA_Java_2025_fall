#!/bin/bash

set -e

BRANCH=${GITHUB_HEAD_REF:-${GITHUB_REF_NAME:-$(git rev-parse --abbrev-ref HEAD)}}
echo "Current branch: $BRANCH"

git fetch origin main --quiet

if [ "$BRANCH" = "main" ]; then
    BASE=$(git rev-parse HEAD^)
else
    BASE=$(git merge-base origin/main HEAD)
fi
echo "Base commit for diff: $BASE"

ALL_CHANGED_FILES=$(git diff --diff-filter=ACMRT --name-only "$BASE" HEAD)
echo "All changed/added/renamed/copied files:"
echo "$ALL_CHANGED_FILES"

ALL_DELETED_FILES=$(git diff --diff-filter=D --name-only "$BASE" HEAD)
echo "All deleted files:"
echo "$ALL_DELETED_FILES"

FULL_RUN=false

if [ -n "$ALL_DELETED_FILES" ] && echo "$ALL_DELETED_FILES" | grep -qv "Test\.java$"; then
  FULL_RUN=true
  echo "→ Non-test deleted file detected → FULL_RUN=true"
fi

if [ "$FULL_RUN" = false ] && [ -n "$ALL_CHANGED_FILES" ] && echo "$ALL_CHANGED_FILES" | grep -qv "Test\.java$"; then
  FULL_RUN=true
  echo "→ Non-test changed/added file detected → FULL_RUN=true"
fi

if [ "$FULL_RUN" = false ]; then
  CHANGED_TEST_FILES=$(echo "$ALL_CHANGED_FILES" | grep 'Test\.java$' || true)
  CHANGED_TEST_FILES=$(echo "$CHANGED_TEST_FILES" | tr '\n' ';')
  echo "Test .java files to run: $CHANGED_TEST_FILES"
else
  CHANGED_TEST_FILES=""
fi

if [ "$FULL_RUN" = true ]; then
  echo "LIST_OF_CHANGED_FILES=FULL_RUN"
  echo "LIST_OF_CHANGED_FILES=FULL_RUN" >> "$GITHUB_ENV"
elif [ -n "$CHANGED_TEST_FILES" ]; then
  echo "LIST_OF_CHANGED_FILES=$CHANGED_TEST_FILES"
  echo "LIST_OF_CHANGED_FILES=$CHANGED_TEST_FILES" >> "$GITHUB_ENV"
else
  echo "LIST_OF_CHANGED_FILES=NONE"
  echo "LIST_OF_CHANGED_FILES=NONE" >> "$GITHUB_ENV"
fi
