runGradle() {
  if [[ $CODEBUILD_BUILD_SUCCEEDING = 1 ]]; then
    echo Executing "$@"
    ./gradlew --build-cache "$@"
  fi
}

ifCI() {
  if [[ $CI = true ]]; then
    "$@"
  else
    echo Not a CI environment, will NOT run "$@"
  fi
}
