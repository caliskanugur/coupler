def call(String rootPath) {
    sh "${rootPath}pipeline/scripts/build_qase_reporter.sh;" + "${rootPath}reporter\""
}

