next() {
  if [[ $CODEBUILD_BUILD_SUCCEEDING = 1 ]]; then
    echo "Executing $1"
    ./gradlew --build-cache "$@"
  fi
}

ifCI() {
  if [[ $CI = 1 ]]; then
    "$@"
  fi
}
