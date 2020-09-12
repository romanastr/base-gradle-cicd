next() {
  if [[ $CODEBUILD_BUILD_SUCCEEDING = 1 ]]; then
    echo "Executing $1"
    ./gradlew --build-cache "$1"
  fi
}

ifCI() {
  if [[ $CI = 1 ]]; then
    "$@"
  fi
}