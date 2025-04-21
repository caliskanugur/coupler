def call(Map container) {
    sh "docker cp ${container.Name}:${container.rootPath}${container.ResultsOut} ."
    step([$class: 'JUnitResultArchiver', testResults: "**/${container.ResultsOut}"])
}
